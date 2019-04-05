package com.executor.gateway.service;

import com.executor.gateway.model.po.GatewayConfig;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/8 17:34
 * @Description: 路由加载service
 */
public interface DynamicRouteService {
    /**
     * 加载路由资源
     */
    void loadRoutes();
    /**
     *
     * 功能描述:刷新路由（不包含Api）
     *
     * @param:
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/4/4 0004 10:03
     */
    Integer updateRoute(GatewayConfig gatewayConfig);
    /**
     *
     * 功能描述:刷新所有api配置
     *
     * @param:
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/4/4 0004 17:57
     */
    void refreshApi();
    /**
     *
     * 功能描述:删除路由
     *
     * @param:
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/4/4 0004 13:47
     */
    Integer deleteRoute(String serviceId);

    /**
     *
     * 功能描述:刷新所有路由配置
     *
     * @param:
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/4/4 0004 17:18
     */
    Integer refreshAllRoute();
}
