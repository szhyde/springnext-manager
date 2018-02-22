package org.springnext.manager.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springnext.manager.entity.Role;

/**
 * 角色
 * @author HyDe
 *
 */
public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	@Modifying
	@Query(value="update Role r set r.isDelete=:isDelete where r.tid in :tids")
	public int updateRoleDeleteByTid(@Param("isDelete") Boolean isDelete,@Param("tids") Long[] ids);
	
	@Query(value="select r from Role r join r.users u where u.tid = :userID and r.isDelete = false")
	public List<Role> findRoleByUserId(@Param("userID") Long userID);
}
