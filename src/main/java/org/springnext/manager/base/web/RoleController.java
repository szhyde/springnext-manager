package org.springnext.manager.base.web;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.service.RoleService;
import org.springnext.manager.core.vo.AjaxMessage;
import org.springnext.manager.core.vo.TablePage;
import org.springnext.manager.core.web.Servlets;

/**
 * 角色控制器
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {
	
	private static final String PAGE_SIZE = "30";
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 角色列表跳转
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "base/role_list";
	}
	
	/**
	 * 角色分页查询
	 * @param pageIndex
	 * @param pageSize
	 * @param sortField
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="search",method = RequestMethod.POST)
	@ResponseBody
	public TablePage search(@RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
			@RequestParam(value = "limit", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "field", defaultValue = "tid") String sortField,
			@RequestParam(value = "direction", defaultValue = "DESC") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Role> rolePage = roleService.searchRoleListPage(searchParams,pageIndex,pageSize,sortField,sortType);
		return TablePage.createTablePage(rolePage, null);
	}
	
	/**
	 * 保存角色
	 * @param role
	 * @param isNew
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid Role role,boolean isNew,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg();
		}
		roleService.saveRole(role);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 删除角色
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(Long[] idList) {
		roleService.deleteRole(idList);
		return AjaxMessage.createSuccessMsg();
	}
}
