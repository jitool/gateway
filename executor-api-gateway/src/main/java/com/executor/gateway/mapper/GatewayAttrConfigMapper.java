package com.executor.gateway.mapper;

import com.executor.gateway.model.po.GatewayAttrConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/11 0011 19:30
 * @Description:
 */
public interface GatewayAttrConfigMapper {
    List<GatewayAttrConfig> queryAllByServiceId(@Param("serviceId") String serviceId);
}
