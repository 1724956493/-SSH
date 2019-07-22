package com.nts.teststruts.mypage;

import javax.persistence.Entity;

public class QuyerBonues {
	private String billcode;
	private String billhead;
	private String project;
	private String yiju;
	private String createdate;
	private String mulct;
	private String reward;
	private String type;
	private String appstatus;
private String jobname;
	public String getAppstatus() {
		return appstatus;
	}

	public void setAppstatus(String appstatus) {
		this.appstatus = appstatus;
	}

	public String getBillcode() {
		return billcode;
	}

	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}

	public String getBillhead() {
		return billhead;
	}

	public void setBillhead(String billhead) {
		this.billhead = billhead;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getYiju() {
		return yiju;
	}

	public void setYiju(String yiju) {
		this.yiju = yiju;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getMulct() {
		return mulct;
	}

	public void setMulct(String mulct) {
		this.mulct = mulct;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public QuyerBonues(Object[] object) {
		super();
		this.billcode = object[0].toString();
		this.billhead = object[1].toString();
		this.project = object[2].toString();
		this.yiju = object[3].toString();
		this.createdate = object[4].toString();
		this.mulct = object[5].toString();
		this.reward = object[6].toString();
		this.type = object[7].toString();
		int key=Integer.parseInt(object[8].toString());
		switch (key) {
		case 1:
			this.appstatus = "审核通过";
			break;
		case 2:
			this.appstatus = "通报不处罚";
			break;
		case 3:
			this.appstatus = "审核不通过";
			break;

		default:
			break;
		}
		this.jobname=object[9].toString();
		
	}

	public QuyerBonues() {

	}
}
