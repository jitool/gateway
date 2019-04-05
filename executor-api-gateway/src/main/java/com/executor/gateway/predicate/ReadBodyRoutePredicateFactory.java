package com.executor.gateway.predicate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static com.executor.gateway.core.constant.GatewayConst.CACHE_REQUEST_BODY_OBJECT_KEY;
import static org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter.CACHED_REQUEST_BODY_KEY;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/25 0025 10:47
 * @Description: 缓存请求体数据predicate
 */
@Slf4j
@Component
public class ReadBodyRoutePredicateFactory extends AbstractRoutePredicateFactory<ReadBodyRoutePredicateFactory.Config> {

    public ReadBodyRoutePredicateFactory() {
        super(Config.class);
    }


    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        throw new UnsupportedOperationException(
                "ReadBodyRoutePredicateFactory is only async.");
    }

    public AsyncPredicate<ServerWebExchange> applyAsync(Config config) {
        return exchange -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            //必须设置contentType
            String contentTypeStr = headers.getFirst("Content-Type");
            if (contentTypeStr == null) {
                log.warn("consider your Content-Type empty?");
                return Mono.just(Boolean.FALSE);
            }
            //文件上传直接跳过
            if (contentTypeStr.startsWith("multipart/form-data")) {
                return Mono.just(Boolean.TRUE);
            }
            String cachedBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
            Mono<String> modifiedBody;
            if (cachedBody != null) {
                modifiedBody = Mono.just(cachedBody);
            } else {
                ServerRequest serverRequest = new DefaultServerRequest(exchange);
                modifiedBody = serverRequest.bodyToMono(String.class)
                        .flatMap(body -> {
                            exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, body);
                            return Mono.just(body);
                        });
            }
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            //因为请求体可能是null，这里自定义个缓存输出
            CustomCacheBodyOutputMessage outputMessage = new CustomCacheBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        if (outputMessage != null) {
                            exchange.getAttributes().put(CACHED_REQUEST_BODY_KEY,
                                    outputMessage.getBody());
                        }
                        return Mono.just(Boolean.TRUE);
                    }));
        };
    }


    public static class Config {
        private String className;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }


}
