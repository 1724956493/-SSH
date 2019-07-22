package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdWzlist;
import com.nts.teststruts.model.AdWztype;
import com.nts.teststruts.util.DBUtil;

public class AdWzlistDaoImpl {

	Session session;
	
	
	public AdWzlist getByUUID(String uuid)
	{
		String hql="from AdWzlist where uuid = :uuid";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		AdWzlist adcwzlist = (AdWzlist) query.uniqueResult();
		session.close();
		return adcwzlist;
	}
	
	public List<AdWzlist> getByWztype(String wztype)
	{
		String hql="from AdWzlist where wztype = :wztype";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("wztype",wztype);
		List<AdWzlist> adcwzlists = query.list();
		session.close();
		return adcwzlists;
	}
	
	public List<AdWzlist> getall()
	{
		String hql="from AdWzlist";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<AdWzlist> adcwzlists = query.list();
		session.close();
		return adcwzlists;
	}
	
	public String insert(AdWzlist adwzlist)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adwzlist);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "success" ;
	       }catch(Exception e){
	        return s = e.toString();
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdWzlist adwzlist)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adwzlist);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String delete(AdWzlist adwzlist)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adwzlist);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return e.toString();
	       }finally{
	            session.close();
	        }
	}
}
