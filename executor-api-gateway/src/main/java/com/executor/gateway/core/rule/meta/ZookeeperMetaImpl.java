package com.executor.gateway.core.rule.meta;

import com.netflix.loadbalancer.Server;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import java.util.Map;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/6 13:51
 * @Description:
 */
public class ZookeeperMetaImpl implements BaseDiscoveryMetaInft {
    @Override
    public Map<String, String> getMetaInfo(Server server) {
        ZookeeperServer zkServer = (ZookeeperServer) server;
        return zkServer.getInstance().getPayload().getMetadata();
    }
}
