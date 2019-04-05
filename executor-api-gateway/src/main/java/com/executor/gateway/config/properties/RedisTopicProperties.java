package com.executor.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/4 0004 12:37
 * @Description:
 */
@ConfigurationProperties(prefix = "redis.topic")
@Data
public class RedisTopicProperties {
    private String apiChannel;
    private String routeChannel;
    private String apiMethodName;
    private String routeMethodName;
}
