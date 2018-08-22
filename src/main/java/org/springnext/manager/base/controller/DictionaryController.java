package org.springnext.manager.base.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.annotation.PermissionsAnnotation;
import org.springnext.manager.base.entity.Dictionary;
import org.springnext.manager.base.service.DictionaryService;
import org.springnext.manager.base.utils.Servlets;
import org.springnext.manager.base.vo.AjaxMessage;
import org.springnext.manager.base.vo.LayTableRequestVO;
import org.springnext.manager.base.vo.LayTableResponseVO;

/**
 * 字典控制器
 * 
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/base/dict")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;

	/**
	 * 跳转到列表页
	 * 
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionarySearch",permissionRemark="字典查询权限",parentPermission="", url = "/base/dict/list",resourceRemark="跳转到字典列表")
	@RequestMapping(value = "list")
	public String list(Model model) {
		return "base/dict/list";
	}

	/**
	 * 列表页查询
	 * 
	 * @param pages
	 * @param model
	 * @param request
	 * @param result
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionarySearch",permissionRemark="字典查询权限",parentPermission="", url = "/base/dict/queryList",resourceRemark="字典查询")
	@RequestMapping(value = "queryList")
	public @ResponseBody LayTableResponseVO<Dictionary> queryList(LayTableRequestVO pages, Model model,
			ServletRequest request, BindingResult result) {
		LayTableResponseVO<Dictionary> jqGridResponseVO = new LayTableResponseVO<Dictionary>();
		if(result.hasErrors()){
			jqGridResponseVO.setCode(500);
			jqGridResponseVO.setMsg("参数验证失败");
			return jqGridResponseVO;
		}
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Dictionary> dictionaryPage = dictionaryService.searchListPage(searchParams, pages.toPageRequest());
		jqGridResponseVO.setCount(dictionaryPage.getTotalElements());
		jqGridResponseVO.setData(dictionaryPage.getContent());
		return jqGridResponseVO;
	}

	/**
	 * 跳转到增加
	 * 
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionarySave",permissionRemark="字典创建与修改权限",parentPermission="baseDictionarySearch", url = "/base/dict/add",resourceRemark="跳转到增加字典页")
	@RequestMapping("/add")
	public String add(Model model) {
		return "base/dict/add";
	}

	/**
	 * 保存字典
	 * 
	 * @param dictionary
	 * @param result
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionarySave",permissionRemark="字典创建与修改权限",parentPermission="baseDictionarySearch", url = "/base/dict/save",resourceRemark="保存字典")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid Dictionary dictionary, BindingResult result) {
		if (result.hasErrors()) {
			return AjaxMessage.createFailureMsg();
		}
		dictionaryService.save(dictionary);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 修改字典跳转
	 * 
	 * @param id
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionarySave",permissionRemark="字典创建与修改权限",parentPermission="baseDictionarySearch", url = "/base/dict/edit",resourceRemark="跳转修改字典页")
	@RequestMapping(value = "edit/{id}")
	public String edit(@PathVariable("id") String id,Model model) {
		model.addAttribute("dict", dictionaryService.findOne(id));
		return "base/dict/edit";

	}

	/**
	 * 删除字典
	 * 
	 * @param id
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionaryDelete",permissionRemark="删除字典权限",parentPermission="baseDictionarySearch", url = "/base/dict/delete",resourceRemark="删除字典")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(@PathVariable("id") String id) {
		dictionaryService.deleteByLogic(id);
		return AjaxMessage.createSuccessMsg();

	}
	

	/**
	 * 批量删除字典
	 * 
	 * @param id
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseDictionaryDelete",permissionRemark="删除字典权限",parentPermission="baseDictionarySearch", url = "/base/dict/deleteAll",resourceRemark="批量删除字典")
	@RequestMapping(value = "deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage deleteAll(@RequestParam("ids[]") String... ids) {
		dictionaryService.deleteAllByLogic(ids);
		return AjaxMessage.createSuccessMsg();

	}
	
}
