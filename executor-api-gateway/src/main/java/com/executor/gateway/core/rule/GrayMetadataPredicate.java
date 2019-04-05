package com.executor.gateway.core.rule;

import com.executor.gateway.core.rule.support.RibbonFilterContext;
import com.executor.gateway.core.rule.support.RibbonFilterContextHolder;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/2 0002 17:58
 * @Description: 灰度负载均衡断言
 */
@Slf4j
public class GrayMetadataPredicate extends AbstractServerPredicate {

    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        final RibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
        ZookeeperServer server = (ZookeeperServer) predicateKey.getServer();
        final Map<String, String> metadata = server.getInstance().getPayload().getMetadata();
        //如果没有设置属性，那么走轮询
        if (context.getAttributes().isEmpty()){
            return true;
        }
        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(context.getAttributes().entrySet());
        return metadata.entrySet().containsAll(attributes);
    }
}
