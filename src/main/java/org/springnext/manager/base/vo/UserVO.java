package org.springnext.manager.base.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springnext.manager.core.vo.BaseVO;

/**
 * 用户VO
 * @author HyDe
 *
 */
public class UserVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 374439069065093594L;

	@NotBlank
	private String loginName;
	@NotBlank
	private String userName;
	@Email
	private String email;
	@NotBlank
	private String userStatus;
	@NotBlank
	private Long groupID;
	@NotBlank
	private String groupName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
