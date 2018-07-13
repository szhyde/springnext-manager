package org.springnext.manager.base.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.entity.User;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	public SecurityUser(User user) {
		if (user != null) {
			BeanUtils.copyProperties(user, this);
		}
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		Role userRole = this.getRole();

		if (userRole != null) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
			authorities.add(authority);
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

		return true;

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

		return true;

	}

}