package org.springnext.manager.base.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色表
 * 
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_sys_dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 字典显示名
	 */
	private String dictName;
	/**
	 * 字典存储值
	 */
	private String dictValue;
	/**
	 * 字典类别显示名
	 */
	private String typeName;
	/**
	 * 字典类别查询值
	 */
	private String typeValue;

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

}
