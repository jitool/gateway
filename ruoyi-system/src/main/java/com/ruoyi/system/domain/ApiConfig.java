package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * api配置表 t_api_config
 * 
 * @author ruoyi
 * @date 2019-03-31
 */
public class ApiConfig extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private Integer id;
	/** 对应路由id */
	private String serviceId;
	/** 环境 1:debug 2:测试 3:正式 4:维护 */
	private Integer environment;
	/** 请求方法,0:ALL 1:GET 2:POST  */
	private Integer methodType;
	/** 标题 */
	private String title;
	/** 接口描述 */
	private String desc;
	/** 需要校验的字段(只能校验非null),ex:["a","b"] */
	private String validateParams;
	/** 权限配置 0：不需要权限 1：需要 */
	private Integer authType;
	/** 接口地址 */
	private String patternUrl;
	/** 并发量 */
	private Integer replenishRate;
	/** 并发容量 */
	private Integer burstCapacity;
	/** 是否启用 1：启 0：停 */
	private Integer state;
	/**  */
	private Date createTime;
	/**  */
	private Date updateTime;
	/** 额外参数(json格式) */
	private String extra;

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
	public void setEnvironment(Integer environment) 
	{
		this.environment = environment;
	}

	public Integer getEnvironment() 
	{
		return environment;
	}
	public void setMethodType(Integer methodType) 
	{
		this.methodType = methodType;
	}

	public Integer getMethodType() 
	{
		return methodType;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getTitle() 
	{
		return title;
	}
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}

	public String getDesc() 
	{
		return desc;
	}
	public void setValidateParams(String validateParams) 
	{
		this.validateParams = validateParams;
	}

	public String getValidateParams() 
	{
		return validateParams;
	}
	public void setAuthType(Integer authType) 
	{
		this.authType = authType;
	}

	public Integer getAuthType() 
	{
		return authType;
	}
	public void setPatternUrl(String patternUrl) 
	{
		this.patternUrl = patternUrl;
	}

	public String getPatternUrl() 
	{
		return patternUrl;
	}
	public void setReplenishRate(Integer replenishRate) 
	{
		this.replenishRate = replenishRate;
	}

	public Integer getReplenishRate() 
	{
		return replenishRate;
	}
	public void setBurstCapacity(Integer burstCapacity) 
	{
		this.burstCapacity = burstCapacity;
	}

	public Integer getBurstCapacity() 
	{
		return burstCapacity;
	}
	public void setState(Integer state) 
	{
		this.state = state;
	}

	public Integer getState() 
	{
		return state;
	}
	public void setExtra(String extra)
	{
		this.extra = extra;
	}

	public String getExtra() 
	{
		return extra;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serviceId", getServiceId())
            .append("environment", getEnvironment())
            .append("methodType", getMethodType())
            .append("title", getTitle())
            .append("desc", getDesc())
            .append("validateParams", getValidateParams())
            .append("authType", getAuthType())
            .append("patternUrl", getPatternUrl())
            .append("replenishRate", getReplenishRate())
            .append("burstCapacity", getBurstCapacity())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("extra", getExtra())
            .toString();
    }
}
