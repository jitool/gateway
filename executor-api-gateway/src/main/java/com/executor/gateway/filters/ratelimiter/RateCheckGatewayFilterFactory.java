package com.executor.gateway.filters.ratelimiter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.executor.gateway.core.constant.LimitLevelEnum;
import com.executor.gateway.model.bo.BaseLimitKey;
import com.executor.gateway.core.util.SpringBeanUtils;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static com.executor.gateway.core.constant.GatewayConst.CACHE_API_OBJECT_KEY;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/8 13:04
 * @Description:
 */
@Component
@Slf4j
public class RateCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<RateCheckGatewayFilterFactory.Config> {
    @Autowired
    private CustomerRateLimiter rateLimiter;

    private KeyResolver keyResolver;

    public RateCheckGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        if (config == null) {
            throw new IllegalStateException("rateLimiter is initialized,but keyResolver is not initialized");
        }
        this.keyResolver = SpringBeanUtils.getBean(config.getKeyResolverName(), KeyResolver.class);

        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            return keyResolver.resolve(exchange).flatMap(key -> {//key 参考com.gateway.config.KeyResolverConfiguration
                ServerHttpResponse httpResponse = exchange.getResponse();
                HttpHeaders headers = httpResponse.getHeaders();
                if (Strings.isNullOrEmpty(route.getId())) {
                    httpResponse.setStatusCode(HttpStatus.BAD_GATEWAY);
                    return httpResponse.setComplete();
                }

                BaseLimitKey baseLimitKey = this.reBuildLimitKey(exchange, key);
                return rateLimiter.isAllowed(route.getId(), JSON.toJSONString(baseLimitKey))
                        .flatMap(response -> {
                            log.debug("response: " + response);
                            //设置headers
                            Map<String, String> responseHeaders = response.getHeaders();
                            for (String header : responseHeaders.keySet()) {
                                headers.add(header, responseHeaders.get(header));
                            }
                            if (response.isAllowed()) {
                                return chain.filter(exchange);
                            }
                            //超过了限流的response返回值
                            httpResponse.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                            return httpResponse.setComplete();
                        });
            });
        };
    }

    private BaseLimitKey reBuildLimitKey(ServerWebExchange exchange, String key) {
        ApiConfigBo apiConfig = exchange.getAttributeOrDefault(CACHE_API_OBJECT_KEY, new ApiConfigBo());
        BaseLimitKey baseLimitKey = JSON.parseObject(key, BaseLimitKey.class);
        baseLimitKey.setReplenishRate(apiConfig.getReplenishRate());
        baseLimitKey.setBurstCapacity(apiConfig.getBurstCapacity());
        return baseLimitKey;
    }

    @Data
    public static class Config {
        //限流策略key的bean名称
        private String keyResolverName;
    }

    public static void main(String[] args) {
        StringBuilder niuBi=new StringBuilder();
        for (int i = 0; i < 3; i++) {
            niuBi.append("6");
        }
        System.out.println(niuBi.toString());
    }
}
