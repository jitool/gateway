package com.ruoyi.system.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/5 13:55
 * @Description:
 */
public class GatewayRefreshEvent extends ApplicationEvent {

    private String channel;

    public GatewayRefreshEvent(Object source, String channel) {
        super(source);
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
