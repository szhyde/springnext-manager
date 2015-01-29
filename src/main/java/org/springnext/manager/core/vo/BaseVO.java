package org.springnext.manager.core.vo;

import javax.persistence.MappedSuperclass;

/**
 * VO对象的父类
 * 
 * @author HyDe
 *
 */
@MappedSuperclass
public abstract class BaseVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -334703990065416911L;
	protected Long tid;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

}
