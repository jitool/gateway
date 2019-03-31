package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ApiConfigMapper;
import com.ruoyi.system.domain.ApiConfig;
import com.ruoyi.system.service.IApiConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * api配置 服务层实现
 * 
 * @author ruoyi
 * @date 2019-03-31
 */
@Service
public class ApiConfigServiceImpl implements IApiConfigService 
{
	@Autowired
	private ApiConfigMapper apiConfigMapper;

	/**
     * 查询api配置信息
     * 
     * @param id api配置ID
     * @return api配置信息
     */
    @Override
	public ApiConfig selectApiConfigById(Integer id)
	{
	    return apiConfigMapper.selectApiConfigById(id);
	}
	
	/**
     * 查询api配置列表
     * 
     * @param apiConfig api配置信息
     * @return api配置集合
     */
	@Override
	public List<ApiConfig> selectApiConfigList(ApiConfig apiConfig)
	{
	    return apiConfigMapper.selectApiConfigList(apiConfig);
	}
	
    /**
     * 新增api配置
     * 
     * @param apiConfig api配置信息
     * @return 结果
     */
	@Override
	public int insertApiConfig(ApiConfig apiConfig)
	{
	    return apiConfigMapper.insertApiConfig(apiConfig);
	}
	
	/**
     * 修改api配置
     * 
     * @param apiConfig api配置信息
     * @return 结果
     */
	@Override
	public int updateApiConfig(ApiConfig apiConfig)
	{
	    return apiConfigMapper.updateApiConfig(apiConfig);
	}

	/**
     * 删除api配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteApiConfigByIds(String ids)
	{
		return apiConfigMapper.deleteApiConfigByIds(Convert.toStrArray(ids));
	}
	
}
