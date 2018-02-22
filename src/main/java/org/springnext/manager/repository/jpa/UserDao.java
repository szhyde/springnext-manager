package org.springnext.manager.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springnext.manager.entity.User;

/**
 * 用户
 * @author HyDe
 *
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	User findByLoginName(String loginName);
	
	@Modifying
	@Query(value="update User u set u.isDelete=:isDelete where u.tid in :tids")
	public int updateUserDeleteByTid(@Param("isDelete") Boolean isDelete,@Param("tids") Long[] ids);
}
