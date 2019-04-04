package com.ruoyi.system.service;

import com.ruoyi.system.domain.GatewayConfig;
import java.util.List;

/**
 * 网关路由配置 服务层
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
public interface IGatewayConfigService 
{
	/**
     * 查询网关路由配置信息
     * 
     * @param serviceId 网关路由配置ID
     * @return 网关路由配置信息
     */
	public GatewayConfig selectGatewayConfigById(String serviceId);
	
	/**
     * 查询网关路由配置列表
     * 
     * @param gatewayConfig 网关路由配置信息
     * @return 网关路由配置集合
     */
	public List<GatewayConfig> selectGatewayConfigList(GatewayConfig gatewayConfig);
	
	/**
     * 新增网关路由配置
     * 
     * @param gatewayConfig 网关路由配置信息
     * @return 结果
     */
	public int insertGatewayConfig(GatewayConfig gatewayConfig);
	
	/**
     * 修改网关路由配置
     * 
     * @param gatewayConfig 网关路由配置信息
     * @return 结果
     */
	public int updateGatewayConfig(GatewayConfig gatewayConfig);
		
	/**
     * 删除网关路由配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGatewayConfigByIds(String ids);
	/**
	 *
	 * 功能描述:通知刷新节点路由配置
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2019/4/4 0004 17:17
	 */
	int refreshRoute();
	
}
