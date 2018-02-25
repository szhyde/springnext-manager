package org.springnext.manager.base.controller;
//package org.springnext.manager.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springnext.manager.core.vo.AjaxMessage;
//import org.springnext.manager.entity.Group;
//import org.springnext.manager.service.GroupService;
//
///**
// * 用户组
// * @author HyDe
// *
// */
//@Controller
//@RequestMapping(value = "/group")
//public class GroupController {
//	
//	@Autowired
//	private GroupService groupService;
//	
//	/**
//	 * 跳转用户组页面
//	 * @return
//	 */
//	@RequestMapping(value = "tree")
//	public String tree(Model model) {
//		List<Group> groupList = groupService.getParentGroup();
//		model.addAttribute("groupList", groupList);
//		return "base/group/tree";
//	}
//	
//	/**
//     * 查看用户组跳转
//     * @param model
//     * @return
//     */
//    @RequestMapping("/view/{id}")
//    public String view(@PathVariable("id") Long id,Model model) {
//        model.addAttribute("group", groupService.findOne(id));
//        return "base/group/view";
//    }
//	
//	/**
//	 * 增加用户跳转
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping("/add/{id}")
//	public String add(@PathVariable("id") Long id,Model model) {
//	    model.addAttribute("group", groupService.findOne(id));
//		return "base/group/add";
//	}
//	
//	/**
//	 * 懒加载树
//	 * @param parentId
//	 * @return
//	 */
//	@RequestMapping(value = "search", method = RequestMethod.GET)
//	@ResponseBody
//	public List<Group> search(@RequestParam(value = "pid", defaultValue = "0") Long parentId) {
//		return groupService.findGroupByParent(parentId);
//	}
//	
//	/**
//	 * 保存组织结构
//	 * @param group
//	 * @param isNew
//	 * @param result
//	 * @return
//	 */
//	@RequestMapping(value = "save", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxMessage save(@Valid Group group,boolean isNew,BindingResult result) {
//		if(result.hasErrors()){
//			return AjaxMessage.createFailureMsg();
//		}
//		groupService.saveGroup(group);
//		return AjaxMessage.createSuccessMsg();
//	}
//	
//	/**
//	 * 删除用户组
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
//	@ResponseBody
//	public AjaxMessage delete(@PathVariable("id") Long id) {
//		groupService.deleteGroup(id);
//		return AjaxMessage.createSuccessMsg();
//	}
//	
//
//}
