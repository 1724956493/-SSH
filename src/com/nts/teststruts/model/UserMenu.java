package com.nts.teststruts.model;

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
public class UserMenu {

	private int id;
	private String name;
    private String code;
    private int menulevel;
    private String activityclassname;

	private Set<UserMenu> childMenu = new HashSet<UserMenu>();
	private UserMenu parentMenu;
	
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
	public int getMenulevel() {
		return menulevel;
	}
	public void setMenulevel(int menulevel) {
		this.menulevel = menulevel;
	}
	
	/**/
	@OneToMany(cascade=CascadeType.ALL,mappedBy="parentMenu",fetch=FetchType.EAGER)
	public Set<UserMenu> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(Set<UserMenu> childMenu) {
		this.childMenu = childMenu;
	}
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	public UserMenu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(UserMenu parentMenu) {
		this.parentMenu = parentMenu;
	}
}
