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
import com.nts.teststruts.model.BdJobbasfil;
import com.nts.teststruts.model.IcSapplyH;
import com.nts.teststruts.util.DBUtil;

public class BdJobbasfilDaoImpl {

	 Session session;
	

	public List<BdJobbasfil> GetAll() throws SQLException
	{
		String hql ="from BdJobbasfil";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdJobbasfil> bdjobbasfils = query.list();	
		DBUtil.closeSession();
		return bdjobbasfils;
	}

	
	public List<BdJobbasfil> GetByType(String pkJobtype) throws SQLException
	{
		String hql ="select pkJobbasfil,jobcode,jobname from BdJobbasfil where pkJobtype =:pkJobtype and def1 = '0' order by jobcode";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkJobtype",pkJobtype);
		List<BdJobbasfil> bdjobbasfils = query.list();	
		DBUtil.closeSession();
		return bdjobbasfils;
	}
	
	public BdJobbasfil GetByName(String jobname) throws SQLException
	{
		String hql ="from BdJobbasfil where jobname =:jobname";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("jobname",jobname);
		BdJobbasfil bdjobbasfil = (BdJobbasfil)query.list().get(0);	
		DBUtil.closeSession();
		return bdjobbasfil;
	}
	
	public BdJobbasfil GetByPk(String pkJobbasfil){
		String hql="from BdJobbasfil where pkJobbasfil =:pkJobbasfil";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkJobbasfil",pkJobbasfil);
		BdJobbasfil bdjobbasfil = (BdJobbasfil) query.uniqueResult();	
		DBUtil.closeSession();
		return bdjobbasfil;
	}
}
