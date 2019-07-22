package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdDeptdoc;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.util.DBUtil;

public class BdDeptdocDaoImpl {

	
	 Session session;
		

	public List<BdDeptdoc> GetAll() throws SQLException
	{
		String hql ="from BdDeptdoc where pk_corp = '1002' and hrcanceled ='N'";       //
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdDeptdoc> Deptdoc = query.list();	
		DBUtil.closeSession();
		return Deptdoc;
	}
	
	public List<BdDeptdoc> GetByFtPk(String pkFathedept) throws SQLException
	{
		String hql ="from BdDeptdoc where pkFathedept =:pkFathedept";       //where pk_corp = 1002
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkFathedept",pkFathedept);
		List<BdDeptdoc> Deptdoc = query.list();	
		DBUtil.closeSession();
		return Deptdoc;
	}
	
	public List<BdDeptdoc> GetByCorp(String pkCorp) throws SQLException
	{
		String hql ="from BdDeptdoc where pkCorp =:pkCorp and pkFathedept is null";       //where pk_corp = 1002
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkCorp",pkCorp);
		List<BdDeptdoc> Deptdoc = query.list();	
		DBUtil.closeSession();
		return Deptdoc;
	}
	
	public BdDeptdoc GetByPk(String pk_deptdoc) throws SQLException
	{
		String hql ="from BdDeptdoc where pk_deptdoc =:pk_deptdoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_deptdoc",pk_deptdoc);
		List<BdDeptdoc> Deptdocs = query.list();	
		BdDeptdoc deptdoc = Deptdocs.get(0);
		DBUtil.closeSession();
		return deptdoc;
	}
}
