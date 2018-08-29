package org.springnext.manager.base.persistence;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * 通用动态查询条件类与DynamicSpecifications一起，实现动态的条件查询
 * @author HyDe
 *
 */
public class SearchFilter {

	//等于，模糊，大于，小于，大于等于，小于等于，空，非空
	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, NULL,NOT_NULL
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank(String.valueOf(value))) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}
