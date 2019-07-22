package com.nts.teststruts.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsnbasdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.model.BdPsnbasdoc;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.model.HiPsndocGrpdef4;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;

public class EmployeeInfo {
	
	private String  employeename;
	private String  deptname;
	private String sex;
	private String  usedeptname;
	private String  employeeyktcode;
	private String  employeeid;
	private String  birthday;
	private String image;
	
	public EmployeeInfo(){}
	
	public EmployeeInfo(BdPsndoc bdpsndoc) throws SQLException, ParseException{
		this.employeename = bdpsndoc.getPsnname();
		this.employeeid = bdpsndoc.getPkPsndoc();
		this.employeeyktcode = bdpsndoc.getGroupdef10();
		this.deptname = new BdDeptdocDaoImpl().GetByPk(bdpsndoc.getPkDeptdoc()).getDeptname();
		if(bdpsndoc.getGroupdef6() !=null){
		this.usedeptname = new BdDeptdocDaoImpl().GetByPk(bdpsndoc.getGroupdef6()).getDeptname();}
		else{
			this.usedeptname = "";
		}
		BdPsnbasdoc bdpsnbasdoc =new BdPsnbasdocDaoImpl().GetByPk(bdpsndoc.getPkPsnbasdoc());
		System.out.println(bdpsnbasdoc.getBirthdate());
		if(bdpsnbasdoc.getBirthdate()!=null)
			{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdaydate =  df.parse(bdpsnbasdoc.getBirthdate());
			int age = ComUtil.daysBetween(birthdaydate, new Date());
			this.birthday = age+"";
			}
		else{
			this.birthday = "生日信息为空，请补全";
		}
		this.sex = bdpsnbasdoc.getSex();
		String id = new BdPsnbasdocDaoImpl().GetByPk(bdpsndoc.getPkPsnbasdoc()).getId();
		String filePath = "D:/upload/hrimage/"+id;
		this.image = DBUtil.Image2String(filePath);
	}
	
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getUsedeptname() {
		return usedeptname;
	}
	public void setUsedeptname(String usedeptname) {
		this.usedeptname = usedeptname;
	}
	public String getEmployeeyktcode() {
		return employeeyktcode;
	}
	public void setEmployeeyktcode(String employeeyktcode) {
		this.employeeyktcode = employeeyktcode;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "EmployeeInfo [employeename=" + employeename + ", deptname=" + deptname + ", sex=" + sex
				+ ", usedeptname=" + usedeptname + ", employeeyktcode=" + employeeyktcode + ", employeeid=" + employeeid
				+ ", birthday=" + birthday + "]";
	}


}
