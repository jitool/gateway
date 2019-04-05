package com.executor.gateway.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: miaoguoxin
 * @Date: 2018/12/11 0011 16:33
 * @Description:
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态 1：启 0：停
     */
    private Integer state;
}
