package org.springnext.manager.base.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springnext.manager.base.entity.Group;

/**
 * 用户组
 * @author HyDe
 *
 */
public interface GroupDao extends PagingAndSortingRepository<Group, Long>, JpaSpecificationExecutor<Group> {
	
	@Modifying
	@Query(value="update Group g set g.isDelete=:isDelete where g.tid = :tid")
	public int updateGroupDeleteByTid(@Param("isDelete") Boolean isDelete,@Param("tid") Long ids);
	
}
