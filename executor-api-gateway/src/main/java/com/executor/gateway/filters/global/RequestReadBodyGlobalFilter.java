package com.executor.gateway.filters.global;

import com.executor.gateway.core.ValidateParamsFactory;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.executor.gateway.core.constant.GatewayConst.CACHE_API_OBJECT_KEY;
import static com.executor.gateway.core.constant.GatewayConst.CACHE_REQUEST_BODY_OBJECT_KEY;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/17 0017 19:43
 * @Description: 读取请求体过滤器，校验参数
 */
@Slf4j
@Component
public class RequestReadBodyGlobalFilter implements GlobalFilter, Ordered {
    @Autowired
    private ValidateParamsFactory validateParamsFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String contentType = headers.getFirst("Content-Type");
        if (Strings.isNullOrEmpty(contentType)) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return response.setComplete();
        }
        //上传文件直接通过
        if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            return chain.filter(exchange);
        }
        String requestParams = exchange.getAttributeOrDefault(CACHE_REQUEST_BODY_OBJECT_KEY, "");
        log.info("\n请求url={},请求参数={}\n,headers={}", request.getPath().value(), requestParams, request.getHeaders());
        ApiConfigBo apiConfig = exchange.getAttributeOrDefault(CACHE_API_OBJECT_KEY, new ApiConfigBo());
        String validateParamsJson = apiConfig.getValidateParams();
        if (!Strings.isNullOrEmpty(validateParamsJson)) {
            //空参数校验
            if (!validateParamsFactory.verifyParams(contentType,validateParamsJson,requestParams)) {
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }


}

