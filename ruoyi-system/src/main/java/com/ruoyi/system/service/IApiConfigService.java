package com.ruoyi.system.service;

import com.ruoyi.system.domain.ApiConfig;
import java.util.List;

/**
 * api配置 服务层
 * 
 * @author ruoyi
 * @date 2019-03-31
 */
public interface IApiConfigService 
{
	/**
     * 查询api配置信息
     * 
     * @param id api配置ID
     * @return api配置信息
     */
	public ApiConfig selectApiConfigById(Integer id);
	
	/**
     * 查询api配置列表
     * 
     * @param apiConfig api配置信息
     * @return api配置集合
     */
	public List<ApiConfig> selectApiConfigList(ApiConfig apiConfig);
	
	/**
     * 新增api配置
     * 
     * @param apiConfig api配置信息
     * @return 结果
     */
	public int insertApiConfig(ApiConfig apiConfig);
	
	/**
     * 修改api配置
     * 
     * @param apiConfig api配置信息
     * @return 结果
     */
	public int updateApiConfig(ApiConfig apiConfig);
		
	/**
     * 删除api配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteApiConfigByIds(String ids);
	/**
	 *
	 * 功能描述:刷新所有api配置
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2019/4/4 0004 17:52
	 */
	int refreshAllApiConfig();

	/**
	 *
	 * 功能描述:api状态切换
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2019/4/4 0004 18:13
	 */
	int changeState(ApiConfig apiConfig);
}
