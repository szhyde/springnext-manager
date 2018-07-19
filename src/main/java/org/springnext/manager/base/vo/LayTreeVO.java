package org.springnext.manager.base.vo;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * LayUI的树形菜单
 * 
 * @author hyde
 *
 */
public class LayTreeVO {

	/**
	 * 树节点的ID
	 */
	private String id;

	/**
	 * 父级ID
	 */
	private String parentID;

	/**
	 * 树节点名
	 */
	private String name;

	/**
	 * 树节点的链接
	 */
	private String href;

	/**
	 * 是否展开，默认展开
	 */
	private boolean spread = true;;

	/**
	 * 其他属性
	 */
	private List<Map<String, Object>> attributes = Lists.newArrayList();

	/**
	 * 子节点
	 */
	private List<LayTreeVO> children = Lists.newArrayList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public List<Map<String, Object>> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Map<String, Object>> attributes) {
		this.attributes = attributes;
	}

	public List<LayTreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<LayTreeVO> children) {
		this.children = children;
	}

}
