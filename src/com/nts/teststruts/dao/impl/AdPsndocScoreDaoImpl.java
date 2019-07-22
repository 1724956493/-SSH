package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdPsndocRp;
import com.nts.teststruts.model.AdPsndocscore;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.CollectReport;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.util.DBUtil;

public class AdPsndocScoreDaoImpl {

	Session session;
	
	public String insert(AdPsndocscore adpsndocscore)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adpsndocscore);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = 	adpsndocscore.getUuidPs() ;
	       }catch(Exception e){
	        return s;
	       }finally{
	            session.close();
	        }
	}
	
	public AdPsndocscore getbypsndoc(String pk_psndoc,int scoretype)
	{
		String hql="from AdPsndocscore where pkPsndoc= :pkPsndoc and scoretype =:scoretype";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkPsndoc",pk_psndoc);
		query.setParameter("scoretype",scoretype);
		AdPsndocscore adpsndocscore = (AdPsndocscore) query.uniqueResult();
		session.close();
		return adpsndocscore;
	}
	
	public String update(AdPsndocscore adpsndocscore)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adpsndocscore);
	        // 提交事务
	        tx.commit();
	        return "true";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
}
