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
import com.ruoyi.system.domain.ApiConfig;
import com.ruoyi.system.service.IApiConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * api配置 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-03-31
 */
@Controller
@RequestMapping("/system/apiConfig")
public class ApiConfigController extends BaseController
{
    private String prefix = "system/apiConfig";
	
	@Autowired
	private IApiConfigService apiConfigService;
	
	@RequiresPermissions("system:apiConfig:view")
	@GetMapping()
	public String apiConfig()
	{
	    return prefix + "/apiConfig";
	}
	
	/**
	 * 查询api配置列表
	 */
	@RequiresPermissions("system:apiConfig:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ApiConfig apiConfig)
	{
		startPage();
        List<ApiConfig> list = apiConfigService.selectApiConfigList(apiConfig);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出api配置列表
	 */
	@RequiresPermissions("system:apiConfig:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ApiConfig apiConfig)
    {
    	List<ApiConfig> list = apiConfigService.selectApiConfigList(apiConfig);
        ExcelUtil<ApiConfig> util = new ExcelUtil<ApiConfig>(ApiConfig.class);
        return util.exportExcel(list, "apiConfig");
    }
	
	/**
	 * 新增api配置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存api配置
	 */
	@RequiresPermissions("system:apiConfig:add")
	@Log(title = "api配置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ApiConfig apiConfig)
	{		
		return toAjax(apiConfigService.insertApiConfig(apiConfig));
	}

	/**
	 * 修改api配置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		ApiConfig apiConfig = apiConfigService.selectApiConfigById(id);
		mmap.put("apiConfig", apiConfig);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存api配置
	 */
	@RequiresPermissions("system:apiConfig:edit")
	@Log(title = "api配置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ApiConfig apiConfig)
	{		
		return toAjax(apiConfigService.updateApiConfig(apiConfig));
	}
	
	/**
	 * 删除api配置
	 */
	@RequiresPermissions("system:apiConfig:remove")
	@Log(title = "api配置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(apiConfigService.deleteApiConfigByIds(ids));
	}

	/**
	 * api状态修改
	 */
	@Log(title = "api配置", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:apiConfig:edit")
	@PostMapping("/changeState")
	@ResponseBody
	public AjaxResult changeState(ApiConfig apiConfig){
		return toAjax(apiConfigService.changeState(apiConfig));
	}
	/**
	 *
	 * 功能描述:api全量刷新
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2019/4/4 0004 18:03
	 */
	@RequiresPermissions("system:apiConfig:refresh")
	@GetMapping("/refresh")
	@ResponseBody
	public AjaxResult refresh(){
		return toAjax(apiConfigService.refreshAllApiConfig());
	}
}
