package org.springnext.manager.base.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springnext.manager.base.entity.Permissions;

import com.google.common.collect.Lists;

public class PermissionsDTO extends BaseDTO implements Serializable {

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
	 * 子集
	 */
	private List<PermissionsDTO> child = Lists.newArrayList();

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

	public List<PermissionsDTO> getChild() {
		return child;
	}

	public void setChild(List<PermissionsDTO> child) {
		this.child = child;
	}

	public static Permissions transformToPermissions(PermissionsDTO permissionsDTO) {
		Permissions permissions = new Permissions();
		BeanUtils.copyProperties(permissionsDTO, permissions);
		return permissions;
	}

	public static PermissionsDTO transformToPermissionsDTO(Permissions permissions) {
		PermissionsDTO permissionsDTO = new PermissionsDTO();
		BeanUtils.copyProperties(permissions, permissionsDTO);
		permissionsDTO.getChild().addAll(transformAllToPermissionsDTO(permissions.getChildPermissions()));
		return permissionsDTO;
	}
	
	public static List<PermissionsDTO> transformAllToPermissionsDTO(List<Permissions> permissionss) {
		List<PermissionsDTO> returnList = Lists.newArrayList();
		for (Permissions permissions : permissionss) {
			returnList.add(transformToPermissionsDTO(permissions));
		}
		return returnList;
	}
	
}
