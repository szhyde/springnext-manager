package org.springnext.manager.base.vo.transform;

import java.util.List;

import org.springnext.manager.base.entity.User;
import org.springnext.manager.base.vo.UserVO;
import org.springnext.manager.core.mapper.BeanMapper;
import org.springnext.manager.core.vo.transform.VOTransform;

import com.google.common.collect.Lists;

/**
 * 用户VO转换器
 * @author HyDe
 *
 */
public class UserVOTransform implements VOTransform<UserVO,User>{
	
	/**
	 * 单例
	 */
	private static UserVOTransform userVOTransform = new UserVOTransform();
	
	private UserVOTransform(){
		
	}
	
	public static UserVOTransform getInstance(){
		return userVOTransform;
	}
	
	@Override
	public UserVO EntityToVO(User entity) {
		
		UserVO userVO = BeanMapper.map(entity, UserVO.class);
		if(entity.getGroup()!=null){
			userVO.setGroupID(entity.getGroup().getTid());
			userVO.setGroupName(entity.getGroup().getGroupName());
		}
		return userVO;
	}

	@Override
	public List<UserVO> EntityToVO(List<User> entityList) {
		List<UserVO> destinationList = Lists.newArrayList();
		for (User user : entityList) {
			UserVO userVO = BeanMapper.map(user, UserVO.class);
			if(user.getGroup()!=null){
				userVO.setGroupID(user.getGroup().getTid());
				userVO.setGroupName(user.getGroup().getGroupName());
			}
			destinationList.add(userVO);
		}
		return destinationList;
	}
}
