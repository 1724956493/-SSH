package com.nts.teststruts.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.nts.teststruts.dao.impl.BdDefdocDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.model.HiPsndocGrpdef4;

public class RewardAndPunishmentInfo {
	
	private String  employeeid;
	private String  bonus;
	private String  image;	
	private String  cknote;
	private String  operator;
	private String  dept;
	private String  status;
	private String  rewardandpunishmentId;
	private int  type;
	private int  status2;
	private String project;
	private String empleader;
	
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCknote() {
		return cknote;
	}
	public void setCknote(String cknote) {
		this.cknote = cknote;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRewardandpunishmentId() {
		return rewardandpunishmentId;
	}
	public void setRewardandpunishmentId(String rewardandpunishmentId) {
		this.rewardandpunishmentId = rewardandpunishmentId;
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus2() {
		return status2;
	}
	public void setStatus2(int status2) {
		this.status2 = status2;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getEmpleader() {
		return empleader;
	}
	public void setEmpleader(String empleader) {
		this.empleader = empleader;
	}
	
	@Override
	public String toString() {
		return "RewardAndPunishmentInfo [employeeid=" + employeeid + ", bonus=" + bonus + ", image=" + image
				+ ", cknote=" + cknote + ", operator=" + operator + ", dept=" + dept + ", status=" + status
				+ ", rewardandpunishmentId=" + rewardandpunishmentId + ", type=" + type + ", status2=" + status2
				+ ", project=" + project + ", empleader=" + empleader + "]";
	}	
}
