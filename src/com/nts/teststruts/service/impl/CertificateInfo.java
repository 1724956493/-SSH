package com.nts.teststruts.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.nts.teststruts.dao.impl.BdDefdocDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.model.HiPsndocGrpdef2;
import com.nts.teststruts.model.HiPsndocGrpdef4;
import com.nts.teststruts.util.FieldChinese;

public class CertificateInfo {
	
	private String  certificateID;
	private String  begindate;
	private String  enddate;	
	private String  certificateType;
	private String  certificatename;
	
	
	public CertificateInfo(){}
	
	public CertificateInfo(HiPsndocGrpdef4 psndocgrpdef4) throws SQLException{
			this.certificateID = psndocgrpdef4.getPkPsndocSub();
			this.begindate = psndocgrpdef4.getBegindate();
			this.enddate  = psndocgrpdef4.getEnddate();
			this.certificatename =  psndocgrpdef4.getGroupdef1();
			this.certificateType =  new BdDefdocDaoImpl().GetByPk(psndocgrpdef4.getGroupdef2()).getDocname();
			
	}
	
	public CertificateInfo(HiPsndocGrpdef2 psndocgrpdef2) throws SQLException{
		this.certificateID = psndocgrpdef2.getPkPsndocSub();
		this.begindate = psndocgrpdef2.getBegindate();
		this.enddate  = psndocgrpdef2.getEnddate();
		this.certificatename =  psndocgrpdef2.getGroupdef1();
		this.certificateType =  new BdDefdocDaoImpl().GetByPk(psndocgrpdef2.getGroupdef3()).getDocname();
}

	public String getCertificateID() {
		return certificateID;
	}

	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificatename() {
		return certificatename;
	}

	public void setCertificatename(String certificatename) {
		this.certificatename = certificatename;
	}

	@Override
	public String toString() {
		return "CertificateInfo [certificateID=" + certificateID + ", begindate=" + begindate + ", enddate=" + enddate
				+ ", certificateType=" + certificateType + ", certificatename=" + certificatename + "]";
	}
	
	
	
	

}
