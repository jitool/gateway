package com.executor.gateway.core.auth;

import com.executor.gateway.filters.auth.AuthVerifyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/5 21:00
 * @Description:
 */
public interface BaseAuthHandler {
    /**
     * debug环境处理逻辑
     * @param exchange
     * @param chain
     * @param config
     * @return
     */
    Mono<Void> debugHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config);

    /**
     * 测试环境处理逻辑
     * @param exchange
     * @param chain
     * @param config
     * @return
     */
    Mono<Void> testHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config);

    /**
     * 正式环境处理逻辑
     * @param exchange
     * @param chain
     * @param config
     * @return
     */
    Mono<Void> prodHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config);

    /**
     * 维护环境处理逻辑
     * @param exchange
     * @param chain
     * @param config
     * @return
     */
    Mono<Void> maintenanceHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config);
}
