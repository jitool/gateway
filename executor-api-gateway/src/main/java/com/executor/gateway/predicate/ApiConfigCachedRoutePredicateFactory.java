package com.executor.gateway.predicate;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.ApiConfigManager;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.executor.gateway.core.constant.GatewayConst.CACHE_API_OBJECT_KEY;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/26 0026 15:20
 * @Description: 缓存api配置predicate(为了给后续的各种过滤器使用)
 */
@Component
@Slf4j
public class ApiConfigCachedRoutePredicateFactory extends AbstractRoutePredicateFactory<ApiConfigCachedRoutePredicateFactory.Config> {
    @Autowired
    private ApiConfigManager apiConfigManager;


    public ApiConfigCachedRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            String apiUri = this.getApiUri(config, exchange, request);
            ApiConfigBo api = apiConfigManager.createApi(apiUri);
            exchange.getAttributes().put(CACHE_API_OBJECT_KEY, api == null ? new ApiConfigBo() : api);
            log.debug("获取到api{}", JSON.toJSONString(api));
            return true;
        };
    }

    //获取api的uri
    private String getApiUri(Config config, ServerWebExchange exchange, ServerHttpRequest request) {
        String rawPath = request.getURI().getRawPath();
        String apiUri;
        if (config.getStripParts() == 0) {
            String routeId = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_PREDICATE_ROUTE_ATTR);
            apiUri = rawPath.replace("/" + routeId, "");
        } else {
            apiUri = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/"))
                    .skip(1).collect(Collectors.joining("/"));
        }
        return apiUri;
    }

    @Data
    public static class Config {
        //需要舍弃的uri部分 ex:访问/msg-center/msg/test,如果stripParts=1,那么会变成/msg/test
        private int stripParts;
    }

    public static void main(String[] args) {
        String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray("/msg-c/msg/test", "/"))
                .skip(1).collect(Collectors.joining("/"));
        System.out.println(newPath);
    }
}
