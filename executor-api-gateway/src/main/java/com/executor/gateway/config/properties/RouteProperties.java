package com.executor.gateway.config.properties;

import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/4/5 15:30
 * @Description: 路由信息，聚合swagger使用
 */
@Component
@Data
public class RouteProperties {
    private List<RouteDefinition> routes = new ArrayList<>();
}
