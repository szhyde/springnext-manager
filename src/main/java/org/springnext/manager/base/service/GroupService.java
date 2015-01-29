package org.springnext.manager.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Group;
import org.springnext.manager.base.repository.jpa.GroupDao;
import org.springnext.manager.base.repository.mybatis.GroupMybatisDao;

import com.google.common.collect.Maps;

/**
 * 用户组
 * @author HyDe
 *
 */
@Service
@Transactional
public class GroupService {
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private GroupMybatisDao groupMyBatisDao;
	
	/**
	 * 按层级取用户组
	 * @param parentId
	 * @return
	 */
	public List<Group> getAllGroup(Long parentId){
		Map<String,Object> parameters = Maps.newHashMap();
		parameters.put("parentId", parentId);
		return groupMyBatisDao.search(parameters);
	}
	
	/**
	 * 保存
	 * @param group
	 */
	public void saveGroup(Group group){
		group.setIsDelete(false);
		groupDao.save(group);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteGroup(Long id) {
		groupDao.updateGroupDeleteByTid(true, id);
	}
}
