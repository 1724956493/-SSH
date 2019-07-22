package com.nts.teststruts.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdCarinfo;
import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdPsndocRp2;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.util.DBUtil;

public class AdCarinfoDaoImpl {

	Session session;
	
	public String insert(AdCarinfo adcarinfo)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adcarinfo);
	        // 提交事务
	        tx.commit();       // 关闭session 
	        return "success";
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdCarinfo adcarinfo)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adcarinfo);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{  
	            session.close();
	       }
	}
	
	public String delete(AdCarinfo adcarinfo)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adcarinfo);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}

	public AdCarinfo getbyuuid(String uuid)
	{
		String hql="from AdCarinfo where uuid = :uuid";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		AdCarinfo adcarinfo = (AdCarinfo)query.uniqueResult();
		session.close();
		return adcarinfo;
	}
	

	public List<AdCarinfo> getbyCarid(String carid){
		String hql="from AdCarinfo where carid = :carid";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("carid",carid);
		List<AdCarinfo> adcarinfos = query.list();
		session.close();
		return adcarinfos;		
	}
	
	public List<AdCarinfo> getall(){
		String hql="from AdCarinfo";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<AdCarinfo> adcarinfos = query.list();
		session.close();
		return adcarinfos;		
	}
}
