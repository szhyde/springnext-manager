package org.springnext.manager.base.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * DAO基类，封装了spring提供的JpaRepository与JpaSpecificationExecutor，同时提供项目自带的逻辑删除方法
 * 
 * @author HyDe
 * @param <T>
 *
 */
@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	@Modifying
	@Query(value = "UPDATE #{#entityName} b SET b.isDelete=:isDelete WHERE b.tid in :tids")
	public int updateDeleteAllByTids(@Param("isDelete") Boolean isDelete, @Param("tids") ID[] ids);

	@Modifying
	@Query(value = "UPDATE #{#entityName} b SET b.isDelete=:isDelete WHERE b.tid in :tid")
	public int updateDeleteByTids(@Param("isDelete") Boolean isDelete, @Param("tid") ID id);
	
	@Modifying
	@Query(value = "DELETE FROM #{#entityName}  WHERE tid in :tids")
	public int deleteAllByTids(@Param("tids") ID[] ids);
}
