package org.springnext.manager.base.repository.mybatis;

import java.util.List;
import java.util.Map;

import org.springnext.manager.base.entity.Group;
import org.springnext.manager.core.persistence.MyBatisRepository;

/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author HyDe
 */
@MyBatisRepository
public interface GroupMybatisDao {

	/**
	 * 层级查询，同时查询是否有子集
	 * @param parameters
	 * @return
	 */
	List<Group> search(Map<String, Object> parameters);
}
