package com.executor.gateway.filters.auth;

import com.executor.gateway.core.constant.EnvironmentTypeEnum;
import com.executor.gateway.core.constant.GatewayConst;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/31 16:09
 * @Description:
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
            ApiConfigBo apiConfig = exchange.getAttributeOrDefault(GatewayConst.CACHE_API_OBJECT_KEY,new ApiConfigBo());
            ServerHttpResponse response = exchange.getResponse();
            ServerHttpRequest request = exchange.getRequest();
            if (apiConfig.getEnvironmentType()== EnvironmentTypeEnum.DEBUG.getValue()){
                return chain.filter(exchange);
            }
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config{
        //校验策略bean名称
        private String authStrategy;
    }
}
