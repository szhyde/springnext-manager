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
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.service.UserService;
import org.springnext.manager.base.vo.transform.UserVOTransform;
import org.springnext.manager.core.vo.AjaxMessage;
import org.springnext.manager.core.vo.TablePage;
import org.springnext.manager.core.web.Servlets;

/**
 * 用户控制器
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	private static final String PAGE_SIZE = "30";
	
	@Autowired
	private UserService userService;
	
	/**
	 * 用户列表跳转
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "base/user_list";
	}
	
	/**
	 * 用户列表分页查询
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
		Page<User> userPage = userService.searchUserListPage(searchParams,pageIndex,pageSize,sortField,sortType);
		return TablePage.createTablePage(userPage, UserVOTransform.getInstance());
	}
	
	/**
	 * 保存用户
	 * @param user
	 * @param isNew
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid User user,boolean isNew,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg();
		}
		if(isNew){
			userService.createUser(user);
		}else{
			userService.updateUser(user);
		}
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 删除用户
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(Long[] idList) {
		userService.deleteUser(idList);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 检查登录名是否存在
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public AjaxMessage checkLoginName(String loginName) {
		User user = userService.findUserByLoginName(loginName);
		if(user==null){
			return AjaxMessage.createSuccessMsg();
		}else{
			return AjaxMessage.createFailureMsg("用户名己存在");
		}
	}
}
