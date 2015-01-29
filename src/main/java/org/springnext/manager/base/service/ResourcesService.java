package org.springnext.manager.base.service;

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
import org.springnext.manager.base.entity.Resources;
import org.springnext.manager.base.repository.jpa.ResourcesDao;
import org.springnext.manager.core.persistence.DynamicSpecifications;
import org.springnext.manager.core.persistence.SearchFilter;

/**
 * 资源管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional
@Monitored
public class ResourcesService {

	@Autowired
	private ResourcesDao resourcesDao;

	/**
	 * 按页面传来的查询条件查询资源.
	 */
	public Page<Resources> searchResourcesListPage(Map<String, Object> searchParams,
			int pageIndex, int pageSize, String sortField, String sortType) {
		
		searchParams.put("EQ_isDelete", Boolean.FALSE);
		
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Resources> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), Resources.class);
		Page<Resources> page = resourcesDao.findAll(spec, new PageRequest(
				pageIndex, pageSize, new Sort(Direction.fromString(sortType),
						sortField)));
		return page;
	}

	/**
	 * 保存资源
	 * @param resources
	 */
	public void saveResources(Resources resources) {
		resources.setIsDelete(false);
		resourcesDao.save(resources);
	}

	/**
	 * 删除资源
	 * @param ids
	 */
	public void deleteResources(Long[] ids) {
		resourcesDao.updateResourcesDeleteByTid(true, ids);
	}

}
