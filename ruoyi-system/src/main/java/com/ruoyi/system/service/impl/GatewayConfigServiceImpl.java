package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.GatewayConfig;
import com.ruoyi.system.service.AbstractGatewayConfigService;
import com.ruoyi.system.service.IGatewayConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网关路由配置 服务层实现
 *
 * @author ruoyi
 * @date 2019-04-03
 */
@Service
public class GatewayConfigServiceImpl extends AbstractGatewayConfigService implements IGatewayConfigService {

    /**
     * 查询网关路由配置信息
     *
     * @param serviceId 网关路由配置ID
     * @return 网关路由配置信息
     */
    @Override
    public GatewayConfig selectGatewayConfigById(String serviceId) {
        return gatewayConfigMapper.selectGatewayConfigById(serviceId);
    }

    /**
     * 查询网关路由配置列表
     *
     * @param gatewayConfig 网关路由配置信息
     * @return 网关路由配置集合
     */
    @Override
    public List<GatewayConfig> selectGatewayConfigList(GatewayConfig gatewayConfig) {
        return gatewayConfigMapper.selectGatewayConfigList(gatewayConfig);
    }

    /**
     * 新增网关路由配置
     *
     * @param gatewayConfig 网关路由配置信息
     * @return 结果
     */
    @Override
    public int insertGatewayConfig(GatewayConfig gatewayConfig) {
        int result = gatewayConfigMapper.insertGatewayConfig(gatewayConfig);
        if (result > 0) {
            super.sendRouteUpdateNotify(gatewayConfig);
        }
        return result;
    }

    /**
     * 修改网关路由配置
     *
     * @param gatewayConfig 网关路由配置信息
     * @return 结果
     */
    @Override
    public int updateGatewayConfig(GatewayConfig gatewayConfig) {
        int resultCode = gatewayConfigMapper.updateGatewayConfig(gatewayConfig);
        if (resultCode>0){
            super.sendRouteUpdateNotify(gatewayConfig);
        }
        return resultCode;
    }


    /**
     * 删除网关路由配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGatewayConfigByIds(String ids) {
        String[] idArr = Convert.toStrArray(ids);
        int resultCode = gatewayConfigMapper.deleteGatewayConfigByIds(idArr);
        if (resultCode>0){
           super.sendRouteDelNontify(idArr);
        }
        return resultCode;
    }

    @Override
    public int refreshRoute() {
        super.sendRefreshAllRouteNotify();
        return 1;
    }
}
