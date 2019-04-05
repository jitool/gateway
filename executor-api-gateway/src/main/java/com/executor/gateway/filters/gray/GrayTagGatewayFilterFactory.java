package com.executor.gateway.filters.gray;

import com.executor.gateway.core.rule.support.RibbonFilterContextHolder;
import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/2 0002 15:56
 * @Description: 添加标签的过滤器，用于灰度发布
 */
@Component
public class GrayTagGatewayFilterFactory extends AbstractGatewayFilterFactory<GrayTagGatewayFilterFactory.Config> {
    public GrayTagGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
            if (!Strings.isNullOrEmpty(config.getTagKey())){
                ServerHttpRequest request = exchange.getRequest();
                String tag = request.getHeaders().getFirst(config.getTagKey());
                RibbonFilterContextHolder.getCurrentContext().add(config.getTagKey(),tag);
            }
            return chain.filter(exchange);
        } ;
    }

    @Data
    public static class Config{
        //头部中带的标签名称
        private String tagKey;
    }
}
