package com.executor.gateway.core;

import com.executor.gateway.service.DynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/26 0026 17:08
 * @Description: 路由资源加载器
 */
@Component
public class RoutesResourceLoader implements ApplicationRunner {
    @Autowired
    private DynamicRouteService dynamicRouteService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        dynamicRouteService.loadRoutes();
    }
}
