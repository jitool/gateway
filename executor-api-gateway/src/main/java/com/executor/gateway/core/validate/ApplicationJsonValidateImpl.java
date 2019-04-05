package com.executor.gateway.core.validate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/27 0027 16:31
 * @Description:
 */
@Component
public class ApplicationJsonValidateImpl implements BaseValidateIntf {
    @Override
    public boolean verify(List<String> validateParamsList, String requestParams) {
        JSONObject requestParamsObj = JSON.parseObject(requestParams);
        for (String key : validateParamsList) {
            Object value = requestParamsObj.get(key);
            if (value == null) {
                return false;
            }
        }
        return true;
    }
}
