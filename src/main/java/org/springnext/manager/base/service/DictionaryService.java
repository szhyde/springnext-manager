package org.springnext.manager.base.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springnext.manager.base.entity.Dictionary;
import org.springnext.manager.base.repository.jpa.BaseDao;
import org.springnext.manager.base.repository.jpa.DictionaryDao;

import com.google.common.collect.Maps;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * 字典
 * 
 * @author HyDe
 *
 */
@Service
@Transactional(readOnly=true)
public class DictionaryService extends BaseService<Dictionary, Long> {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Resource(name="dictionaryEhcache")
	private Ehcache dictionaryEhcache;

	@Override
	protected BaseDao<Dictionary, Long> initBaseDao() {
		return dictionaryDao;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getDictionaryMap(String typeValue) {
		Map<String, String> returnMap = Maps.newHashMap();
		Element element = dictionaryEhcache.get(typeValue);
		if (element != null && element.getObjectValue() instanceof Map) {
			returnMap = (Map<String, String>) element.getObjectValue();
		} else {
			List<Dictionary> dictList = findAllByAttributesName("EQ_typeValue", typeValue, null);
			for (Dictionary dictionary : dictList) {
				returnMap.put(dictionary.getDictValue(), dictionary.getDictName());
			}
			if(dictList.size()>0) {
				dictionaryEhcache.put(new Element(typeValue, returnMap));
			}
		}
		return returnMap;
	}
	
}
