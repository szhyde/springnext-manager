package org.springnext.manager.base.controller;
//package org.springnext.manager.controller;
//
//import java.util.Map;
//
//import javax.servlet.ServletRequest;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springnext.manager.core.web.jui.vo.AjaxDone;
//import org.springnext.manager.core.web.jui.vo.PagesVO;
//import org.springnext.manager.entity.Permissions;
//import org.springnext.manager.service.PermissionsService;
//import org.springnext.manager.utils.Servlets;
//
///**
// * 权限控制类
// * @author HyDe
// *
// */
//@Controller
//@RequestMapping(value = "/permissions")
//public class PermissionsController {
//	
//	@Autowired
//	private PermissionsService permissionsService;
//	
//	/**
//	 * 跳转权限列表
//	 * @return
//	 */
//	@RequestMapping(value = "list")
//	public String list(PagesVO pages, Model model,ServletRequest request,BindingResult result) {
//		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		Page<Permissions> permissionsPage = permissionsService.searchPermissionsListPage(searchParams,pages.getPageNum()-1,pages.getPageSize(),pages.getOrderField(),pages.getOrderDirection());
//		pages.setTotalCount(Long.valueOf(permissionsPage.getTotalElements()).intValue());
//		model.addAttribute("permissionsList", permissionsPage.getContent());
//		model.addAttribute("totalCount", permissionsPage.getTotalElements());
//		model.addAttribute("pages", pages);
//		model.addAttribute("searchParams", Servlets.getParametersByPrefix(request, "search_"));
//		return "base/permissions/list";
//	}
//	
//	/**
//	 * 增加角色跳转
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/add")
//	public String add() {
//		return "base/permissions/add";
//	}
//	
//	/**
//	 * 保存角色
//	 * @param role
//	 * @param result
//	 * @return
//	 */
//	@RequestMapping(value = "insert", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxDone insert(@Valid Permissions permissions,ServletRequest request,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxDone.createFailureMsg("参数验证失败", request);
//		}
//		permissionsService.savePermissions(permissions);
//		return AjaxDone.createSuccessMsg("操作成功", request);
//	}
//	
//	@RequestMapping("/edit/{permissionsId}")
//	public String edit(@PathVariable("permissionsId") Long permissionsId, Model model) {
//		Permissions permissions = permissionsService.findPermissionsByID(permissionsId);
//		model.addAttribute("permissions", permissions);
//		return "base/permissions/edit";
//	}
//	
//	/**
//	 * 更新权限
//	 * @param permissions
//	 * @param request
//	 * @param result
//	 * @return
//	 */
//	@RequestMapping(value = "update", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxDone update(@Valid Permissions permissions,ServletRequest request,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxDone.createFailureMsg("参数验证失败", request);
//		}
//		permissionsService.savePermissions(permissions);
//		return AjaxDone.createSuccessMsg("操作成功", request);
//	}
//	
//	/**
//	 * 删除权限
//	 * @param idList
//	 * @return
//	 */
//	@RequestMapping(value = "/delete/{permissionsId}")
//	@ResponseBody
//	public AjaxDone delete(@PathVariable("permissionsId") Long permissionsId,ServletRequest request) {
//		permissionsService.deletePermissions(new Long[]{permissionsId});
//		return AjaxDone.createSuccessMsg("删除成功",request);
//	}
//}
