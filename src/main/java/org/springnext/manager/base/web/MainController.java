package org.springnext.manager.base.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.service.UserService;
import org.springnext.manager.core.vo.AjaxMessage;

/**
 * 登陆后主页面
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/main")
public class MainController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转主页面工作台
	 * @return
	 */
	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String list() {
		return "main/dashboard";
	}
	
	/**
	 * 跳转修改密码
	 * @return
	 */
	@RequestMapping(value = "toChangePassword", method = RequestMethod.GET)
	public String toChangePassword() {
		return "main/change_password";
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage changePassword(@RequestParam(value = "oldPassword", defaultValue = "") String oldPassword,
			@RequestParam(value = "newPassword", defaultValue = "") String newPassword) {
		if(StringUtils.equals("", oldPassword)||StringUtils.equals("", newPassword)){
			return AjaxMessage.createFailureMsg();
		}
		userService.changePassword(oldPassword, newPassword);
		return AjaxMessage.createSuccessMsg();
	}
}
