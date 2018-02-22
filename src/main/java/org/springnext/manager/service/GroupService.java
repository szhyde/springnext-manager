package org.springnext.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.entity.Group;
import org.springnext.manager.repository.jpa.GroupDao;

/**
 * 用户组
 * @author HyDe
 *
 */
@Service
@Transactional
public class GroupService extends BaseService<Group, Long>{
    
	
	@Autowired
	private GroupDao groupDao;
	
//	@Autowired
//    private GroupMybatisDao groupMyBatisDao;
	
//	/**
//	 * 取所有顶级用户组
//	 * @return
//	 */
//	public List<Group> getParentGroup(){
//		List<Group> list = groupDao.getParentGroup();
//		for (Group group : list) {
//			Hibernates.initLazyProperty(group.getParent());
//			Hibernates.initLazyProperty(group.getChildGroup());
//		}
//		return list;
//	}
	
//	/**
//	 * 按层级取用户组
//	 * @param parentId
//	 * @return
//	 */
//	public List<Group> findGroupByParent(Long parentId){
//		Map<String,Object> parameters = Maps.newHashMap();
//		parameters.put("parentId", parentId);
//		return groupMyBatisDao.search(parameters);
//	}
	
	/**
	 * 保存
	 * @param group
	 */
	public void saveGroup(Group group){
		group.setIsDelete(false);
		groupDao.save(group);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteGroup(Long id) {
		groupDao.updateGroupDeleteByTid(true, id);
	}

    @Override
    protected PagingAndSortingRepository<Group, Long> initPagingAndSortingRepository()
    {
        return groupDao;
    }

    @Override
    protected JpaSpecificationExecutor<Group> initJpaSpecificationExecutor()
    {
        return groupDao;
    }
	
	
}
