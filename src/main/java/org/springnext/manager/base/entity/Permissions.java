package org.springnext.manager.base.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
/**
 * 权限表
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_permissions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permissions extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8751830048723317724L;
	/**
	 * 权限，英文，用于系统鉴权判断
	 */
	@NotBlank
	private String permissions;
	/**
	 * 权限说明
	 */
	private String remark;
	/**
	 * 所属角色
	 */
	/*多对多定义*/
	@ManyToMany
	@JoinTable(name = "t_role_permissions", joinColumns = { @JoinColumn(name = "permissions_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	/*加载策略定义*/
	@Fetch(FetchMode.SUBSELECT)
	/*集合按id排序*/
	@OrderBy("tid ASC")
	/*缓存策略*/
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Role> roles;

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
}
