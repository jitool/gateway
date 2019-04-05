package com.executor.gateway.predicate;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.ApiConfigManager;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

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
            String routeId = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_PREDICATE_ROUTE_ATTR);
            String apiUri = request.getPath().value().replace(routeId, "");
            ApiConfigBo api = apiConfigManager.createApi(apiUri);
            if (api == null) {
                return false;
            }
            exchange.getAttributes().put(CACHE_API_OBJECT_KEY, api);
            log.info("获取到api{}", JSON.toJSONString(api));
            return true;
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
