package com.nts.teststruts.dao.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="UserMeun")
public class UserMeun {

	private int id;
	private String name;
    private String code;
    private int menulevel;
    private String activityclassname;
	private Set<UserMeun> childMenu = new HashSet<UserMeun>();
	private UserMeun parentMenu;

	public int getMenulevel() {
		return menulevel;
	}
	public void setMenulevel(int menulevel) {
		this.menulevel = menulevel;
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
		
	
	public String getActivityclassname() {
		return activityclassname;
	}
	public void setActivityclassname(String activityclassname) {
		this.activityclassname = activityclassname;
	}
	/**/
	@OneToMany(cascade=CascadeType.ALL,mappedBy="parentMenu",fetch=FetchType.EAGER)
	public Set<UserMeun> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(Set<UserMeun> childMenu) {
		this.childMenu = childMenu;
	}
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	public UserMeun getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(UserMeun parentMenu) {
		this.parentMenu = parentMenu;
	}
	
}
