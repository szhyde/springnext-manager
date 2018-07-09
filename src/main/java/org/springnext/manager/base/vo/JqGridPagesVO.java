package org.springnext.manager.base.vo;

import java.io.Serializable;

public class JqGridPagesVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2172260021261986128L;

	/**
     * 总页数
     */
    private Integer total;

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
     * 排序
     */
    private String sord;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

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
    
}
