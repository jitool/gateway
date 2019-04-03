package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GatewayAttrConfigMapper;
import com.ruoyi.system.domain.GatewayAttrConfig;
import com.ruoyi.system.service.IGatewayAttrConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 网关属性配置（predicate,filter配置） 服务层实现
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
@Service
public class GatewayAttrConfigServiceImpl implements IGatewayAttrConfigService 
{
	@Autowired
	private GatewayAttrConfigMapper gatewayAttrConfigMapper;

	/**
     * 查询网关属性配置（predicate,filter配置）信息
     * 
     * @param id 网关属性配置（predicate,filter配置）ID
     * @return 网关属性配置（predicate,filter配置）信息
     */
    @Override
	public GatewayAttrConfig selectGatewayAttrConfigById(Integer id)
	{
	    return gatewayAttrConfigMapper.selectGatewayAttrConfigById(id);
	}
	
	/**
     * 查询网关属性配置（predicate,filter配置）列表
     * 
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 网关属性配置（predicate,filter配置）集合
     */
	@Override
	public List<GatewayAttrConfig> selectGatewayAttrConfigList(GatewayAttrConfig gatewayAttrConfig)
	{
	    return gatewayAttrConfigMapper.selectGatewayAttrConfigList(gatewayAttrConfig);
	}
	
    /**
     * 新增网关属性配置（predicate,filter配置）
     * 
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 结果
     */
	@Override
	public int insertGatewayAttrConfig(GatewayAttrConfig gatewayAttrConfig)
	{
	    return gatewayAttrConfigMapper.insertGatewayAttrConfig(gatewayAttrConfig);
	}
	
	/**
     * 修改网关属性配置（predicate,filter配置）
     * 
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 结果
     */
	@Override
	public int updateGatewayAttrConfig(GatewayAttrConfig gatewayAttrConfig)
	{
	    return gatewayAttrConfigMapper.updateGatewayAttrConfig(gatewayAttrConfig);
	}

	/**
     * 删除网关属性配置（predicate,filter配置）对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGatewayAttrConfigByIds(String ids)
	{
		return gatewayAttrConfigMapper.deleteGatewayAttrConfigByIds(Convert.toStrArray(ids));
	}
	
}
