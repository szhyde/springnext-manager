package org.springnext.manager.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Dictionary;
import org.springnext.manager.base.repository.jpa.DictionaryDao;

/**
 * 字典
 * @author HyDe
 *
 */
@Service
@Transactional
public class DictionaryService extends BaseService<Dictionary, Long>{
    
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
    @Override
    protected PagingAndSortingRepository<Dictionary, Long> initPagingAndSortingRepository()
    {
        return dictionaryDao;
    }

    @Override
    protected JpaSpecificationExecutor<Dictionary> initJpaSpecificationExecutor()
    {
        return dictionaryDao;
    }
	
	
}
