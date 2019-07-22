package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdRole;
import com.nts.teststruts.model.AdRolemenu;
import com.nts.teststruts.util.DBUtil;

public class AdRoleMenuDaoImpl {
	Session session;
	
	
	public List<AdRolemenu> getall()
	{
		List<AdRolemenu> rolemenus;
		

			String hql="from AdRolemenu";
	
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			rolemenus = query.list();
			session.close();
			return rolemenus;
	}
	
	public List<AdRolemenu> getbyrole(String uuidRole)
	{
		List<AdRolemenu> rolemenus;	

			String hql="from AdRolemenu where uuidRole = :uuidRole";
	
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			query.setParameter("uuidRole",uuidRole);
			rolemenus = query.list();
			session.close();
			return rolemenus;
	}
	
	public List<AdRolemenu> getwebbyrole(String uuidRole)
	{
		List<AdRolemenu> rolemenus;	

			String hql="from AdRolemenu where uuidRole = :uuidRole and uuidMenu in (select uuidMenu from AdMenu where type != 0)";
	
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			query.setParameter("uuidRole",uuidRole);
			rolemenus = query.list();
			session.close();
			return rolemenus;
	}
	
	public Boolean insert(AdRolemenu adrolemenu)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adrolemenu);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return true ;
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}
	
	public Boolean delete(AdRolemenu adrolemenu)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adrolemenu);
	        // 提交事务
	        tx.commit();
	        return true;        // 关闭session	 
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}
}
