package org.springnext.manager.base.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springnext.manager.base.entity.Group;

/**
 * 用户组
 * @author HyDe
 *
 */
public interface GroupDao extends BaseDao<Group, Long> {
	
	@Query(value="from Group g where g.parent is null")
	public List<Group> getParentGroup();
	
}
