package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 网关属性配置（predicate,filter配置）表 t_gateway_attr_config
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
public class GatewayAttrConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** 对应t_gateway_config的service_id */
	private String serviceId;
	/** 配置类型 1：predicate 2：filter */
	private Integer type;
	/** 属性类名称，参考spring cloud gateway 文档 */
	private String attrName;
	/** 属性附属参数 参考spring cloud gateway文档，json格式 */
	private String attrArgs;
	/**  */
	private String desc;
	/** 排序 */
	private Integer sort;
	/**  */
	private Date createTime;
	/**  */
	private Date updateTime;
	/** 状态 1：启 0：停 */
	private Integer state;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setServiceId(String serviceId) 
	{
		this.serviceId = serviceId;
	}

	public String getServiceId() 
	{
		return serviceId;
	}
	public void setType(Integer type) 
	{
		this.type = type;
	}

	public Integer getType() 
	{
		return type;
	}
	public void setAttrName(String attrName) 
	{
		this.attrName = attrName;
	}

	public String getAttrName() 
	{
		return attrName;
	}
	public void setAttrArgs(String attrArgs) 
	{
		this.attrArgs = attrArgs;
	}

	public String getAttrArgs() 
	{
		return attrArgs;
	}
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}

	public String getDesc() 
	{
		return desc;
	}
	public void setSort(Integer sort) 
	{
		this.sort = sort;
	}

	public Integer getSort() 
	{
		return sort;
	}
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	public Date getCreateTime() 
	{
		return createTime;
	}
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() 
	{
		return updateTime;
	}
	public void setState(Integer state) 
	{
		this.state = state;
	}

	public Integer getState() 
	{
		return state;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serviceId", getServiceId())
            .append("type", getType())
            .append("attrName", getAttrName())
            .append("attrArgs", getAttrArgs())
            .append("desc", getDesc())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("state", getState())
            .toString();
    }
}
