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
//import org.springnext.manager.entity.Role;
//import org.springnext.manager.service.RoleService;
//import org.springnext.manager.utils.Servlets;
//
///**
// * 角色控制器
// * @author HyDe
// *
// */
//@Controller
//@RequestMapping(value = "/role")
//public class RoleController {
//	
//	@Autowired
//	private RoleService roleService;
//	
//	/**
//	 * 角色分页查询
//	 * 
//	 * @param pages
//	 * @param model
//	 * @param request
//	 * @param result
//	 * @return
//	 */
//	@RequestMapping(value = "list")
//	public String list(PagesVO pages, Model model,ServletRequest request,BindingResult result) {
//		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//		Page<Role> rolePage = roleService.searchRoleListPage(searchParams,pages.getPageNum()-1,pages.getPageSize(),pages.getOrderField(),pages.getOrderDirection());
//		pages.setTotalCount(Long.valueOf(rolePage.getTotalElements()).intValue());
//		model.addAttribute("roleList", rolePage.getContent());
//		model.addAttribute("totalCount", rolePage.getTotalElements());
//		model.addAttribute("pages", pages);
//		model.addAttribute("searchParams", Servlets.getParametersByPrefix(request, "search_"));
//		return "base/role/list";
//	}
//	
//	/**
//	 * 增加角色跳转
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/add")
//	public String add() {
//		return "base/role/add";
//	}
//	
//	/**
//	 * 保存角色
//	 * @param role
//	 * @param isNew
//	 * @param result
//	 * @return
//	 */
//	@RequestMapping(value = "insert", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxDone insert(@Valid Role role,ServletRequest request,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxDone.createFailureMsg("参数验证失败", request);
//		}
//		roleService.saveRole(role);
//		return AjaxDone.createSuccessMsg("操作成功", request);
//	}
//	
//	@RequestMapping("/edit/{roleId}")
//	public String edit(@PathVariable("roleId") Long roleId, Model model) {
//		Role role = roleService.findRoleByID(roleId);
//
//		model.addAttribute("role", role);
//
//		return "base/role/edit";
//	}
//	
//	@RequestMapping(value = "update", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxDone update(@Valid Role role,ServletRequest request,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxDone.createFailureMsg("参数验证失败", request);
//		}
//		roleService.saveRole(role);
//		return AjaxDone.createSuccessMsg("操作成功", request);
//	}
//	
//	/**
//	 * 删除角色
//	 * @param idList
//	 * @return
//	 */
//	@RequestMapping("/delete/{roleId}")
//	@ResponseBody
//	public AjaxDone delete(@PathVariable("roleId") Long roleId,ServletRequest request) {
//		roleService.deleteRole(new Long[]{roleId});
//		return AjaxDone.createSuccessMsg("删除成功",request);
//	}
//}
