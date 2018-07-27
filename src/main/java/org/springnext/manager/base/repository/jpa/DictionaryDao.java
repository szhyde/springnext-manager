package org.springnext.manager.base.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springnext.manager.base.entity.Dictionary;

/**
 * 字典
 * 
 * @author HyDe
 *
 */
public interface DictionaryDao extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {
//	@Modifying
//	@Query(value = "update Dictionary d set d.isDelete=:isDelete where d.tid in :tids")
//	public int updateDictionaryDeleteByTid(@Param("isDelete") Boolean isDelete, @Param("tids") Long[] ids);
}
