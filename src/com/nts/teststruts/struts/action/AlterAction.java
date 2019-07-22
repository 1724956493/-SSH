package com.nts.teststruts.struts.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.nts.teststruts.dao.impl.IcSapplyHDaoImpl;
import com.nts.teststruts.model.*;
import com.nts.teststruts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AlterAction extends ActionSupport {
	
	public BdCorp getCorp() {
		return corp;
	}

	public void setCorp(BdCorp corp) {
		this.corp = corp;
	}

	public IcSapplyH getSapply_h() {
		return sapply_h;
	}

	public void setSapply_h(IcSapplyH sapply_h) {
		this.sapply_h = sapply_h;
	}

	private BdCorp corp;	
	private IcSapplyH sapply_h;	
	
	public String add() {		
		sapply_h.setPkCorp(corp.getPkCorp());
		IcSapplyHDaoImpl sahDI = new IcSapplyHDaoImpl();
		sahDI.Update(sapply_h);
		return SUCCESS;
	}
	
	
	





	

	
}
