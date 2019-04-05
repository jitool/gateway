package com.executor.gateway.core;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.util.CommonUtils;
import com.executor.gateway.model.bo.ApiConfigBo;
import com.executor.gateway.model.po.ApiConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/26 0026 15:37
 * @Description: 用于从redis中获取api信息
 */
@Component
public class ApiConfigManager {

    /**
     * api配置map
     */
    private Map<String, ApiConfigBo> apiConfigMap = new HashMap<>();

    public void putConfig(String uri, ApiConfigBo apiConfig) {
        apiConfigMap.put(uri, apiConfig);
    }

    public void delConfig(String uri){
        apiConfigMap.remove(uri);
    }

    public Map<String, ApiConfigBo> getApiConfigMap() {
        return apiConfigMap;
    }

    /**
     * 功能描述:获取 api 配置
     *
     * @param: [routeId:路由id  uri:请求uri]
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/3/26 0026 16:29
     */
    public ApiConfigBo createApi(String uri) {
        uri = uri.replaceAll("//", "/");
        ApiConfigBo apiConfigBo = apiConfigMap.get(uri);
        //如果没查询到可能是模糊地址，需要转换后再次查询
        if (apiConfigBo == null) {
            uri = CommonUtils.convertUrlForMatchingAll(uri);
            apiConfigBo = apiConfigMap.get(uri);
        }
        return apiConfigBo;
    }

    private ApiConfigBo getApiDefinition(Object json) {
        ApiConfig apiConfig = JSON.parseObject(json.toString(), ApiConfig.class);
        ApiConfigBo apiConfigBo = new ApiConfigBo();
        BeanUtils.copyProperties(apiConfig, apiConfigBo);
        //状态不可用就返回Null
        if (ApiConfig.UNUSE_STATE == apiConfig.getState()) {
            apiConfigBo = null;
        }
        return apiConfigBo;
    }
}
