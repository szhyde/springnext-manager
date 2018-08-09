package org.springnext.manager.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.BaseEntity;
import org.springnext.manager.base.persistence.DynamicSpecifications;
import org.springnext.manager.base.persistence.SearchFilter;
import org.springnext.manager.base.repository.jpa.BaseDao;

import com.google.common.collect.Maps;

/**
 * 通用的BaseService类，主要用于透传通用的Repository提供的方法。这个类不是必须继承的，只有需要在上层调用Repository层方法的时候，
 * 可以继承此类，减少无意义的透传方法
 * 
 * @author HyDe
 * @version 2015年4月13日
 * @see BaseService
 * @since
 */
public abstract class BaseService<T extends BaseEntity, ID extends Serializable> {

	protected abstract BaseDao<T, ID> initBaseDao();

	/**
	 * 对子类实现的initJpaSpecificationExecutor方法回值的值进行空值验证
	 * 
	 * @return Repository的实现
	 * @throws 如果initJpaSpecificationExecutor返回值为空会抛出UninitializedRepositoryException
	 */
	private BaseDao<T, ID> getBaseDao() {
		BaseDao<T, ID> baseDao = initBaseDao();
		if (baseDao == null) {
			throw new UninitializedRepositoryException("initBaseDao方法返回空值");
		}
		return baseDao;
	}

	/**
	 * 保存
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public T save(T entity) {
		return getBaseDao().save(entity);
	}

	/**
	 * 批量保存
	 * 
	 * @param entities
	 * @return
	 */	
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public Iterable<T> save(Iterable<T> entities) {
		return getBaseDao().saveAll(entities);
	}

	/**
	 * 框架的逻辑删除
	 * 
	 * @param id
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void deleteByLogic(ID id) {
		getBaseDao().updateDeleteByTids(Boolean.TRUE, id);
	}

	/**
	 * 框架的逻辑删除
	 * 
	 * @param id
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void deleteAllByLogic(ID[] ids) {
		getBaseDao().updateDeleteAllByTids(Boolean.TRUE, ids);
	}

	/**
	 * 物理删除
	 * 
	 * @param id
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void delete(ID id) {
		getBaseDao().deleteById(id);
	}

	/**
	 * 批量物理删除
	 * 
	 * @param entities
	 */
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public void delete(ID[] ids) {
		getBaseDao().deleteAllByTids(ids);
	}

	/**
	 * 按ID确定数据是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean exists(ID id) {
		return getBaseDao().existsById(id);
	}

	/**
	 * 统计数据库里的表行数
	 * 
	 * @return
	 */
	public long count() {
		return getBaseDao().count();
	}

	/**
	 * 按查询条件统计代码面页数
	 * 
	 * @param spec
	 * @return
	 */
	public long countByAttributesName(String attributesName, String value) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put(attributesName, value);
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		return getBaseDao().count(spec);
	}

	/**
	 * 按查询条件统计全表数据
	 * 
	 * @param spec
	 * @return
	 */
	public long countByAttributesNames(Map<String, Object> searchParams) {
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		return getBaseDao().count(spec);
	}

	/**
	 * 按ID查找
	 * 
	 * @param id
	 * @return
	 */
	public T findOne(ID id) {
		return getBaseDao().findById(id).get();
	}

	/**
	 * 按查询条件取单条数据
	 * 
	 * @param spec
	 * @return
	 */
	public T findOneByAttributesName(String attributesName, String value) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put(attributesName, value);
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		Optional<T> optional = getBaseDao().findOne(spec);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	/**
	 * 按查询条件取全表数据
	 * 
	 * @param spec
	 * @return
	 */
	public T findOneByAttributesNames(Map<String, Object> searchParams) {
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		Optional<T> optional = getBaseDao().findOne(spec);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	/**
	 * 取所有表数据
	 * 
	 * @return
	 */
	public Iterable<T> findAll() {
		return getBaseDao().findAll();
	}

	/**
	 * 按ID批量查找
	 * 
	 * @param ids
	 * @return
	 */
	public Iterable<T> findAll(Iterable<ID> ids) {
		return getBaseDao().findAllById(ids);
	}

	/**
	 * 带排序的取全表数据
	 * 
	 * @param sort
	 * @return
	 */
	public Iterable<T> findAll(Sort sort) {
		return getBaseDao().findAll(sort);
	}

	/**
	 * 按查询条件取全表数据
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findAllByAttributesName(String attributesName, String value, Sort sort) {
		Map<String, Object> searchParams = Maps.newHashMap();
		searchParams.put(attributesName, value);
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		if (sort == null) {
			return findAll(spec);
		}
		return findAll(spec, sort);
	}

	/**
	 * 按查询条件取全表数据
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findAllByAttributesNames(Map<String, Object> searchParams, Sort sort) {
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		if (sort == null) {
			return findAll(spec);
		}
		return findAll(spec, sort);
	}

	/**
	 * 按页面传来的查询条件查询.默认只查询未删除的数据
	 */
	public Page<T> searchListPage(Map<String, Object> searchParams, PageRequest pageRequest) {
		// 增加搜索项，只查询未删除的用户
		if (searchParams.get("EQ_isDelete") == null) {
			searchParams.put("EQ_isDelete", Boolean.FALSE);
		}
		// 提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// 拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values());
		return getBaseDao().findAll(spec, pageRequest);
	}

	/**
	 * 按查询条件取全表分页数据
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findAll(Specification<T> spec, Sort sort) {
		if (spec == null && sort == null) {
			return getBaseDao().findAll();
		} else if (spec == null) {
			return getBaseDao().findAll(sort);
		} else if (sort == null) {
			return getBaseDao().findAll(spec);
		}
		return getBaseDao().findAll(spec, sort);
	}

	/**
	 * 按查询条件取全表数据
	 * 
	 * @param spec
	 * @return
	 */
	public List<T> findAll(Specification<T> spec) {
		if (spec == null) {
			return getBaseDao().findAll();
		}
		return getBaseDao().findAll(spec);
	}

}
