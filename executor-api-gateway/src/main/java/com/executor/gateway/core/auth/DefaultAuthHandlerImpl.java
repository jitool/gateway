package com.executor.gateway.core.auth;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.ApiResult;
import com.executor.gateway.core.constant.RESPONSE;
import com.executor.gateway.filters.auth.AuthVerifyGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/5 21:08
 * @Description:
 */
@Component
@Slf4j
public class DefaultAuthHandlerImpl implements BaseAuthHandler {
    @Override
    public Mono<Void> debugHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config) {
        return chain.filter(exchange);
    }

    @Override
    public Mono<Void> testHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config) {
        return chain.filter(exchange);
    }

    @Override
    public Mono<Void> prodHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config) {
        return chain.filter(exchange);
    }

    @Override
    public Mono<Void> maintenanceHandle(ServerWebExchange exchange, GatewayFilterChain chain, AuthVerifyGatewayFilterFactory.Config config) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        ApiResult apiResult=new ApiResult(RESPONSE.ERROR,"抱歉,正在维护中...");
        String s = JSON.toJSONString(apiResult);
        DataBuffer dataBuffer = response.bufferFactory().wrap(s.getBytes());
         return response.writeWith(Mono.just(dataBuffer));
    }
}
