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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.dto.UserDTO;
import org.springnext.manager.base.entity.User;
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
	
	/**
	 * 用户列表页
	 * 
	 * @param pages
	 * @param model
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list() {
		return "base/user/list";
	}
	
	
	/**
	 * 用户列表页
	 * 
	 * @param pages
	 * @param model
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "queryList")
	public @ResponseBody LayTableResponseVO<UserDTO> queryList(LayTableRequestVO pages, Model model,ServletRequest request,BindingResult result) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<User> userPage = userService.searchUserListPage(searchParams,pages.toPageRequest());
		LayTableResponseVO<UserDTO> jqGridResponseVO = new LayTableResponseVO<UserDTO>();
		jqGridResponseVO.setCount(userPage.getTotalElements());
		jqGridResponseVO.addRows(userPage.getContent(), UserDTO.class);
		return jqGridResponseVO;
	}
	
	/**
	 * 增加用户跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		return "base/user/add";
	}
	
	/**
	 * 保存用户
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage insert(@Valid User user,ServletRequest request,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg("参数验证失败");
		}
		userService.createUser(user);
		return AjaxMessage.createSuccessMsg("操作成功");
	}
	
	@RequestMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId, Model model) {
		User user = userService.findUserByID(userId);

		model.addAttribute("user", user);

		return "base/user/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage update(@Valid User user,ServletRequest request,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg("参数验证失败");
		}
		userService.updateUser(user);
		return AjaxMessage.createSuccessMsg("操作成功");
	}
	
	@RequestMapping("/delete/{userId}")
	@ResponseBody
	public AjaxMessage delete(@PathVariable("userId") Long userId,ServletRequest request) {

		userService.deleteUser(new Long[]{userId});

		return AjaxMessage.createSuccessMsg("删除成功");
	}
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		User user = userService.findUserByID(id);
		model.addAttribute("user", user);
		return "base/user/detail";
	}
	
}
