package com.ruoyi.web.controller.config;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.GatewayAttrConfig;
import com.ruoyi.system.service.IGatewayAttrConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 网关属性配置（predicate,filter配置） 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
@Controller
@RequestMapping("/system/gatewayAttrConfig")
public class GatewayAttrConfigController extends BaseController
{
    private String prefix = "system/gatewayAttrConfig";
	
	@Autowired
	private IGatewayAttrConfigService gatewayAttrConfigService;
	
	@RequiresPermissions("system:gatewayAttrConfig:view")
	@GetMapping()
	public String gatewayAttrConfig()
	{
	    return prefix + "/gatewayAttrConfig";
	}
	
	/**
	 * 查询网关属性配置（predicate,filter配置）列表
	 */
	@RequiresPermissions("system:gatewayAttrConfig:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GatewayAttrConfig gatewayAttrConfig)
	{
		startPage();
        List<GatewayAttrConfig> list = gatewayAttrConfigService.selectGatewayAttrConfigList(gatewayAttrConfig);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出网关属性配置（predicate,filter配置）列表
	 */
	@RequiresPermissions("system:gatewayAttrConfig:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GatewayAttrConfig gatewayAttrConfig)
    {
    	List<GatewayAttrConfig> list = gatewayAttrConfigService.selectGatewayAttrConfigList(gatewayAttrConfig);
        ExcelUtil<GatewayAttrConfig> util = new ExcelUtil<GatewayAttrConfig>(GatewayAttrConfig.class);
        return util.exportExcel(list, "gatewayAttrConfig");
    }
	
	/**
	 * 新增网关属性配置（predicate,filter配置）
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存网关属性配置（predicate,filter配置）
	 */
	@RequiresPermissions("system:gatewayAttrConfig:add")
	@Log(title = "网关属性配置（predicate,filter配置）", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GatewayAttrConfig gatewayAttrConfig)
	{		
		return toAjax(gatewayAttrConfigService.insertGatewayAttrConfig(gatewayAttrConfig));
	}

	/**
	 * 修改网关属性配置（predicate,filter配置）
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		GatewayAttrConfig gatewayAttrConfig = gatewayAttrConfigService.selectGatewayAttrConfigById(id);
		mmap.put("gatewayAttrConfig", gatewayAttrConfig);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存网关属性配置（predicate,filter配置）
	 */
	@RequiresPermissions("system:gatewayAttrConfig:edit")
	@Log(title = "网关属性配置（predicate,filter配置）", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GatewayAttrConfig gatewayAttrConfig)
	{		
		return toAjax(gatewayAttrConfigService.updateGatewayAttrConfig(gatewayAttrConfig));
	}
	
	/**
	 * 删除网关属性配置（predicate,filter配置）
	 */
	@RequiresPermissions("system:gatewayAttrConfig:remove")
	@Log(title = "网关属性配置（predicate,filter配置）", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(gatewayAttrConfigService.deleteGatewayAttrConfigByIds(ids));
	}
	
}
