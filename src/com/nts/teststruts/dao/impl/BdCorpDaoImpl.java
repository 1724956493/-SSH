package com.nts.teststruts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdCorp;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.IcSapplyH;
import com.nts.teststruts.util.DBUtil;

public class BdCorpDaoImpl {

	 Session session;
	

	public List<BdCorp> GetAll() throws SQLException
	{
		String hql ="from BdCorp";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdCorp> corp = query.list();	
		DBUtil.closeSession();
		return corp;
	}
	
	public BdCorp GetByPk(String pk_corp) throws SQLException
	{
		String hql ="from BdCorp where pk_corp =:pk_corp";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_corp",pk_corp);
		List<BdCorp> BdCorps = query.list();	
		BdCorp bdcorp = BdCorps.get(0);
		DBUtil.closeSession();
		return bdcorp;
	}
	
	public List<BdCorp> GetByCode(String fathercorp) throws SQLException
	{
		String hql ="from BdCorp where fathercorp =:fathercorp";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("fathercorp",fathercorp);
		List<BdCorp> BdCorps = query.list();	
		DBUtil.closeSession();
		return BdCorps;
	}
}
