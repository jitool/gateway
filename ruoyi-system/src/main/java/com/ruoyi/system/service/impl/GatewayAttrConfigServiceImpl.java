package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.system.domain.GatewayAttrConfig;
import com.ruoyi.system.mapper.GatewayAttrConfigMapper;
import com.ruoyi.system.service.AbstractGatewayConfigService;
import com.ruoyi.system.service.IGatewayAttrConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网关属性配置（predicate,filter配置） 服务层实现
 *
 * @author ruoyi
 * @date 2019-04-03
 */
@Service
public class GatewayAttrConfigServiceImpl extends AbstractGatewayConfigService implements IGatewayAttrConfigService {
    @Autowired
    private GatewayAttrConfigMapper gatewayAttrConfigMapper;

    /**
     * 查询网关属性配置（predicate,filter配置）信息
     *
     * @param id 网关属性配置（predicate,filter配置）ID
     * @return 网关属性配置（predicate,filter配置）信息
     */
    @Override
    public GatewayAttrConfig selectGatewayAttrConfigById(Integer id) {
        return gatewayAttrConfigMapper.selectGatewayAttrConfigById(id);
    }

    /**
     * 查询网关属性配置（predicate,filter配置）列表
     *
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 网关属性配置（predicate,filter配置）集合
     */
    @Override
    public List<GatewayAttrConfig> selectGatewayAttrConfigList(GatewayAttrConfig gatewayAttrConfig) {
        return gatewayAttrConfigMapper.selectGatewayAttrConfigList(gatewayAttrConfig);
    }

    /**
     * 新增网关属性配置（predicate,filter配置）
     *
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 结果
     */
    @Override
    public int insertGatewayAttrConfig(GatewayAttrConfig gatewayAttrConfig) {
        int resultCode = gatewayAttrConfigMapper.insertGatewayAttrConfig(gatewayAttrConfig);
        if (resultCode > 0) {
            super.sendRefreshAllRouteNotify();
        }
        return resultCode;
    }


    /**
     * 修改网关属性配置（predicate,filter配置）
     *
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 结果
     */
    @Override
    public int updateGatewayAttrConfig(GatewayAttrConfig gatewayAttrConfig) {
        int resultCode = gatewayAttrConfigMapper.updateGatewayAttrConfig(gatewayAttrConfig);
        if (resultCode > 0) {
            super.sendRefreshAllRouteNotify();
        }
        return resultCode;
    }

    @Override
    public int changeState(GatewayAttrConfig gatewayAttrConfig) {
        return this.updateGatewayAttrConfig(gatewayAttrConfig);
    }

    /**
     * 删除网关属性配置（predicate,filter配置）对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGatewayAttrConfigByIds(String ids) {
        String[] idsArr = Convert.toStrArray(ids);
        int resultCode = gatewayAttrConfigMapper.deleteGatewayAttrConfigByIds(idsArr);
        if (resultCode > 0) {
            super.sendRefreshAllRouteNotify();
        }
        return resultCode;
    }

}
