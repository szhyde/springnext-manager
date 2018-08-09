package org.springnext.manager.base.service;

import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.repository.jpa.BaseDao;
import org.springnext.manager.base.repository.jpa.UserDao;

import com.google.common.collect.Maps;

/**
 * 用户管理业务类.
 * 
 * @author HyDe
 */
@Service
@Transactional(readOnly=true)
public class UserService extends BaseService<User, Long>{

	private static Logger logger = LoggerFactory
			.getLogger(UserService.class);

	@Autowired
	private BusinessLogger businessLogger;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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

		User dbUser = userDao.getOne(user.getTid());
		try {
			BeanUtils.copyProperties(user, dbUser, "loginPassword","isDelete");
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
		user.setLoginPassword(passwordEncoder.encode(user.getLoginPassword()));
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
	public boolean changePassword(String oldPassword,String newPassword) {
		String loginName = getCurrentLoginName();
		User user = findOneByAttributesName("EQ_loginName", loginName);
		if(passwordEncoder.matches(oldPassword, user.getLoginPassword())){
			user.setLoginPassword(passwordEncoder.encode(newPassword));
			userDao.save(user);
			return true;
		}
		return false;
	}
	
	/**
	 * 修改密码
	 * @param tid
	 * @param newPassword
	 */
	public void changePassword(Long tid,String newPassword) {
		User user = findOne(tid);
		user.setLoginPassword(passwordEncoder.encode(newPassword));
		userDao.save(user);
	}


	@Override
	protected BaseDao<User, Long> initBaseDao() {
		return userDao;
	}
}
