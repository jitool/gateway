package com.executor.gateway.config;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.model.bo.BaseLimitKey;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/8 17:02
 * @Description: key生成规则,如需配置不同的限流策略，请参考apiKeyResolver()
 * 默认是限流到api级别,比如ip限流策略，那么会以（ip+api）进行限流；
 * 配置为全局级别，那么只会以ip进行限流
 */
@Configuration
public class KeyResolverConfiguration {

    /**
     * api限流
     * @return
     */
    @Bean("apiUriKeyResolver")
    public KeyResolver apiUriKeyResolver() {
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestUri = request.getPath().value();
            return Mono.just(createKey(requestUri,requestUri));
        };
    }

    /**
     * 路由限流策略
     * @return
     */
    @Bean("routeIdKeyResolver")
    public KeyResolver routeIdKeyResolver(){
        return exchange -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String routeId = Objects.requireNonNull(route).getId();
            ServerHttpRequest request = exchange.getRequest();
            String requestUri = request.getPath().value();
            return Mono.just(createKey(requestUri,routeId));
        };
    }

    /**
     * ip限流策略
     * @return
     */
    @Bean("remoteIpKeyResolver")
    public KeyResolver remoteIpKeyResolver(){
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestUri = request.getPath().value();
            String hostString = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
            return Mono.just(createKey(requestUri,hostString));
        };
    }

    private String createKey(String requestUri,String bizStr){
        BaseLimitKey baseLimitKey = BaseLimitKey.builder()
                .apiUri(requestUri)
                .limitBiz(bizStr)
                .build();
        return JSON.toJSONString(baseLimitKey);
    }

}
