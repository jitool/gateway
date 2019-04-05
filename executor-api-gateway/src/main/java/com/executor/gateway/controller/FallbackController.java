package com.executor.gateway.controller;

import com.executor.gateway.core.ApiResult;
import com.executor.gateway.core.constant.RESPONSE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/")
    public ApiResult<String> test()
    {
        return new ApiResult<>(RESPONSE.SUCCESS,"成功");
    }
    @GetMapping("/csrf")
    public ApiResult<String> csrf(){
        return new ApiResult<>(RESPONSE.SUCCESS,"csrf");
    }
}
