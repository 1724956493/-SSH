package com.nts.teststruts.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdJobbasfilDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.model.AdPsndocRp;
import com.nts.teststruts.model.BdDeptdoc;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.util.FieldChinese;

public class AdPsndocRpExtServ {

	@FieldChinese(name = "序列号")
	private String UUIDRP;
	@FieldChinese(name = "项目号")
	private String PROJECT;
	@FieldChinese(name = "一卡通号")
	private String YKTCODE;
	@FieldChinese(name = "被检查人")
	private String PSNNAME;
	@FieldChinese(name = "部门名称")
	private String EMPDEPT;
	@FieldChinese(name = "工作区域")
	private String EMPUSERDEPT;
	@FieldChinese(name = "主管领导")
	private String EMPLEADER;
	@FieldChinese(name = "是否违规")
	private String STATUS;	
	@FieldChinese(name = "违规项")
	private String BONUS;
	@FieldChinese(name = "备注")
	private String CKNOTE;
	@FieldChinese(name = "检查人")
	private String OPERATE;
	@FieldChinese(name = "检查人部门")
	private String DEPT;
	@FieldChinese(name = "检查时间")
	private String CREATE_TIME;
	@FieldChinese(name = "图片号")
	private String IMAGE;
	@FieldChinese(name = "检查类型")
	private int TYPE;		
	@FieldChinese(name = "人员信息是否正确")
	private int RIGHT;
	@FieldChinese(name = "是否缴费")
	private int PAYSTATUS;
	
	public AdPsndocRpExtServ(){}
	
	
	public AdPsndocRpExtServ(AdPsndocRp adpsndocrp) throws SQLException{
		BdDeptdocDaoImpl deptdocimpl = new BdDeptdocDaoImpl();
		BdJobbasfilDaoImpl bdjobbasfilimpl = new BdJobbasfilDaoImpl();
		BdPsndocDaoImpl bdpsndocimpl = new BdPsndocDaoImpl();
		this.UUIDRP = adpsndocrp.getUuidRp();
		this.BONUS =adpsndocrp.getBonus();
		this.CKNOTE = adpsndocrp.getCknote();
		this.DEPT =adpsndocrp.getDept();		
		SimpleDateFormat myFmt1=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); 
		this.CREATE_TIME = myFmt1.format(adpsndocrp.getCreateTime());
		if(adpsndocrp.getEmpdept()!=null){
		this.EMPDEPT =deptdocimpl.GetByPk(adpsndocrp.getEmpdept()).getDeptname();}
		if(adpsndocrp.getEmpuserdept()!=null){
		this.EMPUSERDEPT = deptdocimpl.GetByPk(adpsndocrp.getEmpuserdept()).getDeptname();}
		this.EMPLEADER = adpsndocrp.getEmpleader();
		this.IMAGE = adpsndocrp.getImage();
		this.OPERATE =adpsndocrp.getOperate();
		this.STATUS =adpsndocrp.getStatus();
		if(adpsndocrp.getPkPsndoc()!=null){
			this.PSNNAME = bdpsndocimpl.GetByPsnPk(adpsndocrp.getPkPsndoc()).getPsnname();
			this.YKTCODE = bdpsndocimpl.GetByPsnPk(adpsndocrp.getPkPsndoc()).getGroupdef10();
		}
		if(adpsndocrp.getProject()!=null){
		this.PROJECT = bdjobbasfilimpl.GetByPk(adpsndocrp.getProject()).getJobname();}
		this.RIGHT =adpsndocrp.getRight();
		this.TYPE = adpsndocrp.getType();
	}
	
	
	
	
	public String getYktcode() {
		return YKTCODE;
	}


	public void setYktcode(String yktcode) {
		this.YKTCODE = yktcode;
	}


	public String getUuidrp() {
		return UUIDRP;
	}
	public void setUuidRp(String uuidrp) {
		this.UUIDRP = uuidrp;
	}
	public String getPsname() {
		return PSNNAME;
	}
	public void setPsname(String psname) {
		this.PSNNAME = psname;
	}
	public String getStatus() {
		return STATUS;
	}
	public void setStatus(String status) {
		this.STATUS = status;
	}
	public String getCreate_time() {
		return CREATE_TIME;
	}
	public void setCreate_time(String create_time) {
		this.CREATE_TIME = create_time;
	}
	public String getBonus() {
		return BONUS;
	}
	public void setBonus(String bonus) {
		this.BONUS = bonus;
	}
	public String getImage() {
		return IMAGE;
	}
	public void setImage(String image) {
		this.IMAGE = image;
	}
	public String getCknote() {
		return CKNOTE;
	}
	public void setCknote(String cknote) {
		this.CKNOTE = cknote;
	}
	public String getOperate() {
		return OPERATE;
	}
	public void setOperate(String operate) {
		this.OPERATE = operate;
	}
	public String getDept() {
		return DEPT;
	}
	public void setDept(String dept) {
		this.DEPT = dept;
	}
	public int getType() {
		return TYPE;
	}
	public void setType(int type) {
		this.TYPE = type;
	}
	public String getEmpdept() {
		return EMPDEPT;
	}
	public void setEmpdept(String empdept) {
		this.EMPDEPT = empdept;
	}
	public String getEmpuserdept() {
		return EMPUSERDEPT;
	}
	public void setEmpuserdept(String empuserdept) {
		this.EMPUSERDEPT = empuserdept;
	}
	public String getProject() {
		return PROJECT;
	}
	public void setProject(String project) {
		this.PROJECT = project;
	}
	public String getEmpleader() {
		return EMPLEADER;
	}
	public void setEmpleader(String empleader) {
		this.EMPLEADER = empleader;
	}
	public int getRight() {
		return RIGHT;
	}
	public void setRight(int right) {
		this.RIGHT = right;
	}
	public int getPAYSTATUS() {
		return PAYSTATUS;
	}
	public void setPAYSTATUS(int pAYSTATUS) {
		PAYSTATUS = pAYSTATUS;
	}


	@Override
	public String toString() {
		return "AdPsndocRpExtServ [UUIDRP=" + UUIDRP + ", PROJECT=" + PROJECT + ", YKTCODE=" + YKTCODE + ", PSNNAME="
				+ PSNNAME + ", EMPDEPT=" + EMPDEPT + ", EMPUSERDEPT=" + EMPUSERDEPT + ", EMPLEADER=" + EMPLEADER
				+ ", STATUS=" + STATUS + ", BONUS=" + BONUS + ", CKNOTE=" + CKNOTE + ", OPERATE=" + OPERATE + ", DEPT="
				+ DEPT + ", CREATE_TIME=" + CREATE_TIME + ", IMAGE=" + IMAGE + ", TYPE=" + TYPE + ", RIGHT=" + RIGHT
				+ ", PAYSTATUS=" + PAYSTATUS + "]";
	}

}
