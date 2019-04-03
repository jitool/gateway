package com.ruoyi.system.service;

import com.ruoyi.system.domain.GatewayAttrConfig;
import java.util.List;

/**
 * 网关属性配置（predicate,filter配置） 服务层
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
public interface IGatewayAttrConfigService 
{
	/**
     * 查询网关属性配置（predicate,filter配置）信息
     * 
     * @param id 网关属性配置（predicate,filter配置）ID
     * @return 网关属性配置（predicate,filter配置）信息
     */
	public GatewayAttrConfig selectGatewayAttrConfigById(Integer id);
	
	/**
     * 查询网关属性配置（predicate,filter配置）列表
     * 
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 网关属性配置（predicate,filter配置）集合
     */
	public List<GatewayAttrConfig> selectGatewayAttrConfigList(GatewayAttrConfig gatewayAttrConfig);
	
	/**
     * 新增网关属性配置（predicate,filter配置）
     * 
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 结果
     */
	public int insertGatewayAttrConfig(GatewayAttrConfig gatewayAttrConfig);
	
	/**
     * 修改网关属性配置（predicate,filter配置）
     * 
     * @param gatewayAttrConfig 网关属性配置（predicate,filter配置）信息
     * @return 结果
     */
	public int updateGatewayAttrConfig(GatewayAttrConfig gatewayAttrConfig);
		
	/**
     * 删除网关属性配置（predicate,filter配置）信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGatewayAttrConfigByIds(String ids);
	
}
