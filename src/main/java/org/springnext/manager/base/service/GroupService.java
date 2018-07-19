package org.springnext.manager.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Group;
import org.springnext.manager.base.repository.jpa.GroupDao;

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
	
	
	
	/**
	 * 按层级取用户组
	 * @param parentId
	 * @return
	 */
	public List<Group> getParentGroup(){
		return groupDao.getParentGroup();
	}
	
	
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
