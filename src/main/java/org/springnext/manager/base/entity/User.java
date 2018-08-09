package org.springnext.manager.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户表
 * 
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_user")
/* 默认的缓存策略 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 登录密码
	 */
	@JsonIgnore
	private String loginPassword;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 用户状态
	 */
	private String userStatus;
	/**
	 * 所在用户
	 */
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	/**
	 * 所属角色
	 */
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
