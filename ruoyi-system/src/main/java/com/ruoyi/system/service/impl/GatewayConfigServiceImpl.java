package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GatewayConfigMapper;
import com.ruoyi.system.domain.GatewayConfig;
import com.ruoyi.system.service.IGatewayConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 网关路由配置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
@Service
public class GatewayConfigServiceImpl implements IGatewayConfigService 
{
	@Autowired
	private GatewayConfigMapper gatewayConfigMapper;

	/**
     * 查询网关路由配置信息
     * 
     * @param serviceId 网关路由配置ID
     * @return 网关路由配置信息
     */
    @Override
	public GatewayConfig selectGatewayConfigById(String serviceId)
	{
	    return gatewayConfigMapper.selectGatewayConfigById(serviceId);
	}
	
	/**
     * 查询网关路由配置列表
     * 
     * @param gatewayConfig 网关路由配置信息
     * @return 网关路由配置集合
     */
	@Override
	public List<GatewayConfig> selectGatewayConfigList(GatewayConfig gatewayConfig)
	{
	    return gatewayConfigMapper.selectGatewayConfigList(gatewayConfig);
	}
	
    /**
     * 新增网关路由配置
     * 
     * @param gatewayConfig 网关路由配置信息
     * @return 结果
     */
	@Override
	public int insertGatewayConfig(GatewayConfig gatewayConfig)
	{

	    return gatewayConfigMapper.insertGatewayConfig(gatewayConfig);
	}
	
	/**
     * 修改网关路由配置
     * 
     * @param gatewayConfig 网关路由配置信息
     * @return 结果
     */
	@Override
	public int updateGatewayConfig(GatewayConfig gatewayConfig)
	{
	    return gatewayConfigMapper.updateGatewayConfig(gatewayConfig);
	}

	/**
     * 删除网关路由配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGatewayConfigByIds(String ids)
	{
		return gatewayConfigMapper.deleteGatewayConfigByIds(Convert.toStrArray(ids));
	}
	
}
