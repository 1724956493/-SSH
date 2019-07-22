package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdWztype;
import com.nts.teststruts.util.DBUtil;

public class AdWztypeDaoImpl {

	Session session;
	
	public List<AdWztype> getByParentID(String uuid,int type)
	{
		String hql="from AdWztype where wzparent = :uuid and type =:type";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		query.setParameter("type",type);
		List<AdWztype> adwztypes = query.list();
		session.close();
		return adwztypes;
	}
	
	public List<AdWztype> getall()
	{
		String hql="from AdWztype";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<AdWztype> adwztypes = query.list();
		session.close();
		return adwztypes;
	}
	
	public AdWztype getByUUID(String uuid)
	{
		String hql="from AdWztype where uuid = :uuid";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		AdWztype adwztype = (AdWztype) query.uniqueResult();
		session.close();
		return adwztype;
	}
	
	public String insert(AdWztype adwztype)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adwztype);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "success" ;
	       }catch(Exception e){
	        return s = e.toString();
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdWztype adwztype)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adwztype);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String delete(AdWztype adwztype)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adwztype);
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
