package com.executor.gateway.filters.auth;

import com.executor.gateway.core.auth.BaseAuthHandler;
import com.executor.gateway.core.constant.EnvironmentTypeEnum;
import com.executor.gateway.core.constant.GatewayConst;
import com.executor.gateway.core.util.SpringBeanUtils;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/31 16:09
 * @Description: 权限校验过滤器，通过BaseAuthHandler来控制权限策略，限制到每个api，并根据不同环境进行不同的权限过滤
 */
@Component
@Slf4j
public class AuthVerifyGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthVerifyGatewayFilterFactory.Config> {

    public AuthVerifyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ApiConfigBo apiConfig = exchange.getAttributeOrDefault(GatewayConst.CACHE_API_OBJECT_KEY, new ApiConfigBo());
            BaseAuthHandler authHandler = (BaseAuthHandler) SpringBeanUtils.getBean(config.getAuthStrategy());
            if (authHandler == null) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                log.warn("找不到对应的authHandler");
                return response.setComplete();
            }
            if (apiConfig.getEnvironmentType() == EnvironmentTypeEnum.DEBUG.getValue()) {
                return authHandler.debugHandle(exchange, chain, config);
            } else if (apiConfig.getEnvironmentType() == EnvironmentTypeEnum.TEST.getValue()) {
                return authHandler.testHandle(exchange, chain, config);
            } else if (apiConfig.getEnvironmentType() == EnvironmentTypeEnum.PROD.getValue()) {
                return authHandler.prodHandle(exchange, chain, config);
            } else if (apiConfig.getEnvironmentType() == EnvironmentTypeEnum.MAINTENANCE.getValue()) {
                return authHandler.maintenanceHandle(exchange, chain, config);
            }
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        //校验策略bean名称
        private String authStrategy = "defaultAuthHandlerImpl";
    }
}
