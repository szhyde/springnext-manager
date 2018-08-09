package org.springnext.manager.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.repository.jpa.BaseDao;
import org.springnext.manager.base.repository.jpa.PermissionsDao;

/**
 * 权限管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional(readOnly=true)
public class PermissionsService extends BaseService<Permissions, Long>{

	@Autowired
	private PermissionsDao permissionsDao;

	@Override
	protected BaseDao<Permissions, Long> initBaseDao() {
		return permissionsDao;
	}



}
