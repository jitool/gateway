package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/4 0004 12:18
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "redis.topic.channel")
public class RedisTopicChannelProperties {
    //api的redis通道
    private String apiChannel;
    //route的redis通道
    private String routeChannel;

    public String getApiChannel() {
        return apiChannel;
    }

    public void setApiChannel(String apiChannel) {
        this.apiChannel = apiChannel;
    }

    public String getRouteChannel() {
        return routeChannel;
    }

    public void setRouteChannel(String routeChannel) {
        this.routeChannel = routeChannel;
    }
}
