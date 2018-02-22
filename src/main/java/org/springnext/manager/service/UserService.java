package org.springnext.manager.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.Hibernate;
import org.hibernate.service.spi.ServiceException;
import org.javasimon.aop.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.domain.ShiroUser;
import org.springnext.manager.entity.User;
import org.springnext.manager.persistence.DynamicSpecifications;
import org.springnext.manager.persistence.SearchFilter;
import org.springnext.manager.repository.jpa.UserDao;
import org.springnext.manager.utils.Digests;
import org.springnext.manager.utils.Encodes;

import com.google.common.collect.Maps;

/**
 * 用户管理业务类.
 * 
 * @author HyDe
 */
// Spring Service Bean的标识.
@Service
@Transactional
@Monitored
public class UserService {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory
			.getLogger(UserService.class);

	@Autowired
	private BusinessLogger businessLogger;

	@Autowired
	private UserDao userDao;
	
	/**
	 * 取用户
	 */
	public User findUserByID(Long userID) {
		User user = userDao.findOne(userID);
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
			businessLogger.log("USER", "登陆", loginName, null);
		}
		return userDao.findByLoginName(loginName);
	}

	/**
	 * 按页面传来的查询条件查询用户.
	 */
	public Page<User> searchUserListPage(Map<String, Object> searchParams,
			int pageIndex, int pageSize, String sortField, String sortType) {
		logger.info("用户{}再在检索用户数据.", getCurrentLoginName());
		//增加搜索项，只查询未删除的用户
		searchParams.put("EQ_isDelete", Boolean.FALSE);
		//提取搜索条件，把key值按_切分查询字段与条件
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		//拼接查询条件
		Specification<User> spec = DynamicSpecifications.bySearchFilter(
				filters.values(), User.class);
		Page<User> userListPage = userDao.findAll(spec, new PageRequest(
				pageIndex, pageSize, new Sort(Direction.fromString(sortType),
						sortField)));
		return userListPage;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentLoginName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setPasswordSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getLoginPassword().getBytes(),
				salt, HASH_INTERATIONS);
		user.setLoginPassword(Encodes.encodeHex(hashPassword));
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
		
		User dbUser = userDao.findOne(user.getTid());
		try {
			org.springframework.beans.BeanUtils.copyProperties(user, dbUser, "loginPassword","passwordSalt","isDelete");
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
		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		entryptPassword(user);
		
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
		
		oldPassword = Encodes.encodeHex(Digests.sha1(oldPassword.getBytes(), Encodes.decodeHex(user.getPasswordSalt()),HASH_INTERATIONS));
		
		if(StringUtils.equals(oldPassword, user.getLoginPassword())){
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			user.setPasswordSalt(Encodes.encodeHex(salt));
			byte[] hashPassword = Digests.sha1(newPassword.getBytes(),salt, HASH_INTERATIONS);
			user.setLoginPassword(Encodes.encodeHex(hashPassword));
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
