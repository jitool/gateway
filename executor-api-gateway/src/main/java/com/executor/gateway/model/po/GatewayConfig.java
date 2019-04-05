package com.executor.gateway.model.po;

import com.executor.gateway.model.BaseEntity;
import lombok.Data;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/11 0011 14:53
 * @Description: 路由配置实体
 */
@Data
public class GatewayConfig extends BaseEntity {
    private String serviceId;
    private String url;
    private String suffixPath;
}
