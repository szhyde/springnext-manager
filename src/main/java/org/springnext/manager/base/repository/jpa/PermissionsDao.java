package org.springnext.manager.base.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springnext.manager.base.entity.Permissions;

/**
 * 权限
 * @author HyDe
 *
 */
public interface PermissionsDao extends JpaRepository<Permissions, Long>, JpaSpecificationExecutor<Permissions> {
	@Modifying
	@Query(value="update Permissions u set u.isDelete=:isDelete where u.tid in :tids")
	public int updatePermissionsDeleteByTid(@Param("isDelete") Boolean isDelete,@Param("tids") Long[] ids);
}
