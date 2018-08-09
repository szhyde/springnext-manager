package org.springnext.manager.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.repository.jpa.BaseDao;
import org.springnext.manager.base.repository.jpa.RoleDao;

/**
 * 角色管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional(readOnly=true)
public class RoleService extends BaseService<Role, Long>{

	@Autowired
	private RoleDao roleDao;

	@Override
	protected BaseDao<Role, Long> initBaseDao() {
		return roleDao;
	}



}
