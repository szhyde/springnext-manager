package org.springnext.manager.base.shiro;


import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springnext.manager.base.domain.ShiroUser;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.service.UserService;
import org.springnext.manager.base.utils.Encodes;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;


public class ShiroDbRealm extends AuthorizingRealm
{
	@Autowired
	protected UserService userService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		User user = userService.findUserByLoginName(token.getUsername());
		if (user != null) {
			if ("disabled".equals(user.getUserStatus())) {
				throw new DisabledAccountException();
			}

			byte[] salt = Encodes.decodeHex(user.getPasswordSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getTid(),
					user.getLoginName(), user.getUserName()),
					user.getLoginPassword(), ByteSource.Util.bytes(salt),
					getName());
		} else {
			return null;
		}

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.findUserByID(shiroUser.userID);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		for (Role role : user.getRoles()) {
			// 基于Role的权限信息
			info.addRole(role.getRole());
			// 基于Permission的权限信息
			info.addStringPermissions(Collections2.transform(
					role.getPermissions(), new Function<Permissions, String>() {
						@Override
						public String apply(Permissions arg0) {
							return arg0.getPermissions();
						}
					}));
		}

		return info;
	}
	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				UserService.HASH_ALGORITHM);
		matcher.setHashIterations(UserService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);

	}

}