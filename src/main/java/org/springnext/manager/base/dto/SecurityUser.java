package org.springnext.manager.base.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.entity.User;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Collection<GrantedAuthority> authorities = null;
	
	private List<Permissions> permissions = null;

	public SecurityUser(User user,List<Permissions> permissions) {
		if (user != null) {
			BeanUtils.copyProperties(user, this);
		}
		if (permissions != null) {
			this.permissions = permissions;
		}
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		if(authorities==null) {
			authorities = new ArrayList<GrantedAuthority>();
			Role role = this.getRole();
			if(role!=null) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getRoleName());
	            authorities.add(grantedAuthority);
				if(this.permissions!=null) {
					for (Permissions permission : permissions) {
			            if (permission != null && permission.getPermissions()!=null) {
				            grantedAuthority = new SimpleGrantedAuthority("ROLE_"+permission.getPermissions());
				            //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
				            authorities.add(grantedAuthority);
			            }
			        }
				}
			}
		}

		return authorities;

	}

	@Override
	public String getPassword() {
		return super.getLoginPassword();
	}

	@Override
	public String getUsername() {

		return super.getLoginName();

	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.getIsDelete();

	}

	@Override
	public boolean isAccountNonLocked() {

		return true;

	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;

	}

	@Override
	public boolean isEnabled() {
		return "enable".equals(this.getUserStatus());

	}

}