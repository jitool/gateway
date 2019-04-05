package com.executor.gateway.core.validate;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringTokenizer;


/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/27 21:28
 * @Description: 默认的contentType类型(application / x - www - form - urlencoded)
 */
@Component
@Slf4j
public class DefaultUrlencodedValidateImpl implements BaseValidateIntf {
    @Override
    public boolean verify(List<String> validateParamsList, String requestParams) {
        StringTokenizer tokenizer = new StringTokenizer(requestParams, "&");
        JSONObject requestParamsObj = new JSONObject();
        while (tokenizer.hasMoreTokens()) {
            String keyVal = tokenizer.nextToken();
            String[] split = keyVal.split("=");
            if (split.length == 2) {
                requestParamsObj.put(split[0], split[1]);
            } else {
                requestParamsObj.put(split[0], "");
            }
        }
        for (String key : validateParamsList) {
            Object value = requestParamsObj.get(key);
            if (value == null) {
                return false;
            }
        }
        return true;
    }
}
