package org.springnext.manager.base.service;

import java.util.List;
import java.util.Map;

import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.persistence.DynamicSpecifications;
import org.springnext.manager.base.persistence.SearchFilter;
import org.springnext.manager.base.repository.jpa.RoleDao;

import com.google.common.collect.Lists;

/**
 * 角色管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional
@Monitored
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	/**
	 * 按页面传来的查询条件查询角色.
	 */
	public Page<Role> searchRoleListPage(Map<String, Object> searchParams,
			int pageIndex, int pageSize, String sortField, String sortType) {
		
		searchParams.put("EQ_isDelete", Boolean.FALSE);
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Role> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), Role.class);
		Page<Role> page = roleDao.findAll(spec, new PageRequest(
				pageIndex, pageSize, new Sort(Direction.fromString(sortType),
						sortField)));
		return page;
	}
	
	/**
	 * 按用户ID查询角色.
	 */
	public List<Role> searchRoleByUserID(Long userID) {
		List <Role> roleList = roleDao.findRoleByUserId(userID);
		if(roleList == null){
			roleList = Lists.newArrayList();
		}
		return roleList;
	}

	/**
	 * 保存角色
	 * @param role
	 */
	public void saveRole(Role role) {
		role.setIsDelete(false);
		roleDao.save(role);
	}

	/**
	 * 删除角色
	 * @param ids
	 */
	public void deleteRole(Long[] ids) {
		roleDao.updateRoleDeleteByTid(true, ids);
	}

	/**
	 * 取角色
	 * @param roleId
	 * @return
	 */
	public Role findRoleByID(Long roleId) {
		return roleDao.findOne(roleId);
	}

}
