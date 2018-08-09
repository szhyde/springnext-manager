package org.springnext.manager.base.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springnext.manager.base.entity.Role;

import com.google.common.collect.Lists;

public class RoleDTO extends BaseDTO implements Serializable {

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

	public static Role transformToRole(RoleDTO roleDTO) {
		Role role = new Role();
		BeanUtils.copyProperties(roleDTO, role);
		return role;
	}

	public static RoleDTO transformToRoleDTO(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		BeanUtils.copyProperties(role, roleDTO);
		return roleDTO;
	}
	
	public static List<RoleDTO> transformAllToRoleDTO(List<Role> roles) {
		List<RoleDTO> returnList = Lists.newArrayList();
		for (Role role : roles) {
			returnList.add(transformToRoleDTO(role));
		}
		return returnList;
	}
}
