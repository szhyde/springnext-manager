package org.springnext.manager.base.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	protected String tid;
	/**
	 * 删除标记，表数据，不作物理删除
	 */
	@JsonIgnore
	protected Boolean isDelete;
	
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
