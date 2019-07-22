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
import com.nts.teststruts.model.AdqualitybillSub;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.util.DBUtil;

public class AdqualitybillSubDaoImpl {

	Session session;
	
	public Boolean insert(AdqualitybillSub adqualitybillsub)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adqualitybillsub);
	        // 提交事务
	        tx.commit();       // 关闭session 
	        return true;
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}
	
	public Boolean update(AdqualitybillSub adqualitybillsub)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adqualitybillsub);
	        // 提交事务
	        tx.commit();
	        return true;        // 关闭session	 
	       }catch(Exception e){
	        return false;
	       }finally{  
	            session.close();
	       }
	}
	
	public Boolean delete(AdqualitybillSub adqualitybillsub)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adqualitybillsub);
	        // 提交事务
	        tx.commit();
	        return true;        // 关闭session	 
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}

	public AdqualitybillSub getbyuuid(String uuid)
	{
		String hql="from AdqualitybillSub where uuid = :uuid";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		AdqualitybillSub adqualitybillsub = (AdqualitybillSub)query.uniqueResult();
		session.close();
		return adqualitybillsub;
	}
	

	public List<AdqualitybillSub> getbyHuuid(String huuid){
		String hql="from AdqualitybillSub where huuid = :huuid";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("huuid",huuid);
		List<AdqualitybillSub> adqualitybillsubs = query.list();
		session.close();
		return adqualitybillsubs;		
	}
	
	public List<AdqualitybillSub> getbypsnname(String psnname){
		String hql="from AdqualitybillSub where psnname = :psnname";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("psnname",psnname);
		List<AdqualitybillSub> adqualitybillsubs = query.list();
		session.close();
		return adqualitybillsubs;		
	}
	
	public List<AdqualitybillSub> getall(){
		String hql="from AdqualitybillSub";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<AdqualitybillSub> adqualitybillsubs = query.list();
		session.close();
		return adqualitybillsubs;		
	}
}
