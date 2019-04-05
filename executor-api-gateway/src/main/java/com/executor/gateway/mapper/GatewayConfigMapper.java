package com.executor.gateway.mapper;

import com.executor.gateway.model.po.GatewayConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/11 0011 19:30
 * @Description:
 */
public interface GatewayConfigMapper {
    List<GatewayConfig> queryAll();
    /**
     *
     * 功能描述:查询单个路由服务
     *
     * @param:
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/4/4 0004 10:04
     */
    GatewayConfig queryByServiceId(@Param("serviceId") String serviceId);
}
