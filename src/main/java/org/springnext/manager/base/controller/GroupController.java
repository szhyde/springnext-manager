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
import org.springnext.manager.base.entity.Group;
import org.springnext.manager.base.service.GroupService;
import org.springnext.manager.base.vo.AjaxMessage;
import org.springnext.manager.base.vo.LayTreeVO;

import com.google.common.collect.Lists;

/**
 * 用户组
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
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/getAllGroup")
	@ResponseBody
	public List<LayTreeVO> getAllGroup() {
		List<Group> groupList = groupService.getParentGroup();
		return getChildren(groupList);
	}
	
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
	 * 增加用户跳转
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(@RequestParam("groupId") Long tid, Model model) {
		if(tid!=null) {
			model.addAttribute("group", groupService.findOne(tid));
		}
		return "base/group/add";
	}
	
	/**
	 * 保存组织结构
	 * @param group
	 * @param isNew
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage insert(@Valid Group group,boolean isNew,BindingResult result) {
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
