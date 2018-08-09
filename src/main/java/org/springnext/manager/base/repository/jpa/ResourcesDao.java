package org.springnext.manager.base.repository.jpa;

import org.springnext.manager.base.entity.Resources;

/**
 * 资源DAO
 * @author HyDe
 *
 */
public interface ResourcesDao extends BaseDao<Resources, Long> {
	
	Resources findByUrl(String url);
	
}
