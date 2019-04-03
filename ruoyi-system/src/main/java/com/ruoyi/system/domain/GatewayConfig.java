package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 网关路由配置表 t_gateway_config
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
public class GatewayConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 路由唯一id */
	private String serviceId;
	/** 路由url */
	private String url;
	/** 路由后缀Path，紧跟url */
	private String suffixPath;
	/**  */
	private Date createTime;
	/**  */
	private Date updateTime;
	/** 状态 1：启 0：停 */
	private Integer state;

	public void setServiceId(String serviceId) 
	{
		this.serviceId = serviceId;
	}

	public String getServiceId() 
	{
		return serviceId;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getUrl() 
	{
		return url;
	}
	public void setSuffixPath(String suffixPath) 
	{
		this.suffixPath = suffixPath;
	}

	public String getSuffixPath() 
	{
		return suffixPath;
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
            .append("serviceId", getServiceId())
            .append("url", getUrl())
            .append("suffixPath", getSuffixPath())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("state", getState())
            .toString();
    }
}
