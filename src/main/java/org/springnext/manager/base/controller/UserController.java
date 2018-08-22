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
import org.springnext.manager.base.dto.UserDTO;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.service.DictionaryService;
import org.springnext.manager.base.service.UserService;
import org.springnext.manager.base.utils.Servlets;
import org.springnext.manager.base.vo.AjaxMessage;
import org.springnext.manager.base.vo.LayTableRequestVO;
import org.springnext.manager.base.vo.LayTableResponseVO;

/**
 * 用户控制器
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/base/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 跳转到列表页
	 * 
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserSearch",permissionRemark="用户查询权限",parentPermission="", url = "/base/user/list",resourceRemark="跳转到用户列表")
	@RequestMapping(value = "list")
	public String list(Model model) {
		model.addAttribute("userStatus", dictionaryService.findAllByAttributesName("EQ_typeValue", "userStatus", null));
		return "base/user/list";
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
	@PermissionsAnnotation(permission = "baseUserSearch",permissionRemark="用户查询权限",parentPermission="", url = "/base/user/queryList",resourceRemark="用户列表搜索")
	@RequestMapping(value = "queryList")
	public @ResponseBody LayTableResponseVO<UserDTO> queryList(LayTableRequestVO pages, Model model,ServletRequest request,BindingResult result) {
		LayTableResponseVO<UserDTO> jqGridResponseVO = new LayTableResponseVO<UserDTO>();
		if(result.hasErrors()){
			jqGridResponseVO.setCode(500);
			jqGridResponseVO.setMsg("参数验证失败");
			return jqGridResponseVO;
		}
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<User> userPage = userService.searchListPage(searchParams, pages.toPageRequest());
		jqGridResponseVO.setCount(userPage.getTotalElements());
		Map<String, String> userStatusMap = dictionaryService.getDictionaryMap("userStatus");
		jqGridResponseVO.setData(UserDTO.transformAllToUserDTO(userPage.getContent(),userStatusMap));
		return jqGridResponseVO;
	}
	
	/**
	 * 跳转到增加页
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserCreate",permissionRemark="用户创建权限",parentPermission="baseUserSearch", url = "/base/user/add",resourceRemark="跳转到增加用户页")
	@RequestMapping("/add")
	public String add(Model model) {
		model.addAttribute("userStatus", dictionaryService.findAllByAttributesName("EQ_typeValue", "userStatus", null));
		return "base/user/add";
	}
	
	/**
	 * 增加
	 * @param user
	 * @param result
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserCreate",permissionRemark="用户创建权限",parentPermission="baseUserSearch", url = "/base/user/create",resourceRemark="增加用户")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage create(@Valid User user,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg("参数验证失败");
		}
		userService.createUser(user);
		
		return AjaxMessage.createSuccessMsg("操作成功");
	}
	
	/**
	 * 跳转到修改页
	 * @param userId
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserEdit",permissionRemark="用户修改权限",parentPermission="baseUserSearch", url = "/base/user/edit",resourceRemark="跳转到修改用户页")
	@RequestMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") String userId, Model model) {
		User user = userService.findOne(userId);
		model.addAttribute("userStatus", dictionaryService.findAllByAttributesName("EQ_typeValue", "userStatus", null));
		Map<String, String> userStatusMap = dictionaryService.getDictionaryMap("userStatus");
		model.addAttribute("user", UserDTO.transformToUserDTO(user, userStatusMap));

		return "base/user/edit";
	}
	
	/**
	 * 修改
	 * @param user
	 * @param result
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserEdit",permissionRemark="用户修改权限",parentPermission="baseUserSearch", url = "/base/user/update",resourceRemark="修改用户")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage update(@Valid User user,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg("参数验证失败");
		}
		userService.updateUser(user);
		return AjaxMessage.createSuccessMsg("操作成功");
	}
	
	/**
	 * 删除
	 * @param userId
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserDelete",permissionRemark="用户删除权限",parentPermission="baseUserSearch", url = "/base/user/delete",resourceRemark="删除用户")
	@RequestMapping("/delete/{userId}")
	@ResponseBody
	public AjaxMessage delete(@PathVariable("userId") String userId) {

		userService.delete(userId);
		return AjaxMessage.createSuccessMsg("删除成功");
	}
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserDelete",permissionRemark="用户删除权限",parentPermission="baseUserSearch", url = "/base/user/deleteAll",resourceRemark="批量删除用户")
	@RequestMapping(value = "deleteAll", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage deleteAll(@RequestParam("ids[]") String... ids) {
		userService.deleteAllByLogic(ids);
		return AjaxMessage.createSuccessMsg();

	}
	
	/**
	 * 跳转到重置密码页
	 * @param id
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserResetpass",permissionRemark="用户重置密码权限",parentPermission="baseUserSearch", url = "/base/user/resetpass",resourceRemark="跳转到重置密码页")
	@RequestMapping(value = "/resetpass/{id}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable("id") String id, Model model) {
		User user = userService.findOne(id);
		model.addAttribute("user", user);
		return "base/user/resetpass";
	}
	
	/**
	 * 重置密码
	 * @param tid
	 * @param newPassword
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseUserResetpass",permissionRemark="用户重置密码权限",parentPermission="baseUserSearch", url = "/base/user/changepass",resourceRemark="重置密码")
	@RequestMapping(value = "changepass")
	@ResponseBody
	public AjaxMessage changePassword(String tid,String newPassword) {
		userService.changePassword(tid, newPassword);
		return AjaxMessage.createSuccessMsg("修改成功");
	}
	
	/**
	 * 跳转到重置自己的密码页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resetselfpass", method = RequestMethod.GET)
	public String resetPassword(Model model) {
		return "base/user/resetselfpass";
	}
	
	/**
	 * 重置自己的密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "changeselfpass")
	@ResponseBody
	public AjaxMessage changeSelfPassword(String oldPassword,String newPassword) {
		if(userService.changeSelfPassword(oldPassword, newPassword)) {
			return AjaxMessage.createSuccessMsg("修改成功");
		}
		return AjaxMessage.createFailureMsg("原密码不正确");
	}
	
}
