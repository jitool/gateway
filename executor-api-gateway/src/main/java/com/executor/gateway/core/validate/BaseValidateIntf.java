package com.executor.gateway.core.validate;

import java.util.List;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/27 0027 16:30
 * @Description: 校验参数基类
 */
public interface BaseValidateIntf {


    boolean verify(List<String> validateParamsList, String requestParams);
}
