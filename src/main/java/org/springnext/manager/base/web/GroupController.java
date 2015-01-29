package org.springnext.manager.base.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.entity.Group;
import org.springnext.manager.base.service.GroupService;
import org.springnext.manager.core.vo.AjaxMessage;

/**
 * 用户组
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/group")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * 跳转用户组页面
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.GET)
	public String list() {
		return "base/group_tree";
	}
	
	/**
	 * 懒加载树
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	@ResponseBody
	public List<Group> search(@RequestParam(value = "pid", defaultValue = "0") Long parentId) {
		return groupService.getAllGroup(parentId);
	}
	
	/**
	 * 保存组织结构
	 * @param group
	 * @param isNew
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid Group group,boolean isNew,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg();
		}
		groupService.saveGroup(group);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 删除用户组
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(@PathVariable("id") Long id) {
		groupService.deleteGroup(id);
		return AjaxMessage.createSuccessMsg();
	}
	

}
