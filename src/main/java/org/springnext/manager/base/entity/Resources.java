package org.springnext.manager.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 资源
 * 
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
	private static final long serialVersionUID = 1L;
	/**
	 * 资源地址
	 */
	private String url;
	/**
	 * 关联权限
	 */
	@ManyToOne
	@JoinColumn(name = "permissions_id")
	private Permissions permissions;
	/**
	 * 权限名
	 */
	private String permissionsName;
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

	public Permissions getPermissions() {
		return permissions;
	}

	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}

	public String getPermissionsName() {
		return permissionsName;
	}

	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
