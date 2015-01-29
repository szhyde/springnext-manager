package org.springnext.manager.base.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springnext.manager.core.entity.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户组
 * @author HyDe
 *
 */
@Entity
@Table(name = "t_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8653447792384821493L;
	/**
	 * 用户组名
	 */
	private String groupName;
	/**
	 * 父级
	 */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Group parent;
	/**
	 * 拥有用户
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "group")
	private List<User> users;
	/**
	 * 子集
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "parent")
	private List<Group> childGroup;
	/**
	 * 子集数量，但不保存到数据库中
	 */
	@Transient
	private Integer child;
	/**
	 * 父级ID，同样不保存到数据库中
	 */
	@Transient
	private Long pid;
	
	public Long getPid(){
		return pid;
	}
	
	public void setPid(Long pid){
		this.pid = pid;
	}
	public Boolean getLeaf(){
		if(child==null || child==0){
			return true;
		}
		return false;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Group> getChildGroup() {
		return childGroup;
	}

	public void setChildGroup(List<Group> childGroup) {
		this.childGroup = childGroup;
	}

	public Integer getChild() {
		return child;
	}

	public void setChild(Integer child) {
		this.child = child;
	}

}
