package com.executor.gateway.core;

import com.executor.gateway.service.DynamicRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/1 20:48
 * @Description: redis topic模式接收器
 */
@Component
@Slf4j
public class RedisTopicMessageReceiver {
    @Autowired
    private DynamicRouteService dynamicRouteService;

    /**
     * 接收route变化消息
     */
    public void receiveRouteMessage(String message) {
        try {
            dynamicRouteService.refreshAllRoute();
            log.info("刷新成功");
        } catch (Exception e) {
            log.error("处理gateway 消息异常",e);
        }
    }

    /**
     * 接收Api变化通知
     * @param message
     */
    public void receiveApiMessage(String message) {
        //刷新所有Api配置
        dynamicRouteService.refreshApi();
        log.info("刷新api成功");
      //  log.info("收到一条Api消息:{}", JSON.toJSONString(apiConfigManager.getApiConfigMap()));
    }
}
