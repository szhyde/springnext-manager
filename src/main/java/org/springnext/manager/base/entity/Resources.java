package org.springnext.manager.base.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 资源
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_resources")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resources extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2991935228174338908L;
	/**
	 * 资源地址
	 */
	private String url;
	/**
	 * 权限要求
	 */
	private String permissions;
	/**
	 * 说明
	 */
	private String remark;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
