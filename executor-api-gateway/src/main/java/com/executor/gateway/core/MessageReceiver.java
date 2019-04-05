package com.executor.gateway.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.executor.gateway.core.constant.RESPONSE;
import com.executor.gateway.core.constant.TopicNotifyEventType;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import com.executor.gateway.model.po.GatewayConfig;
import com.executor.gateway.service.DynamicRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/1 20:48
 * @Description:
 */
@Component
@Slf4j
public class MessageReceiver {
    @Autowired
    private ApiConfigManager apiConfigManager;
    @Autowired
    private DynamicRouteService dynamicRouteService;

    /**
     * 接收route变化消息
     */
    public void receiveRouteMessage(String message) {
        try {
            JSONObject jsonObject = JSON.parseObject(message);
            String eventType = jsonObject.getString("type");
            if (eventType.equals(TopicNotifyEventType.DEL.getValue())){
                String serviceId = jsonObject.getString("serviceId");
                dynamicRouteService.deleteRoute(serviceId);
            }else if(eventType.equals(TopicNotifyEventType.UPDATE.getValue())){
                GatewayConfig gatewayConfig = jsonObject.getObject("gatewayConfig", GatewayConfig.class);
                Integer resultCode = dynamicRouteService.updateRoute(gatewayConfig);
                if (resultCode== RESPONSE.ERROR){
                    log.warn("数据库不存在此gateway route配置，更新失败");
                }
            }else if(eventType.equals(TopicNotifyEventType.REFRESH.getValue())){
                //刷新所有路由配置
                dynamicRouteService.refreshAllRoute();
            }
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
        JSONObject jsonObject = JSON.parseObject(message);
        String eventType = jsonObject.getString("type");
        JSONArray patternUrlArr = jsonObject.getJSONArray("patternUrl");
        if (eventType.equals(TopicNotifyEventType.DEL.getValue())) {
            for (Object o : patternUrlArr) {
                apiConfigManager.delConfig(o.toString());
            }
        } else if(eventType.equals(TopicNotifyEventType.UPDATE.getValue())){
            ApiConfig apiConfig = jsonObject.getObject("apiConfig", ApiConfig.class);
            ApiConfigBo apiConfigBo = new ApiConfigBo();
            BeanUtils.copyProperties(apiConfig, apiConfigBo);
            //先删除后增加(可能url改变)
            for (Object o : patternUrlArr) {
                apiConfigManager.delConfig(o.toString());
                apiConfigManager.putConfig(apiConfigBo.getPatternUrl(),apiConfigBo);
            }
        }else if(eventType.equals(TopicNotifyEventType.REFRESH.getValue())){
            //刷新所有Api配置
            dynamicRouteService.refreshApi();
        }

      //  log.info("收到一条Api消息:{}", JSON.toJSONString(apiConfigManager.getApiConfigMap()));
    }
}
