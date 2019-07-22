package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.util.DBUtil;

public class BdDefdocDaoImpl {

	 Session session;
		

	public List<BdDefdoc> GetAll() throws SQLException
	{
		String hql ="from BdDefdoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdDefdoc> BdDefdoc = query.list();	
		DBUtil.closeSession();
		return BdDefdoc;
	}
	
	public List<BdDefdoc> GetByListPk(String pkDefdoclist) throws SQLException
	{
		String hql ="from BdDefdoc where pkDefdoclist = :pkDefdoclist";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkDefdoclist",pkDefdoclist);
		List<BdDefdoc> BdDefdoc = query.list();	
		DBUtil.closeSession();
		return BdDefdoc;
	}
	
	public BdDefdoc GetByPk(String pk_defdoc) throws SQLException
	{
		String hql ="from BdDefdoc where pk_defdoc =:pk_defdoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_defdoc",pk_defdoc);
		List<BdDefdoc> BdDefdocs = query.list();	
		BdDefdoc defdoc = BdDefdocs.get(0);
		DBUtil.closeSession();
		return defdoc;
	}
}
