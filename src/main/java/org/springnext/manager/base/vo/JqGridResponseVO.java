package org.springnext.manager.base.vo;

import java.io.Serializable;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.google.common.collect.Lists;

public class JqGridResponseVO<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 总行数
     */
    private Long records;

    /**
     * 总页数
     */
    private Long total;

    /**
     * 排序
     */
    private List<T> rows = Lists.newArrayList();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

    public <S> void addRows(List<S> sourceList,Class<T> destinationClass){
    	Mapper mapper = new DozerBeanMapper();
    	for (S source : sourceList) {
			if (source != null) {
				rows.add(mapper.map(source, destinationClass));
			}
		}
    }
}
