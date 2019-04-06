package com.executor.gateway.core.rule.meta;

import com.netflix.loadbalancer.Server;

import java.util.Map;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/6 13:47
 * @Description: 服务发现获取metainfo接口
 */
public interface BaseDiscoveryMetaInft {

    Map<String,String> getMetaInfo(Server server);
}
