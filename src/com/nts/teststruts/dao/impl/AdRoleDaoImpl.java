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

public class AdRoleDaoImpl {
	Session session;
	
	
	public List<AdRole> getall()
	{
		List<AdRole> roles;
		

			String hql="from AdRole";
	
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			roles = query.list();
			session.close();
			return roles;
	}
	
	public Boolean insert(AdRole adrole)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adrole);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return true ;
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}
}
