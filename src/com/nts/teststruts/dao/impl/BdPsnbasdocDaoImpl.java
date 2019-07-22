package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdPsnbasdoc;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.util.DBUtil;

public class BdPsnbasdocDaoImpl {

	
	 Session session;
		

	public List<BdPsnbasdoc> GetAll() throws SQLException
	{
		String hql ="from BdPsnbasdoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdPsnbasdoc> BdPsnbasdoc = query.list();	
		DBUtil.closeSession();
		return BdPsnbasdoc;
	}
	
	public List<BdPsnbasdoc> GetbyMobile(String mobile) throws SQLException
	{
		String hql ="from BdPsnbasdoc where mobile = :mobile";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("mobile",mobile);
		List<BdPsnbasdoc> bdPsnbasdocs  = query.list();	
		DBUtil.closeSession();
		return bdPsnbasdocs;
	}
	
	public BdPsnbasdoc GetByPk(String pk_psnbasdoc) throws SQLException
	{
		String hql ="from BdPsnbasdoc where pk_psnbasdoc =:pk_psnbasdoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_psnbasdoc",pk_psnbasdoc);
		List<BdPsnbasdoc> BdPsnbasdocs = query.list();	
		BdPsnbasdoc bdpsnbasdoc = BdPsnbasdocs.get(0);
		DBUtil.closeSession();
		return bdpsnbasdoc;
	}
	
	public List<BdPsnbasdoc> GetById(String id) throws SQLException
	{
		String hql ="from BdPsnbasdoc where id =:id";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("id",id);
		List<BdPsnbasdoc> BdPsnbasdocs = query.list();	
		DBUtil.closeSession();
		return BdPsnbasdocs;
	}
}
