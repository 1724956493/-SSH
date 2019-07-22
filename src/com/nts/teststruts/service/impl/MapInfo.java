package com.nts.teststruts.service.impl;

import javax.persistence.Entity;


public class MapInfo {
	private String s1;
	private int s2;
	private int s3;
	
	public MapInfo(){}
	
	public MapInfo(String s1,int s2)
	{
		s1 = s1;
		s2 = s2;
	}
	
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public int getS2() {
		return s2;
	}
	public void setS2(int s2) {
		this.s2 = s2;
	}

	public int getS3() {
		return s3;
	}

	public void setS3(int s3) {
		this.s3 = s3;
	}

	@Override
	public String toString() {
		return "MapInfo [s1=" + s1 + ", s2=" + s2 + ", s3=" + s3 + "]";
	}

	
	
}
