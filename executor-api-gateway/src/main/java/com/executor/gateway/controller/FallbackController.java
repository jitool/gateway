package com.executor.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.ApiResult;
import com.executor.gateway.core.constant.RESPONSE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/28 0028 17:53
 * @Description: 熔断Controller
 */
@RestController
@Slf4j
public class FallbackController {
    @Autowired
    private ZookeeperDiscoveryClient zookeeperServiceRegistry;

    @RequestMapping("/defaultfallback")
    public ApiResult<String> fallback(){
        return new ApiResult<>(RESPONSE.ERROR,"当前服务不可用");
    }


}
