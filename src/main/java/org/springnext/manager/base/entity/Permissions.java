package org.springnext.manager.base.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 权限表
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_sys_permissions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permissions extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 权限，英文，用于系统鉴权判断
	 */
	private String permissions;
	/**
	 * 权限说明
	 */
	private String remark;
	/**
	 * 父级
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Permissions parent;
	
	/**
	 * 子集
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "parent")
	private List<Permissions> childPermissions;
	/**
	 * 拥有资源
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "permissions")
	private List<Resources> resources;
	/**
	 * 所属角色
	 */
	/*多对多定义*/
	@ManyToMany
	@JoinTable(name = "t_sys_role_permissions", joinColumns = { @JoinColumn(name = "permissions_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
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
	public Permissions getParent() {
		return parent;
	}
	public void setParent(Permissions parent) {
		this.parent = parent;
	}
	public List<Permissions> getChildPermissions() {
		return childPermissions;
	}
	public void setChildPermissions(List<Permissions> childPermissions) {
		this.childPermissions = childPermissions;
	}
	public List<Resources> getResources() {
		return resources;
	}
	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
