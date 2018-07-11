package org.springnext.manager.base.controller;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.dto.UserDTO;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.service.UserService;
import org.springnext.manager.base.utils.Servlets;
import org.springnext.manager.base.vo.JqGridRequestVO;
import org.springnext.manager.base.vo.JqGridResponseVO;

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
	public @ResponseBody JqGridResponseVO<UserDTO> queryList(JqGridRequestVO pages, Model model,ServletRequest request,BindingResult result) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<User> userPage = userService.searchUserListPage(searchParams,pages.toPageRequest());
		JqGridResponseVO<UserDTO> jqGridResponseVO = new JqGridResponseVO<UserDTO>();
		jqGridResponseVO.setPage(pages.getPage());
		jqGridResponseVO.setRecords(userPage.getTotalElements());
		jqGridResponseVO.setTotal(userPage.getTotalElements()/pages.getRows()+userPage.getTotalElements()%pages.getRows());
		jqGridResponseVO.addRows(userPage.getContent(), UserDTO.class);
		return jqGridResponseVO;
	}
	
//	/**
//	 * 增加用户跳转
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/add")
//	public String add(Model model) {
//		return "base/user/add";
//	}
//	
//	/**
//	 * 保存用户
//	 * @param user
//	 * @param result
//	 * @return
//	 */
//	@RequestMapping(value = "insert", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxDone insert(@Valid User user,ServletRequest request,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxDone.createFailureMsg("参数验证失败", request);
//		}
//		userService.createUser(user);
//		return AjaxDone.createSuccessMsg("操作成功", request);
//	}
//	
//	@RequestMapping("/edit/{userId}")
//	public String edit(@PathVariable("userId") Long userId, Model model) {
//		User user = userService.findUserByID(userId);
//
//		model.addAttribute("user", user);
//
//		return "base/user/edit";
//	}
//	
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxDone update(@Valid User user,ServletRequest request,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxDone.createFailureMsg("参数验证失败", request);
//		}
//		userService.updateUser(user);
//		return AjaxDone.createSuccessMsg("操作成功", request);
//	}
//	
//	@RequestMapping("/delete/{userId}")
//	@ResponseBody
//	public AjaxDone delete(@PathVariable("userId") Long userId,ServletRequest request) {
//
//		userService.deleteUser(new Long[]{userId});
//
//		return AjaxDone.createSuccessMsg("删除成功",request);
//	}
//	
//	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
//	public String detail(@PathVariable("id") Long id, Model model) {
//		User user = userService.findUserByID(id);
//		model.addAttribute("user", user);
//		return "base/user/detail";
//	}
	
}
