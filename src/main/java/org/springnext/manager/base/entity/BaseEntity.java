package org.springnext.manager.base.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

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
	private static final long serialVersionUID = -1L;
	/**
	 * 表ID
	 */
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	protected String tid;
	/**
	 * 删除标记，表数据，不作物理删除
	 */
	@JsonIgnore
	@JoinColumn(name = "is_delete")
	protected Boolean isDelete = false;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
