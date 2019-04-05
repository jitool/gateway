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
import com.ruoyi.system.domain.GatewayConfig;
import com.ruoyi.system.service.IGatewayConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 网关路由配置 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-04-03
 */
@Controller
@RequestMapping("/system/gatewayConfig")
public class GatewayConfigController extends BaseController
{
    private String prefix = "system/gatewayConfig";
	
	@Autowired
	private IGatewayConfigService gatewayConfigService;
	
	@RequiresPermissions("system:gatewayConfig:view")
	@GetMapping()
	public String gatewayConfig()
	{
	    return prefix + "/gatewayConfig";
	}
	
	/**
	 * 查询网关路由配置列表
	 */
	@RequiresPermissions("system:gatewayConfig:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GatewayConfig gatewayConfig)
	{
		startPage();
        List<GatewayConfig> list = gatewayConfigService.selectGatewayConfigList(gatewayConfig);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出网关路由配置列表
	 */
	@RequiresPermissions("system:gatewayConfig:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GatewayConfig gatewayConfig)
    {
    	List<GatewayConfig> list = gatewayConfigService.selectGatewayConfigList(gatewayConfig);
        ExcelUtil<GatewayConfig> util = new ExcelUtil<GatewayConfig>(GatewayConfig.class);
        return util.exportExcel(list, "gatewayConfig");
    }
	
	/**
	 * 新增网关路由配置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存网关路由配置
	 */
	@RequiresPermissions("system:gatewayConfig:add")
	@Log(title = "网关路由配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GatewayConfig gatewayConfig)
	{		
		return toAjax(gatewayConfigService.insertGatewayConfig(gatewayConfig));
	}

	/**
	 * 修改网关路由配置
	 */
	@GetMapping("/edit/{serviceId}")
	public String edit(@PathVariable("serviceId") String serviceId, ModelMap mmap)
	{
		GatewayConfig gatewayConfig = gatewayConfigService.selectGatewayConfigById(serviceId);
		mmap.put("gatewayConfig", gatewayConfig);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存网关路由配置
	 */
	@RequiresPermissions("system:gatewayConfig:edit")
	@Log(title = "网关路由配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GatewayConfig gatewayConfig)
	{		
		return toAjax(gatewayConfigService.updateGatewayConfig(gatewayConfig));
	}
	
	/**
	 * 删除网关路由配置
	 */
	@RequiresPermissions("system:gatewayConfig:remove")
	@Log(title = "网关路由配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(gatewayConfigService.deleteGatewayConfigByIds(ids));
	}

	/**
	 * route状态修改
	 */
	@Log(title = "api配置", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:gatewayConfig:edit")
	@PostMapping("/changeState")
	@ResponseBody
	public AjaxResult changeState(GatewayConfig gatewayConfig){
		return toAjax(gatewayConfigService.changeState(gatewayConfig));
	}

	/**
	 *
	 * 功能描述:刷新网关路由配置
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2019/4/4 0004 17:28
	 */
	@RequiresPermissions("system:gatewayConfig:refresh")
	@GetMapping("/refresh")
	@ResponseBody
	public AjaxResult refresh(){
		return toAjax(gatewayConfigService.refreshRoute());
	}
}
