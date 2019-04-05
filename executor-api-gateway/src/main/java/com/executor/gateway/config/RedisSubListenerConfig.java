package com.executor.gateway.config;

import com.executor.gateway.config.properties.RedisTopicProperties;
import com.executor.gateway.core.MessageReceiver;
import com.executor.gateway.core.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/1 20:46
 * @Description: redis topic通道配置
 */
@Configuration
@EnableConfigurationProperties(RedisTopicProperties.class)
public class RedisSubListenerConfig {
    @Autowired
    private RedisTopicProperties redisTopicProperties;

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter, MessageListenerAdapter listenerAdapter2) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个刷新route的通道
        container.addMessageListener(listenerAdapter, new PatternTopic(redisTopicProperties.getRouteChannel()));
        //订阅了一个刷新api的通道
        container.addMessageListener(listenerAdapter2, new PatternTopic(redisTopicProperties.getApiChannel()));
        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, redisTopicProperties.getRouteMethodName());
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, redisTopicProperties.getApiMethodName());
    }

}
