package org.springnext.manager.base.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springnext.manager.base.annotation.PermissionsAnnotation;
import org.springnext.manager.base.entity.Group;
import org.springnext.manager.base.service.GroupService;
import org.springnext.manager.base.vo.AjaxMessage;
import org.springnext.manager.base.vo.LayTreeVO;

import com.google.common.collect.Lists;

/**
 * 用户组控制器
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/base/group")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	
	
	/**
	 * 加载所有的树节点
	 * @return
	 */
	@RequestMapping(value = "/getAllGroup")
	@ResponseBody
	public List<LayTreeVO> getAllGroup() {
		List<Group> groupList = groupService.getParentGroup();
		return getChildren(groupList);
	}
	
	/**
	 * 递归查询所有用户组的子集
	 * @param groupList
	 * @return
	 */
	private List<LayTreeVO> getChildren(List<Group> groupList){
		List<LayTreeVO> list = Lists.newArrayList();
		if(groupList!=null) {
			LayTreeVO tempLayTreeVO;
			for (Group group : groupList) {
				if(!group.getIsDelete()) {
					tempLayTreeVO = new LayTreeVO();
					tempLayTreeVO.setId(String.valueOf(group.getTid()));
					if(group.getPid()!=null) {
						tempLayTreeVO.setParentID(String.valueOf(group.getPid()));
					}
					tempLayTreeVO.setName(group.getGroupName());
					tempLayTreeVO.setChildren(getChildren(group.getChildGroup()));
					list.add(tempLayTreeVO);
				}
			}
		}
    	return list;
    }
	
	/**
	 * 选择用户组跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/select")
	public String select( Model model) {
		return "base/group/select";
	}
	
	/**
	 * 跳转到增加页
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseGroupSave",permissionRemark="用户组创建与修改权限",parentPermission="baseUserSearch", url = "/base/group/add",resourceRemark="跳转到增加用户组页")
	@RequestMapping("/add")
	public String add(@RequestParam("groupId") Long tid, Model model) {
		if(tid!=null) {
			model.addAttribute("group", groupService.findOne(tid));
		}
		return "base/group/add";
	}
	
	/**
	 * 保存
	 * @param group
	 * @param result
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseGroupSave",permissionRemark="用户组创建与修改权限",parentPermission="baseUserSearch", url = "/base/group/save",resourceRemark="保存用户组")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid Group group,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg();
		}
		groupService.save(group);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 跳转到修改页
	 * @param id
	 * @param model
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseGroupSave",permissionRemark="用户组创建与修改权限",parentPermission="baseUserSearch", url = "/base/group/edit",resourceRemark="跳转到用户组修改")
	@RequestMapping(value = "edit/{id}")
	public String edit(@PathVariable("id") Long id,Model model) {
		model.addAttribute("group", groupService.findOne(id));
		return "base/group/edit";

	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@PermissionsAnnotation(permission = "baseGroupDelete",permissionRemark="删除用户组权限",parentPermission="baseUserSearch", url = "/base/group/delete",resourceRemark="删除用户组")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(@PathVariable("id") Long id) {
		groupService.deleteByLogic(id);
		return AjaxMessage.createSuccessMsg();
	}
	
}
