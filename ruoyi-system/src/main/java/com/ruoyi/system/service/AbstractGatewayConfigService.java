package com.ruoyi.system.service;

import com.ruoyi.common.config.RedisTopicChannelProperties;
import com.ruoyi.common.enums.TopicNotifyEventType;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.system.domain.GatewayConfig;
import com.ruoyi.system.mapper.GatewayConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    protected  void sendRouteDelNontify(String[] idsArr){
        for (String serviceId : idsArr) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",TopicNotifyEventType.DEL.getValue());
            jsonObject.put("serviceId",serviceId);
            redisTemplate.convertAndSend(redisTopicChannelProperties.getRouteChannel(),jsonObject.toString());
        }
    }

    //已经添加了路由配置才发送通知
    protected void sendRouteUpdateNotify(GatewayConfig gatewayConfig) {
        if (gatewayConfig!=null){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type",TopicNotifyEventType.UPDATE.getValue());
            jsonObject.put("gatewayConfig",gatewayConfig);
            redisTemplate.convertAndSend(redisTopicChannelProperties.getRouteChannel(),jsonObject.toString());
        }
    }
    //刷新所有路由配置
    protected void sendRefreshAllRouteNotify(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("type", TopicNotifyEventType.REFRESH.getValue());
        redisTemplate.convertAndSend(redisTopicChannelProperties.getRouteChannel(),jsonObject.toString());
    }
}
