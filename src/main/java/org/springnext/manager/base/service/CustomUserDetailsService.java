package org.springnext.manager.base.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springnext.manager.base.dto.SecurityUser;
import org.springnext.manager.base.entity.Permissions;
import org.springnext.manager.base.entity.Role;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.repository.jpa.PermissionsDao;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired // 数据库服务类
	private UserService userService;

	@Autowired // 数据库服务类
	private PermissionsDao permissionsDao;

	@Override
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		// SUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
		// 本例使用SUser中的email作为用户名:
		User user = userService.findOneByAttributesName("EQ_loginName", loginName);
		if (user == null) {

			throw new UsernameNotFoundException("UserName " + loginName + " not found");

		}
		List<Permissions> preList = null;
		if (user.getRole() != null) {
			Specification<Permissions> specification = new Specification<Permissions>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Permissions> root, javax.persistence.criteria.CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {

					ListJoin<Permissions, Role> join = root.join(root.getModel().getList("roles", Role.class));
					Predicate predicate = criteriaBuilder.equal(join.get("tid").as(String.class),
							user.getRole().getTid());
					// 这里面的join代表的是student，属于加入进来的部分，而不是链接表的全部结果；
					return predicate;
				}

			};
			preList = permissionsDao.findAll(specification);
		}

		// SecurityUser实现UserDetails并将User的Email映射为username
		SecurityUser securityUser = new SecurityUser(user, preList);
		return securityUser;

	}

}
