package com.ruoyi.system.service;

import com.ruoyi.common.config.RedisTopicChannelProperties;
import com.ruoyi.common.enums.TopicNotifyEventType;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.system.domain.GatewayConfig;
import com.ruoyi.system.listener.GatewayRefreshEvent;
import com.ruoyi.system.mapper.GatewayConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/4 0004 13:57
 * @Description: 公用gateway attr 和 gateway config
 */
public abstract class AbstractGatewayConfigService {
    @Autowired
    protected StringRedisTemplate redisTemplate;
    @Autowired
    protected RedisTopicChannelProperties redisTopicChannelProperties;
    @Autowired
    protected GatewayConfigMapper gatewayConfigMapper;
    @Autowired
    private ApplicationContext applicationContext;

    //刷新所有路由配置(包括route、preidicate、filter)
    protected void sendRefreshAllRouteNotify(){
        applicationContext.publishEvent(new GatewayRefreshEvent(this,redisTopicChannelProperties.getRouteChannel()));
    }
}
