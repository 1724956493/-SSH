package com.nts.teststruts.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.CollectReport;
import com.nts.teststruts.util.DBUtil;

public class CollectionReportDaoImpl {

	Session session;
	
	public String update(CollectReport collectreport)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(collectreport);
	        // 提交事务
	        tx.commit();
	        return "true";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}

	public CollectReport getByPK(String onePkCode)
	{
		String hql="from CollectReport where onePkCode=:onePkCode";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("onePkCode",onePkCode);
		CollectReport collectreport = (CollectReport) query.uniqueResult();
		session.close();
		return collectreport;
	}
	
	public CollectReport getByCode(String mark)
	{
		String hql="from CollectReport where mark=:mark";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("mark",mark);
		CollectReport collectreport = (CollectReport) query.uniqueResult();
		session.close();
		return collectreport;
	}
	
	public CollectReport getBypkCollect(String pkCollect)
	{
		String hql="from CollectReport where pkCollect=:pkCollect";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkCollect",pkCollect);
		CollectReport collectreport = (CollectReport) query.uniqueResult();
		session.close();
		return collectreport;
	}
}
