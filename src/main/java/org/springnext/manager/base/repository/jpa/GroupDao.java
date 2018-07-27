package org.springnext.manager.base.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springnext.manager.base.entity.Group;

/**
 * 用户组
 * @author HyDe
 *
 */
public interface GroupDao extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
	
	@Modifying
	@Query(value="update Group g set g.isDelete=:isDelete where g.tid = :tid")
	public int updateGroupDeleteByTid(@Param("isDelete") Boolean isDelete,@Param("tid") Long ids);
	
	@Query(value="from Group g where g.parent is null")
	public List<Group> getParentGroup();
	
}
