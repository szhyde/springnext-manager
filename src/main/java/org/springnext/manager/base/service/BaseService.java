/*
 * 文件名：BaseService.java 版权：Copyright by SpringNext 描述： 修改人：HyDe 修改时间：2015年4月13日 跟踪单号：
 */

package org.springnext.manager.base.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springnext.manager.base.entity.BaseEntity;
import org.springnext.manager.base.persistence.DynamicSpecifications;
import org.springnext.manager.base.persistence.SearchFilter;


/**
 * 通用的BaseService类，主要用于透传通用的Repository提供的方法。这个类不是必须继承的，只有需要在上层调用Repository层方法的时候， 可以继承此类，减少无意义的透传方法
 * 
 * @author HyDe
 * @version 2015年4月13日
 * @see BaseService
 * @since
 */
public abstract class BaseService<T extends BaseEntity, ID extends Serializable>
{
    protected abstract PagingAndSortingRepository<T, ID> initPagingAndSortingRepository();

    protected abstract JpaSpecificationExecutor<T> initJpaSpecificationExecutor();

    /**
     * 对子类实现的initPagingAndSortingRepository方法回值的值进行空值验证
     * 
     * @return Repository的实现
     * @throws 如果initPagingAndSortingRepository返回值为空会抛出UninitializedRepositoryException
     */
    private PagingAndSortingRepository<T, ID> getPagingAndSortingRepository()
    {
        PagingAndSortingRepository<T, ID> repository = initPagingAndSortingRepository();
        if (repository == null)
        {
            throw new UninitializedRepositoryException("initPagingAndSortingRepository方法返回空值");
        }
        return repository;
    }

    /**
     * 对子类实现的initJpaSpecificationExecutor方法回值的值进行空值验证
     * 
     * @return Repository的实现
     * @throws 如果initJpaSpecificationExecutor返回值为空会抛出UninitializedRepositoryException
     */
    private JpaSpecificationExecutor<T> getJpaSpecificationExecutor()
    {
        JpaSpecificationExecutor<T> executor = initJpaSpecificationExecutor();
        if (executor == null)
        {
            throw new UninitializedRepositoryException("initJpaSpecificationExecutor方法返回空值");
        }
        return executor;
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    public T save(T entity)
    {
        return getPagingAndSortingRepository().save(entity);
    }

    /**
     * 批量保存
     * @param entities
     * @return
     */
    public Iterable<T> save(Iterable<T> entities)
    {
        return getPagingAndSortingRepository().saveAll(entities);
    }

    /**
     * 按ID查找
     * @param id
     * @return
     */
    public T findOne(ID id)
    {
        return getPagingAndSortingRepository().findById(id).get();
    }

    /**
     * 按ID确定数据是否存在
     * @param id
     * @return
     */
    public boolean exists(ID id)
    {
        return getPagingAndSortingRepository().existsById(id);
    }

    /**
     * 取所有表数据
     * @return
     */
    public Iterable<T> findAll()
    {
        return getPagingAndSortingRepository().findAll();
    }

    /**
     * 按ID批量查找
     * @param ids
     * @return
     */
    public Iterable<T> findAll(Iterable<ID> ids)
    {
        return getPagingAndSortingRepository().findAllById(ids);
    }

    /**
     * 统计数据库里的表行数
     * @return
     */
    public long count()
    {
        return getPagingAndSortingRepository().count();
    }

    /**
     * 框架的逻辑删除
     * @param id
     */
    @SuppressWarnings("unchecked")
	public void deleteByLogic(ID id)
    {
    	BaseEntity entity = findOne(id);
    	entity.setIsDelete(true);
        save((T)entity);
    }
    
    /**
     * 物理删除
     * @param id
     */
	public void delete(ID id)
    {
    	getPagingAndSortingRepository().deleteById(id);
    }

	/**
	 * 批量物理删除
	 * @param entities
	 */
    public void delete(Iterable<? extends T> entities)
    {
        getPagingAndSortingRepository().deleteAll(entities);
    }

    /**
     * 带排序的取全表数据
     * @param sort
     * @return
     */
    public Iterable<T> findAll(Sort sort)
    {
        return getPagingAndSortingRepository().findAll(sort);
    }

    /**
     * 带分页的取全表数据
     * @param pageable
     * @return
     */
    public Page<T> findAll(Pageable pageable)
    {
        return getPagingAndSortingRepository().findAll(pageable);
    }

    /**
     * 按查询条件取单条数据
     * @param spec
     * @return
     */
    public T findOne(Specification<T> spec)
    {
        return getJpaSpecificationExecutor().findOne(spec).get();
    }

    /**
     * 按查询条件取全表数据
     * @param spec
     * @return
     */
    public List<T> findAll(Specification<T> spec)
    {
        return getJpaSpecificationExecutor().findAll(spec);
    }

    /**
     * 按查询条件分页取出表数据
     * @param spec
     * @param pageable
     * @return
     */
    public Page<T> findAll(Specification<T> spec, Pageable pageable)
    {
        return getJpaSpecificationExecutor().findAll(spec, pageable);
    }

    /**
     * 按查询条件排序后取数据有数据
     * @param spec
     * @param sort
     * @return
     */
    public List<T> findAll(Specification<T> spec, Sort sort)
    {
        return getJpaSpecificationExecutor().findAll(spec, sort);
    }

    /**
     * 按查询条件统计代码面页数
     * @param spec
     * @return
     */
    public long count(Specification<T> spec)
    {
        return getJpaSpecificationExecutor().count(spec);
    }
    
    /**
	 * 按页面传来的查询条件查询.
	 */
	public Page<T> searchListPage(Map<String, Object> searchParams,PageRequest pageRequest) {
		//增加搜索项，只查询未删除的用户
		//searchParams.put("EQ_isDelete", Boolean.FALSE);
		//提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		//拼接查询条件
		Specification<T> spec = DynamicSpecifications.bySearchFilter(
				filters.values());
		return getJpaSpecificationExecutor().findAll(spec, pageRequest);
	}
}
