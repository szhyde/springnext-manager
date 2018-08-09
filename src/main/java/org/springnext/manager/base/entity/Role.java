package org.springnext.manager.base.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 角色表
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色名,英文用于系统权限判断
	 */
	private String roleName;
	/**
	 * 说明
	 */
	private String remark;
	/**
	 * 拥有的权限
	 */
	/* 多对多定义 */
	@ManyToMany
	@JoinTable(name = "t_role_permissions", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "permissions_id") })
	/* 加载策略定义 */
	@Fetch(FetchMode.SUBSELECT)
	/* 集合按id排序 */
	@OrderBy("tid ASC")
	/* 缓存策略 */
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Permissions> permissions;
	/**
	 * 所属用户
	 */
	/* 多对多定义 */
	@OneToMany(mappedBy = "role")
	private List<User> users;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permissions> permissions) {
		this.permissions = permissions;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
