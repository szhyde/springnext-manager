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
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.service.PermissionsService;
import org.springnext.manager.core.vo.AjaxMessage;
import org.springnext.manager.core.vo.TablePage;
import org.springnext.manager.core.web.Servlets;

/**
 * 权限控制类
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/permissions")
public class PermissionsController {
	
	private static final String PAGE_SIZE = "30";
	
	@Autowired
	private PermissionsService permissionsService;
	
	/**
	 * 跳转权限列表
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "base/permissions_list";
	}
	
	/**
	 * 权限分页查询
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
		Page<Permissions> permissionsPage = permissionsService.searchPermissionsListPage(searchParams,pageIndex,pageSize,sortField,sortType);
		return TablePage.createTablePage(permissionsPage, null);
	}
	
	/**
	 * 保存权限
	 * @param permissions
	 * @param isNew
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(@Valid Permissions permissions,boolean isNew,BindingResult result) {
		if(result.hasErrors()){
			return AjaxMessage.createFailureMsg();
		}
		permissionsService.savePermissions(permissions);
		return AjaxMessage.createSuccessMsg();
	}
	
	/**
	 * 删除权限
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(Long[] idList) {
		permissionsService.deletePermissions(idList);
		return AjaxMessage.createSuccessMsg();
	}
}
