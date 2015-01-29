package org.springnext.manager.core.dto;

import javax.persistence.MappedSuperclass;

/**
 * RESTful的DTO的父类
 * @author HyDe
 *
 */
@MappedSuperclass
public abstract class BaseDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5634770634051065322L;
	protected Long tid;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

}
