package org.springnext.manager.base.service;

import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.persistence.DynamicSpecifications;
import org.springnext.manager.base.persistence.SearchFilter;
import org.springnext.manager.base.repository.jpa.UserDao;

import com.google.common.collect.Maps;

/**
 * 用户管理业务类.
 * 
 * @author HyDe
 */
// Spring Service Bean的标识.
@Service
public class UserService {

	private static Logger logger = LoggerFactory
			.getLogger(UserService.class);

	@Autowired
	private BusinessLogger businessLogger;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 取用户
	 */
	public User findUserByID(Long userID) {
		User user = userDao.getOne(userID);
		//好习惯，提前初始化前端需要的懒加载项
		Hibernate.initialize(user.getGroup());
		return user;
	}

	/**
	 * 按登录名查询用户.
	 */
	public User findUserByLoginName(String loginName) {
		// 业务日志演示
		if (businessLogger != null) {
			businessLogger.log("USER", "按登入帐号查找用户信息", loginName, null);
		}
		return userDao.findByLoginName(loginName);
	}

	/**
	 * 按页面传来的查询条件查询用户.
	 */
	public Page<User> searchUserListPage(Map<String, Object> searchParams,PageRequest pageRequest) {
		logger.info("用户{}再在检索用户数据.", getCurrentLoginName());
		//增加搜索项，只查询未删除的用户
		//searchParams.put("EQ_isDelete", Boolean.FALSE);
		//提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		//拼接查询条件
		Specification<User> spec = DynamicSpecifications.bySearchFilter(
				filters.values());
		
		
		Page<User> userListPage = userDao.findAll(spec, pageRequest);
		logger.info(userListPage.getContent().size()+"");
		return userListPage;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentLoginName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}


	/**
	 * 
	 * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
	 * 
	 */
	public void updateUser(User user) {

		if (isSupervisor(user)) {
			logger.warn("操作员{}尝试修改超级管理员用户", getCurrentLoginName());
			throw new ServiceException("不能修改超级管理员用户");
		}
		
		User dbUser = userDao.getOne(user.getTid());
		try {
			BeanUtils.copyProperties(user, dbUser, "loginPassword","passwordSalt","isDelete");
		} catch (Exception e) {
			logger.error("操作员{}修改用户数据{}出错", getCurrentLoginName(),user.getLoginName());
			throw new ServiceException("用户更新失败");
		} 
		userDao.save(dbUser);

		// 业务日志演示
		Map<Object, Object> logData = Maps.newHashMap();
		logData.put("userId", user.getTid());
		businessLogger.log("USER", "UPDATE", getCurrentLoginName(), logData);
	}

	/**
	 * 创建用户
	 * @param user
	 */
	public void createUser(User user) {
		user.setLoginPassword("123456");
		user.setIsDelete(false);
		user.setLoginPassword(passwordEncoder.encode("123456"));
		userDao.save(user);

		// 业务日志演示
		Map<Object, Object> logData = Maps.newHashMap();
		logData.put("userId", user.getTid());
		businessLogger.log("USER", "CREATE", getCurrentLoginName(), logData);
	}
	
	/**
	 * 修改密码
	 * @param oldPassword
	 * @param newPassword
	 */
	public void changePassword(String oldPassword,String newPassword) {
		String loginName = getCurrentLoginName();
		User user = findUserByLoginName(loginName);
		if(passwordEncoder.matches(oldPassword, user.getLoginPassword())){
			user.setLoginPassword(passwordEncoder.encode(newPassword));
			userDao.save(user);
		}
	}

	/**
	 * 删除用户
	 * @param ids
	 */
	public void deleteUser(Long[] ids) {
		userDao.updateUserDeleteByTid(true, ids);
	}

	/**
	 * 判断是否超级管理员.表数据初始化时会创建超级用户
	 */
	private boolean isSupervisor(User user) {
		return ((user.getTid() != null) && (user.getTid() == 1L));
	}
}
