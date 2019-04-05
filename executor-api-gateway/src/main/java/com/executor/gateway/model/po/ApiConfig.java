package com.executor.gateway.model.po;

import com.executor.gateway.core.constant.AuthTypeEnum;
import com.executor.gateway.core.constant.EnvironmentTypeEnum;
import com.executor.gateway.model.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/9 19:56
 * @Description:
 */
@Data
public class ApiConfig extends BaseEntity {
    public static final int USE_STATE = 1;
    public static final int UNUSE_STATE = 0;
    private String serviceId;
    private String patternUrl;
    private String title;
    private String desc;
    //环境类型 对应com.immouo.gateway.constant.enums.EnvironmentTypeEnum,默认正式环境
    private int environmentType = EnvironmentTypeEnum.PROD.getValue();
    //权限配置,默认需要检查权限
    private int authType = AuthTypeEnum.AUTH.getValue();
    //额外参数(json格式)
    private String extra;
    //并发量
    private int replenishRate = 5;
    //容量
    private int burstCapacity = 10;
    //需要校验的字段名json数组字符串（只能校验非空）
    private String validateParams;
}
