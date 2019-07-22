package com.nts.teststruts.service.impl;

import com.nts.teststruts.model.YktDept;

public class YktXfReport implements Comparable<YktXfReport> {
	
	private String deptname;
	private String deptcode;
	private int time1Total;
	private int time2Total;
	private int time3Total;
	private int time4Total;
	private int time5Total;
	private int time6Total;
	private String stname;
	
	
	public YktXfReport(){}
	
	public YktXfReport(YktDept yktdept){
		this.deptname = yktdept.getDeptname();
		this.deptcode = yktdept.getDeptcode();		
	}
	
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public int getTime1Total() {
		return time1Total;
	}
	public void setTime1Total(int time1Total) {
		this.time1Total = time1Total;
	}
	public int getTime2Total() {
		return time2Total;
	}
	public void setTime2Total(int time2Total) {
		this.time2Total = time2Total;
	}
	public int getTime3Total() {
		return time3Total;
	}
	public void setTime3Total(int time3Total) {
		this.time3Total = time3Total;
	}
	public int getTime4Total() {
		return time4Total;
	}
	public void setTime4Total(int time4Total) {
		this.time4Total = time4Total;
	}
	public int getTime5Total() {
		return time5Total;
	}
	public void setTime5Total(int time5Total) {
		this.time5Total = time5Total;
	}
	public int getTime6Total() {
		return time6Total;
	}
	public void setTime6Total(int time6Total) {
		this.time6Total = time6Total;
	}
	
	
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	@Override
	public String toString() {
		return "YktXfReport [deptname=" + deptname + ", deptcode=" + deptcode + ", time1Total=" + time1Total
				+ ", time2Total=" + time2Total + ", time3Total=" + time3Total + ", time4Total=" + time4Total
				+ ", time5Total=" + time5Total + ", time6Total=" + time6Total + ", stname=" + stname + "]";
	}

	@Override
	public int compareTo(YktXfReport another) {
        int i = deptcode.compareTo(another.deptcode); //比较名字字符串  
        if (i == 0) { //如果名字一样，则继续比较年龄  
            return deptname.compareTo(another.deptname);  
        } else { //首先比较名字，名字不一样，则返回比较结果  
            return i;  
        }
	}
	

	
	
	

}
