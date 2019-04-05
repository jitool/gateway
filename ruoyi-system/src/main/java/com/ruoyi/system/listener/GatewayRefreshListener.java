package com.ruoyi.system.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/5 13:53
 * @Description: 路由刷新监听
 */
@Component
public class GatewayRefreshListener {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @EventListener(GatewayRefreshEvent.class)
    public void handleRefresh(GatewayRefreshEvent gatewayRefreshEvent){
        String channel = gatewayRefreshEvent.getChannel();
        redisTemplate.convertAndSend(channel,"");
    }
}
