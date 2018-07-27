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
import org.springnext.manager.base.entity.Dictionary;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.service.DictionaryService;
import org.springnext.manager.base.utils.Servlets;
import org.springnext.manager.base.vo.AjaxMessage;
import org.springnext.manager.base.vo.LayTableRequestVO;
import org.springnext.manager.base.vo.LayTableResponseVO;

/**
 * 字典控制器
 * @author HyDe
 *
 */
@Controller
@RequestMapping(value = "/base/dict")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryService;
	
	/**
	 * 字典列表页
	 * 
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list() {
		return "base/dict/list";
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
	public @ResponseBody LayTableResponseVO<Dictionary> queryList(LayTableRequestVO pages, Model model,ServletRequest request,BindingResult result) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Dictionary> dictionaryPage = dictionaryService.searchListPage(searchParams,pages.toPageRequest());
		LayTableResponseVO<Dictionary> jqGridResponseVO = new LayTableResponseVO<Dictionary>();
		jqGridResponseVO.setCount(dictionaryPage.getTotalElements());
		jqGridResponseVO.setData(dictionaryPage.getContent());
		return jqGridResponseVO;
	}
	
}
