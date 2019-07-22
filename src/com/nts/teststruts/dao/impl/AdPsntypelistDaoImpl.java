package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdPsntypelist;
import com.nts.teststruts.model.AdWzlist;
import com.nts.teststruts.util.DBUtil;

public class AdPsntypelistDaoImpl {

	Session session;
	
	public AdPsntypelist getByUUID(String uuid)
	{
		String hql="from AdPsntypelist where uuid = :uuid";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		AdPsntypelist psntypelist = (AdPsntypelist) query.uniqueResult();
		session.close();
		return psntypelist;
	}
	
	public List<AdPsntypelist> getall(){
		String hql="from AdPsntypelist";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<AdPsntypelist> adpsntypelists = query.list();
		session.close();
		return adpsntypelists;	
	}
	
	
	public List<AdPsntypelist> getByPsntype(String psntype){
		String hql="from AdPsntypelist where psntype = :psntype and dr =0";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("psntype",psntype);
		List<AdPsntypelist> adpsntypelists = query.list();
		session.close();
		return adpsntypelists;	
	}
	
	public List<AdPsntypelist> getQAQC(){
		String hql="from AdPsntypelist where psntype in (:psntype) and dr =0";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameterList("psntype", new String[]{"QA","QC"});
		List<AdPsntypelist> adpsntypelists = query.list();
		session.close();
		return adpsntypelists;	
	}
	
	public List<AdPsntypelist> getByPsntype(String psntype,String psnbasdocpk){
		String hql="from AdPsntypelist where psntype = :psntype and psnbasdocpk =:psnbasdocpk";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("psntype",psntype);
		query.setParameter("psnbasdocpk",psnbasdocpk);
		List<AdPsntypelist> adpsntypelists = query.list();
		session.close();
		return adpsntypelists;	
	}

	
	public String insert(AdPsntypelist adpsntypelist)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adpsntypelist);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "success" ;
	       }catch(Exception e){
	        return s = "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdPsntypelist adpsntypelist)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adpsntypelist);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String delete(AdPsntypelist adpsntypelist)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adpsntypelist);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	
}
