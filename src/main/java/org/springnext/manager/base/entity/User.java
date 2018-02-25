package org.springnext.manager.base.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户表
 * 
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_user")
/*默认的缓存策略*/
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6664041807850636809L;
	/**
	 * 登录名
	 */
	@NotBlank
	private String loginName;
	/**
	 * 登录密码
	 */
	@JsonIgnore
	private String loginPassword;
	/**
	 * 加密盐
	 */
	@JsonIgnore
	private String passwordSalt;
	/**
	 * 用户名
	 */
	@NotBlank
	private String userName;
	/**
	 * 邮箱
	 */
	@Email
	private String email;
	/**
	 * 用户状态
	 */
	@NotBlank
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
	@JsonIgnore
	/*多对多定义*/
	@ManyToMany
	@JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	/*加载策略定义*/
	@Fetch(FetchMode.SUBSELECT)
	/*集合按id排序*/
	@OrderBy("tid ASC")
	/*缓存策略*/
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Role> roles;

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

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
