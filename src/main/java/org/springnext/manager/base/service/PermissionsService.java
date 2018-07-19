package org.springnext.manager.base.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.persistence.DynamicSpecifications;
import org.springnext.manager.base.persistence.SearchFilter;
import org.springnext.manager.base.repository.jpa.PermissionsDao;

/**
 * 权限管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional
public class PermissionsService {

	@Autowired
	private PermissionsDao permissionsDao;

	/**
	 * 按页面传来的查询条件查询用户.
	 */
	public Page<Permissions> searchPermissionsListPage(Map<String, Object> searchParams,
			int pageIndex, int pageSize, String sortField, String sortType) {
		
		searchParams.put("EQ_isDelete", Boolean.FALSE);
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Permissions> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), Permissions.class);
		Page<Permissions> page = permissionsDao.findAll(spec, new PageRequest(
				pageIndex, pageSize, new Sort(Direction.fromString(sortType),
						sortField)));
		return page;
	}

	/**
	 * 保存
	 * @param permissions
	 */
	public void savePermissions(Permissions permissions) {
		permissions.setIsDelete(false);
		permissionsDao.save(permissions);
	}

	/**
	 * 删除
	 * @param ids
	 */
	public void deletePermissions(Long[] ids) {
		permissionsDao.updatePermissionsDeleteByTid(true, ids);
	}
	

}
