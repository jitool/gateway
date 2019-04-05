package com.executor.gateway.model.po;

import com.executor.gateway.model.BaseEntity;
import lombok.Data;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/12 0012 17:43
 * @Description: 网关断言和过滤器配置实体
 */
@Data
public class GatewayAttrConfig extends BaseEntity {
    private String serviceId;
    private Integer type;
    private String attrName;
    private String attrArgs;
    private String desc;
}
