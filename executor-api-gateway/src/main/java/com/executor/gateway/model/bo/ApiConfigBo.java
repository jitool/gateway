package com.executor.gateway.model.bo;

import com.executor.gateway.core.constant.AuthTypeEnum;
import com.executor.gateway.core.constant.EnvironmentTypeEnum;
import com.executor.gateway.model.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: miaoguoxin
 * @Date: 2019/3/31 16:24
 * @Description: 只用于网关api缓存的对象
 */
@Data
public class ApiConfigBo implements Serializable {
    private String serviceId;
    private String patternUrl;
    //环境类型 对应com.immouo.gateway.constant.enums.EnvironmentTypeEnum,默认正式环境
    private int environmentType = EnvironmentTypeEnum.PROD.getValue();
    //权限配置,默认需要检查权限
    private int authType = AuthTypeEnum.AUTH.getValue();
    //并发量
    private int replenishRate = 5;
    //容量
    private int burstCapacity = 10;
    //需要校验的字段名json数组字符串（只能校验非空）
    private String validateParams;
    private Integer state;
}
