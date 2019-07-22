package com.nts.teststruts.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.dao.impl.SmUserandclerkDaoImpl;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.BdPsndoc;

public class SmUserServ {

	private String  username;
	private String  usercode;
	private String  deptname;
	private String  useryktcode;
	private String  pkpsndoc;
	private String  password;
	private List<AdMenu> menus;
	
	
	public SmUserServ(){
	}
	
	public SmUserServ(SmUser smuser) throws SQLException
	{
		this.username=smuser.getUserName();
		this.usercode=smuser.getUserCode();
		BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByPk(new SmUserandclerkDaoImpl().GetByPk(smuser.getCuserid()).getPkPsndoc());
		this.pkpsndoc = bdpsndoc.getPkPsndoc();
		this.password = smuser.getUserPassword();
		String pk_deptdoc = bdpsndoc.getPkDeptdoc() ;
		String useryktcode = bdpsndoc.getGroupdef10() ;
		this.deptname = new BdDeptdocDaoImpl().GetByPk(pk_deptdoc).getDeptname();
		this.menus = new AdMenuDaoImpl().getbyUserPk(smuser.getCuserid());
	}

	
	
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getUseryktcode() {
		return useryktcode;
	}

	public void setUseryktcode(String useryktcode) {
		this.useryktcode = useryktcode;
	}

	public List<AdMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<AdMenu> menus) {
		this.menus = menus;
	}

	public String getPkpsndoc() {
		return pkpsndoc;
	}

	public void setPkpsndoc(String pkpsndoc) {
		this.pkpsndoc = pkpsndoc;
	}	
}
