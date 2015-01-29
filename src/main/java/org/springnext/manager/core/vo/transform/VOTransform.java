package org.springnext.manager.core.vo.transform;

import java.util.List;

/**
 * VO转换器接口,方便工具类快速的转换VO，如TablePage类
 * 
 * 可以转换单个实体，也可以转换实体的集合
 * 
 * @author HyDe
 *
 * @param <T1>	目标VO
 * @param <T2>	源实体
 */
public interface VOTransform<T1,T2> {
	
	/**
	 * 单个的VO转换
	 * @param entity
	 * @return
	 */
	public T1 EntityToVO(T2 entity);
	
	/**
	 * 多个的VO转换
	 * @param entityList
	 * @return
	 */
	public List<T1> EntityToVO(List<T2> entityList);
}
