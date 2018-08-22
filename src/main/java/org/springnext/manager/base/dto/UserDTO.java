package org.springnext.manager.base.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springnext.manager.base.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

public class UserDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 登入帐号
	 */
	private String loginName;
	/**
	 * 登入密码
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
	 * 所属组织
	 */
	private String groupID;

	private String groupName;

	/**
	 * 所属角色
	 */
	private String roleID;

	private String roleName;

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

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static User transformToUser(UserDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return user;
	}

	public static UserDTO transformToUserDTO(User user,Map<String, String> userStatusMap) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		userDTO.setUserStatus(userStatusMap.get(user.getUserStatus()));
		userDTO.setRoleID(user.getRole().getTid());
		userDTO.setRoleName(user.getRole().getRoleName());
		userDTO.setGroupID(user.getGroup().getTid());
		userDTO.setGroupName(user.getGroup().getGroupName());
		return userDTO;
	}
	
	public static List<UserDTO> transformAllToUserDTO(List<User> users,Map<String, String> userStatusMap) {
		List<UserDTO> returnList = Lists.newArrayList();
		for (User user : users) {
			returnList.add(transformToUserDTO(user,userStatusMap));
		}
		return returnList;
	}
}
