package org.springnext.manager.base.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 表实体的父类
 * @author HyDe
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6354814299432290905L;
	/**
	 * 表ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long tid;
	/**
	 * 删除标记，表数据，不作物理删除
	 */
	@JsonIgnore
	@JoinColumn(name = "is_delete")
	protected Boolean isDelete = false;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
