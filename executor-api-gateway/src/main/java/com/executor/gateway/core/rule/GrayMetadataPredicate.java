package com.executor.gateway.core.rule;

import com.executor.gateway.core.rule.meta.BaseDiscoveryMetaInft;
import com.executor.gateway.core.rule.meta.ZookeeperMetaImpl;
import com.executor.gateway.core.rule.support.RibbonFilterContext;
import com.executor.gateway.core.rule.support.RibbonFilterContextHolder;
import com.executor.gateway.core.util.SpringBeanUtils;
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
        BaseDiscoveryMetaInft discoveryMetaInft = SpringBeanUtils.getBean(BaseDiscoveryMetaInft.class);
        //如果没有设置属性，那么走轮询
        if (context.getAttributes().isEmpty()){
            return true;
        }
        final Map<String, String> metadata = discoveryMetaInft.getMetaInfo(predicateKey.getServer());
        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(context.getAttributes().entrySet());
        return metadata.entrySet().containsAll(attributes);
    }
}
