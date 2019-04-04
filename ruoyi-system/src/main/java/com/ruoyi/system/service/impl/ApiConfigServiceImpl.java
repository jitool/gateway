package com.ruoyi.system.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.config.RedisTopicChannelProperties;
import com.ruoyi.common.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class ApiConfigServiceImpl implements IApiConfigService {
    @Autowired
    private ApiConfigMapper apiConfigMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisTopicChannelProperties redisTopicChannelProperties;


    /**
     * 查询api配置信息
     *
     * @param id api配置ID
     * @return api配置信息
     */
    @Override
    public ApiConfig selectApiConfigById(Integer id) {
        return apiConfigMapper.selectApiConfigById(id);
    }

    /**
     * 查询api配置列表
     *
     * @param apiConfig api配置信息
     * @return api配置集合
     */
    @Override
    public List<ApiConfig> selectApiConfigList(ApiConfig apiConfig) {
        return apiConfigMapper.selectApiConfigList(apiConfig);
    }

    /**
     * 新增api配置
     *
     * @param apiConfig api配置信息
     * @return 结果
     */
    @Override
    public int insertApiConfig(ApiConfig apiConfig) {
        int resultCode = apiConfigMapper.insertApiConfig(apiConfig);
        if (resultCode>0){
            this.handleInsertOrUpdateSend(apiConfig.getPatternUrl(),apiConfig);
        }
        return resultCode;
    }



    /**
     * 修改api配置
     *
     * @param apiConfig api配置信息
     * @return 结果
     */
    @Override
    public int updateApiConfig(ApiConfig apiConfig) {
        ApiConfig selectApiConfig = apiConfigMapper.selectApiConfigById(apiConfig.getId());
        if (selectApiConfig == null) {
            return 0;
        }
        int resultCode = apiConfigMapper.updateApiConfig(apiConfig);
        if (resultCode>0){
            this.handleInsertOrUpdateSend(selectApiConfig.getPatternUrl(),apiConfig);
        }
        return resultCode;
    }

    /**
     * 删除api配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteApiConfigByIds(String ids) {
        int resultCode = apiConfigMapper.deleteApiConfigByIds(Convert.toStrArray(ids));
        if (resultCode>0){
            List<ApiConfig> apiConfigs = apiConfigMapper.selectInIds(Convert.toStrArray(ids));
            List<String> urls = apiConfigs.stream().map(ApiConfig::getPatternUrl).collect(Collectors.toList());
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("isDel",true);
            jsonObject.put("patternUrl", urls);
            redisTemplate.convertAndSend(redisTopicChannelProperties.getApiChannel(),jsonObject.toString());
        }
        return resultCode;
    }

    private void handleInsertOrUpdateSend(String patternUrl,ApiConfig apiConfig) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("isDel",false);
        jsonObject.put("patternUrl", Collections.singletonList(patternUrl));
        jsonObject.put("apiConfig",apiConfig);
        redisTemplate.convertAndSend(redisTopicChannelProperties.getApiChannel(),jsonObject.toString());
    }
}
