package org.springnext.manager.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springnext.manager.entity.Resources;

/**
 * 资源
 * @author HyDe
 *
 */
public interface ResourcesDao extends JpaRepository<Resources, Long>, JpaSpecificationExecutor<Resources> {
	@Modifying
	@Query(value="update Resources r set r.isDelete=:isDelete where r.tid in :tids")
	public int updateResourcesDeleteByTid(@Param("isDelete") Boolean isDelete,@Param("tids") Long[] ids);
}
