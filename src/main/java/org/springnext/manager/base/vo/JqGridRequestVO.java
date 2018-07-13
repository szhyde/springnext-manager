package org.springnext.manager.base.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class JqGridRequestVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer rows;

    /**
     * 排序字段
     */
    private String sidx;

    /**
     * 排序方式
     */
    private String sord;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
    
	public PageRequest toPageRequest() {
		if(StringUtils.isBlank(sord)) {
			sord = "desc";
		}
		if(StringUtils.isBlank(sidx)) {
			sidx = "tid";
		}
		return new PageRequest(page-1,rows,new Sort(Direction.fromString(sord),sidx));
	}
}
