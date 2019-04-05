package com.executor.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/24 14:43
 * @Description:
 */
@SpringCloudApplication
@MapperScan(basePackages = "com.executor.gateway.mapper")
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class,args);
    }
}
