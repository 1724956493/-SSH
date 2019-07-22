package com.nts.teststruts.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdEquipckCheckcontent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AD_EQUIPCK_CHECKCONTENT", schema = "NC58")

public class AdEquipckCheckcontent implements java.io.Serializable {

	// Fields

	private String uuid;
	private String classname;
	private String checkcontent;
	private int dailycheck;
	private int deptcheck01;
	private int deptcheck02;
	private int functioncheck;
	private String checkresult;
	private String free01;
	private String free02;
	private String free03;
	private String free04;
	private String free05;
	private String free06;

	// Constructors

	/** default constructor */
	public AdEquipckCheckcontent() {
	}

	/** minimal constructor */
	public AdEquipckCheckcontent(String uuid) {
		this.uuid = uuid;
	}

	/** full constructor */
	public AdEquipckCheckcontent(String uuid, String classname, String checkcontent, int dailycheck,
			int deptcheck01, int deptcheck02, int functioncheck, String checkresult, String free01,
			String free02, String free03, String free04, String free05, String free06) {
		this.uuid = uuid;
		this.classname = classname;
		this.checkcontent = checkcontent;
		this.dailycheck = dailycheck;
		this.deptcheck01 = deptcheck01;
		this.deptcheck02 = deptcheck02;
		this.functioncheck = functioncheck;
		this.checkresult = checkresult;
		this.free01 = free01;
		this.free02 = free02;
		this.free03 = free03;
		this.free04 = free04;
		this.free05 = free05;
		this.free06 = free06;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name = "UUID", unique = true, nullable = false, length = 32)

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "CLASSNAME", length = 100)

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Column(name = "CHECKCONTENT", length = 500)

	public String getCheckcontent() {
		return this.checkcontent;
	}

	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
	}

	@Column(name = "DAILYCHECK", precision = 22, scale = 0)

	public int getDailycheck() {
		return this.dailycheck;
	}

	public void setDailycheck(int dailycheck) {
		this.dailycheck = dailycheck;
	}

	@Column(name = "DEPTCHECK01", precision = 22, scale = 0)

	public int getDeptcheck01() {
		return this.deptcheck01;
	}

	public void setDeptcheck01(int deptcheck01) {
		this.deptcheck01 = deptcheck01;
	}

	@Column(name = "DEPTCHECK02", precision = 22, scale = 0)

	public int getDeptcheck02() {
		return this.deptcheck02;
	}

	public void setDeptcheck02(int deptcheck02) {
		this.deptcheck02 = deptcheck02;
	}

	@Column(name = "FUNCTIONCHECK", precision = 22, scale = 0)

	public int getFunctioncheck() {
		return this.functioncheck;
	}

	public void setFunctioncheck(int functioncheck) {
		this.functioncheck = functioncheck;
	}

	@Column(name = "CHECKRESULT", length = 100)

	public String getCheckresult() {
		return this.checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	@Column(name = "FREE01", length = 100)

	public String getFree01() {
		return this.free01;
	}

	public void setFree01(String free01) {
		this.free01 = free01;
	}

	@Column(name = "FREE02", length = 100)

	public String getFree02() {
		return this.free02;
	}

	public void setFree02(String free02) {
		this.free02 = free02;
	}

	@Column(name = "FREE03", length = 100)

	public String getFree03() {
		return this.free03;
	}

	public void setFree03(String free03) {
		this.free03 = free03;
	}

	@Column(name = "FREE04", length = 100)

	public String getFree04() {
		return this.free04;
	}

	public void setFree04(String free04) {
		this.free04 = free04;
	}

	@Column(name = "FREE05", length = 100)

	public String getFree05() {
		return this.free05;
	}

	public void setFree05(String free05) {
		this.free05 = free05;
	}

	@Column(name = "FREE06", length = 100)

	public String getFree06() {
		return this.free06;
	}

	public void setFree06(String free06) {
		this.free06 = free06;
	}

}