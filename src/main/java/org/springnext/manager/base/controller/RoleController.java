package org.springnext.manager.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.annotation.PermissionsAnnotation;
import org.springnext.manager.base.dto.RoleDTO;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.service.PermissionsService;
import org.springnext.manager.base.service.RoleService;
import org.springnext.manager.base.utils.Servlets;
import org.springnext.manager.base.vo.AjaxMessage;
import org.springnext.manager.base.vo.LayTableRequestVO;
import org.springnext.manager.base.vo.LayTableResponseVO;

/**
 * 角色控制器
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/base/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	/**
	 * 跳转到列表页
	 */
	@PermissionsAnnotation(permission = "baseRoleSearch",permissionRemark="角色查询权限",parentPermission="", url = "/base/role/list",resourceRemark="跳转到角色列表")
	@RequestMapping(value = "list")
	public String list(Model model) {
		return "base/role/list";
	}
	
	/**
	 * 跳转选择角色页
	 * 
	 * @return
	 */
	@RequestMapping(value = "select")
	public String select() {
		return "base/role/select";
	}


	/**
	 * 列表页查询
	 * @param pages
	 * @param model
	 * @param request
	 * @param result
	 * @return
	 */
	//公共方法不用权限
	@RequestMapping(value = "queryList")
	public @ResponseBody LayTableResponseVO<RoleDTO> queryList(LayTableRequestVO pages, Model model,
			ServletRequest request, BindingResult result) {
		LayTableResponseVO<RoleDTO> jqGridResponseVO = new LayTableResponseVO<RoleDTO>();
		if(result.hasErrors()){
			jqGridResponseVO.setCode(500);
			jqGridResponseVO.setMsg("参数验证失败");
			return jqGridResponseVO;
		}
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Role> rolePage = roleService.searchListPage(searchParams, pages.toPageRequest());
		jqGridResponseVO.setCount(rolePage.getTotalElements());
		jqGridResponseVO.setData(RoleDTO.transformAllToRoleDTO(rolePage.getContent()));
		return jqGridResponseVO;
	}

	/**
	 * 跳转到增加页
	 * 
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseRoleSave",permissionRemark="角色创建与修改权限",parentPermission="baseRoleSearch", url = "/base/role/add",resourceRemark="跳转到增加角色页")
	@RequestMapping("/add")
	public String add(Model model) {
		return "base/role/add";
	}

	/**
	 * 保存
	 * 
	 * @param role
	 * @param result
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseRoleSave",permissionRemark="角色创建与修改权限",parentPermission="baseRoleSearch", url = "/base/role/save",resourceRemark="保存角色")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid Role role, BindingResult result) {
		if (result.hasErrors()) {
			return AjaxMessage.createFailureMsg();
		}
		roleService.save(role);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 跳转修改页
	 * @param id
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseRoleSave",permissionRemark="角色创建与修改权限",parentPermission="baseRoleSearch", url = "/base/role/edit",resourceRemark="跳转到修改角色页")
	@RequestMapping(value = "edit/{id}")
	public String edit(@PathVariable("id") String id,Model model) {
		model.addAttribute("role", roleService.findOne(id));
		return "base/role/edit";

	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseRoleDelete",permissionRemark="角色删除权限",parentPermission="baseRoleSearch", url = "/base/role/delete",resourceRemark="删除角色")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(@PathVariable("id") String id) {
		roleService.deleteByLogic(id);
		return AjaxMessage.createSuccessMsg();

	}
	

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseRoleDelete",permissionRemark="角色删除权限",parentPermission="baseRoleSearch", url = "/base/role/deleteAll",resourceRemark="批量删除角色")
	@RequestMapping(value = "deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage deleteAll(@RequestParam("ids[]") String... ids) {
		roleService.deleteAllByLogic(ids);
		return AjaxMessage.createSuccessMsg();

	}
	
	/**
	 * 查看角色权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view/{id}")
	public String view(@PathVariable("id") String id,Model model) {
		model.addAttribute("role", roleService.findOne(id));
		return "base/role/edit";

	}
	
	@RequestMapping(value = "set/{id}")
	public String set(@PathVariable("id") String id,Model model) {
		model.addAttribute("permissionsTable", generatorPermissionsTable(roleService.findOne(id).getPermissions()));
		model.addAttribute("roleID", id);
		return "base/role/set";
	}
	
	@RequestMapping(value = "setPermissions")
	@ResponseBody
	public AjaxMessage setPermissions(String id,@RequestParam("permissionsID[]") String... permissionsID) {
		
		Role role = roleService.findOne(id);
		
		role.getPermissions().clear();
		
		Permissions per;
		
		for (String string : permissionsID) {
			per = new Permissions();
			
			per.setTid(string);
			
			role.getPermissions().add(per);
		}
		roleService.save(role);
		
		
		return AjaxMessage.createSuccessMsg();
	}
	
	private String generatorPermissionsTable(List<Permissions> seletePermissions){
		List<Permissions> permissionsList = permissionsService.findAllByAttributesName("NULL_parent", "null", Sort.by(Direction.DESC, "tid"));
		StringBuilder table = new StringBuilder();
		table.append("<table style=\"margin: 0px;border:solid #add9c0; border-width:1px 0px 0px 1px;\"><tbody>");
		for (Permissions permissions : permissionsList) {
			table.append(generatorTableTD(permissions,seletePermissions));
			
		}
		table.append("</tbody></table>");
		return table.toString();
	}
	
	private String generatorTableTD(Permissions permissions,List<Permissions> seletePermissions) {
		StringBuilder td = new StringBuilder();
		td.append("<tr>");
		StringBuilder table = new StringBuilder();
		if(permissions.getChildPermissions()!=null&&permissions.getChildPermissions().size()>0) {
			table.append("<table style=\"margin: 0px;border:solid #add9c0; border-width:0px 0px 0px 1px;width:100%\"><tbody>");
			for (Permissions perm : permissions.getChildPermissions()) {
				table.append(generatorTableTD(perm,seletePermissions));
			}
			table.append("</tbody></table>");
		}
		td.append("<td style=\"border:solid #add9c0; border-width:0px 0px 1px 0px; \" >");
		
		td.append("<input type=\"checkbox\" name=\"permissionsID\" value=\""+permissions.getTid()+"\" title=\""+permissions.getRemark()+"\" lay-skin=\"primary\" "+(seletePermissions.contains(permissions)?"checked":"")+">");
		td.append("</td>");
		
		if(table.length()>0) {
			td.append("<td style=\"border:solid #add9c0; border-width:0px 1px 0px 0px;\">");
			td.append(table.toString());
			td.append("</td>");
		}
		td.append("</tr>");
		return td.toString();
	}
}
