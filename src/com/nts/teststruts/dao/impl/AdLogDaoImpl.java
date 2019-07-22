package com.nts.teststruts.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdLog;
import com.nts.teststruts.util.DBUtil;

public class AdLogDaoImpl {


	Session session;
	
	public String insert(AdLog adlog)
	{
		String s = "false";
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adlog);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "true" ;
	       }catch(Exception e){
	        return s;
	       }finally{
	            session.close();
	        }
	}
	
	
	
}
