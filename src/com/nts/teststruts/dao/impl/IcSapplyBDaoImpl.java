package com.nts.teststruts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.IcSapplyH;
import com.nts.teststruts.util.DBUtil;

public class IcSapplyBDaoImpl {

	 Session session;
	

	public List<IcSapplyH> GetAll(String vbillcode) throws SQLException
	{
		
		String hql="from IcSapplyH where vbillcode =:vbillcode";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("vbillcode",vbillcode);
		List<IcSapplyH> sahs = query.list();	
		DBUtil.closeSession();
		return sahs;
	}
	
	
	public void Update(IcSapplyH sah) 
	{
		session =DBUtil.currentSession();
		session.saveOrUpdate(sah);
		DBUtil.closeSession();
	}
}
