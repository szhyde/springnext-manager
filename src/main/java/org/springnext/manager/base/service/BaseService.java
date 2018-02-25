/*
 * 文件名：BaseService.java 版权：Copyright by SpringNext 描述： 修改人：HyDe 修改时间：2015年4月13日 跟踪单号：
 */

package org.springnext.manager.base.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * 通用的BaseService类，主要用于透传通用的Repository提供的方法。这个类不是必须继承的，只有需要在上层调用Repository层方法的时候， 可以继承此类，减少无意义的透传方法
 * 
 * @author HyDe
 * @version 2015年4月13日
 * @see BaseService
 * @since
 */
public abstract class BaseService<T, ID extends Serializable>
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
     * Saves a given entity. Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * 
     * @param entity
     * @return the saved entity
     */
    public <S extends T> S save(S entity)
    {
        return getPagingAndSortingRepository().save(entity);
    }

    /**
     * Saves all given entities.
     * 
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException
     *             in case the given entity is (@literal null}.
     */
    public <S extends T> Iterable<S> save(Iterable<S> entities)
    {
        return getPagingAndSortingRepository().save(entities);
    }

    /**
     * Retrieves an entity by its id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    public T findOne(ID id)
    {
        return getPagingAndSortingRepository().findOne(id);
    }

    /**
     * Returns whether an entity with the given id exists.
     * 
     * @param id
     *            must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    public boolean exists(ID id)
    {
        return getPagingAndSortingRepository().exists(id);
    }

    /**
     * Returns all instances of the type.
     * 
     * @return all entities
     */
    public Iterable<T> findAll()
    {
        return getPagingAndSortingRepository().findAll();
    }

    /**
     * Returns all instances of the type with the given IDs.
     * 
     * @param ids
     * @return
     */
    public Iterable<T> findAll(Iterable<ID> ids)
    {
        return getPagingAndSortingRepository().findAll(ids);
    }

    /**
     * Returns the number of entities available.
     * 
     * @return the number of entities
     */
    public long count()
    {
        return getPagingAndSortingRepository().count();
    }

    /**
     * Deletes the entity with the given id.
     * 
     * @param id
     *            must not be {@literal null}.
     * @throws IllegalArgumentException
     *             in case the given {@code id} is {@literal null}
     */
    public void delete(ID id)
    {
        getPagingAndSortingRepository().delete(id);
    }

    /**
     * Deletes a given entity.
     * 
     * @param entity
     * @throws IllegalArgumentException
     *             in case the given entity is (@literal null}.
     */
    public void delete(T entity)
    {
        getPagingAndSortingRepository().delete(entity);
    }

    /**
     * Deletes the given entities.
     * 
     * @param entities
     * @throws IllegalArgumentException
     *             in case the given {@link Iterable} is (@literal null}.
     */
    public void delete(Iterable<? extends T> entities)
    {
        getPagingAndSortingRepository().delete(entities);
    }

    /**
     * Returns all entities sorted by the given options.
     * 
     * @param sort
     * @return all entities sorted by the given options
     */
    public Iterable<T> findAll(Sort sort)
    {
        return getPagingAndSortingRepository().findAll(sort);
    }

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the
     * {@code Pageable} object.
     * 
     * @param pageable
     * @return a page of entities
     */
    public Page<T> findAll(Pageable pageable)
    {
        return getPagingAndSortingRepository().findAll(pageable);
    }

    /**
     * Returns a single entity matching the given {@link Specification}.
     * 
     * @param spec
     * @return
     */
    public T findOne(Specification<T> spec)
    {
        return getJpaSpecificationExecutor().findOne(spec);
    }

    /**
     * Returns all entities matching the given {@link Specification}.
     * 
     * @param spec
     * @return
     */
    public List<T> findAll(Specification<T> spec)
    {
        return getJpaSpecificationExecutor().findAll(spec);
    }

    /**
     * Returns a {@link Page} of entities matching the given {@link Specification}.
     * 
     * @param spec
     * @param pageable
     * @return
     */
    public Page<T> findAll(Specification<T> spec, Pageable pageable)
    {
        return getJpaSpecificationExecutor().findAll(spec, pageable);
    }

    /**
     * Returns all entities matching the given {@link Specification} and {@link Sort}.
     * 
     * @param spec
     * @param sort
     * @return
     */
    public List<T> findAll(Specification<T> spec, Sort sort)
    {
        return getJpaSpecificationExecutor().findAll(spec, sort);
    }

    /**
     * Returns the number of instances that the given {@link Specification} will return.
     * 
     * @param spec
     *            the {@link Specification} to count instances for
     * @return the number of instances
     */
    public long count(Specification<T> spec)
    {
        return getJpaSpecificationExecutor().count(spec);
    }
}
