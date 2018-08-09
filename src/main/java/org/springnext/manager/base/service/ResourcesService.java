package org.springnext.manager.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Resources;
import org.springnext.manager.base.repository.jpa.BaseDao;
import org.springnext.manager.base.repository.jpa.ResourcesDao;

/**
 * 资源管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional(readOnly=true)
public class ResourcesService extends BaseService<Resources, Long>{

	@Autowired
	private ResourcesDao resourcesDao;

	@Override
	protected BaseDao<Resources, Long> initBaseDao() {
		return resourcesDao;
	}



}
