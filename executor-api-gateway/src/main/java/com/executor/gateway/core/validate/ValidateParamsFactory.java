package com.executor.gateway.core.validate;

import com.alibaba.fastjson.JSON;
import com.executor.gateway.core.util.SpringBeanUtils;
import com.executor.gateway.core.validate.ApplicationJsonValidateImpl;
import com.executor.gateway.core.validate.BaseValidateIntf;
import com.executor.gateway.core.validate.DefaultUrlencodedValidateImpl;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/27 0027 16:19
 * @Description: 参数校验工厂加载类
 */
@Component
@Slf4j
public class ValidateParamsFactory {
    //装载校验类的map
    private static final Map<String, Class> VALIDATE_CLASS_MAP = new HashMap<>();
    static {
        VALIDATE_CLASS_MAP.put(MediaType.APPLICATION_JSON_VALUE, ApplicationJsonValidateImpl.class);
        VALIDATE_CLASS_MAP.put(MediaType.APPLICATION_JSON_UTF8_VALUE,ApplicationJsonValidateImpl.class);
        VALIDATE_CLASS_MAP.put(MediaType.APPLICATION_FORM_URLENCODED_VALUE, DefaultUrlencodedValidateImpl.class);
    }
    /**
     * 功能描述:校验参数
     *
     * @param:
     * @return:
     * @auther: miaoguoxin
     * @date: 2019/3/27 0027 16:27
     */
    public boolean verifyParams(String contentType, String validateParamsJson, String requestParams) {
        try {
            if (Strings.isNullOrEmpty(requestParams)){
                log.warn("需要校验的参数集合：{}",validateParamsJson);
                return false;
            }
            BaseValidateIntf validateIntf = this.doGetValidateBean(contentType);
            if (validateIntf == null) {
                log.warn("找不到对应的参数处理类");
                return false;
            }
            List<String> validateParamsList = JSON.parseArray(validateParamsJson, String.class);
            return validateIntf.verify(validateParamsList, requestParams);
        } catch (Exception e) {
            log.error("处理参数校验异常",e);
            return false;
        }
    }

    private BaseValidateIntf doGetValidateBean(String contentType){
        Class aClass = VALIDATE_CLASS_MAP.get(contentType);
        if (aClass==null){
            return null;
        }
        return (BaseValidateIntf)SpringBeanUtils.getBean(aClass);
    }
}
