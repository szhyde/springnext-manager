package org.springnext.manager.base.service;

import java.util.HashMap;
import java.util.Map;

import org.springnext.manager.core.security.service.AbstractFilterChainDefinitionsService;

/**
 * 简单实现shiro过滤链动态加载，来源从数据库中加载
 * @author HyDe
 *
 */
public class SimpleFilterChainDefinitionsService extends
		AbstractFilterChainDefinitionsService {
	
	@Override
	public Map<String, String> initOtherPermission() {
		// TODO Auto-generated method stub
		return new HashMap<String, String>();
	}

}
