package com.nts.teststruts.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdEquipreck;
import com.nts.teststruts.util.DBUtil;

public class AdEquipreckDaoImpl {

	Session session;
	
	public String  insert(AdEquipreck adequipreck)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		
		
		try{
	        session.save(adequipreck);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = adequipreck.getPkEquipck().toString() ;
	       }catch(Exception e){
	        return s;
	       }finally{
	            session.close();
	        }
	}
}
