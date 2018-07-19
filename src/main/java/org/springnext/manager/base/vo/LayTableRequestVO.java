package org.springnext.manager.base.vo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * LayUI的Table请求参数
 * @author hyde
 *
 */
public class LayTableRequestVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	private Map<String, String> search;
	private Integer limit;
	private Integer page;
	private String orderField = "tid";
	private String orderDirection = "desc";

	public Map<String, String> getSearch() {
		return search;
	}

	public void setSearch(Map<String, String> search) {
		this.search = search;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public PageRequest toPageRequest() {
		return PageRequest.of(this.page-1, this.limit, new Sort(Direction.fromString(this.orderDirection), this.orderField));
	}
}
