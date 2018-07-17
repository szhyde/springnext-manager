package org.springnext.manager.base.vo;

import java.io.Serializable;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.google.common.collect.Lists;

public class LayTableResponseVO<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	
	/**
	 *  响应代码0表示正常
	 */
	private Integer code = 0;

	/**
	 * 响应消息
	 */
	private String msg = "";

	/**
	 * 总行数
	 */
	private Long count;

	/**
	 * 总数量
	 */
	private List<T> data = Lists.newArrayList();


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	public List<T> getData() {
		return data;
	}


	public void setData(List<T> data) {
		this.data = data;
	}


	public <S> void addRows(List<S> sourceList, Class<T> destinationClass) {
		Mapper mapper = new DozerBeanMapper();
		for (S source : sourceList) {
			if (source != null) {
				data.add(mapper.map(source, destinationClass));
			}
		}
	}
}
