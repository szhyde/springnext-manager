package org.springnext.manager.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Group;
import org.springnext.manager.base.repository.jpa.BaseDao;
import org.springnext.manager.base.repository.jpa.GroupDao;

/**
 * 用户组
 * @author HyDe
 *
 */
@Service
@Transactional(readOnly=true)
public class GroupService extends BaseService<Group, Long>{
    
	
	@Autowired
	private GroupDao groupDao;
	
	
	
	/**
	 * 按层级取用户组
	 * @param parentId
	 * @return
	 */
	public List<Group> getParentGroup(){
		return groupDao.getParentGroup();
	}



	@Override
	protected BaseDao<Group, Long> initBaseDao() {
		return groupDao;
	}
	
}
