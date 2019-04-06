package com.executor.gateway.filters.global;

import com.executor.gateway.core.rule.support.RibbonFilterContext;
import com.executor.gateway.core.rule.support.RibbonFilterContextHolder;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.filter.LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/6 15:22
 * @Description: 清理钩子用的过滤器
 */
@Component
public class HolderContextClearGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //清理线程钩子
        RibbonFilterContextHolder.clearCurrentContext();
        return chain.filter(exchange);
    }

    //在负载策略过滤后进行清除
    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER+1;
    }
}
