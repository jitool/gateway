package com.executor.gateway.config;

import com.executor.gateway.core.rule.GrayMetadataBalanceRule;
import com.executor.gateway.core.rule.meta.BaseDiscoveryMetaInft;
import com.executor.gateway.core.rule.meta.ZookeeperMetaImpl;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/2 0002 18:01
 * @Description: 灰度负载均衡配置
 */
@Configuration
@ConditionalOnProperty(value = "ribbon.filter.gray.enabled", matchIfMissing = true)
public class GrayRibbonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IRule metadataAwareRule() {
        return new GrayMetadataBalanceRule();
    }

    /**
     * 获取metaInfo策略
     * @return
     */
    @Bean
    @Primary
    BaseDiscoveryMetaInft discoveryMetaInft(){
        return new ZookeeperMetaImpl();
    }
}
