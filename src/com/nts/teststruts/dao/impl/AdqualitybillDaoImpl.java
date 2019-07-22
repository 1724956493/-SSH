package com.nts.teststruts.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.nts.teststruts.model.AdCarinfo;
import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdPsndocRp2;
import com.nts.teststruts.model.Adqualitybill;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.DateUtil;

public class AdqualitybillDaoImpl {

	Session session;
	
	public Boolean insert(Adqualitybill adqualitybill)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adqualitybill);
	        // 提交事务
	        tx.commit();       // 关闭session 
	        return true;
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}
	
	public Boolean update(Adqualitybill adqualitybill)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adqualitybill);
	        // 提交事务
	        tx.commit();
	        return true;        // 关闭session	 
	       }catch(Exception e){
	        return false;
	       }finally{  
	            session.close();
	       }
	}
	
	public Boolean delete(Adqualitybill adqualitybill)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adqualitybill);
	        // 提交事务
	        tx.commit();
	        return true;        // 关闭session	 
	       }catch(Exception e){
	        return false;
	       }finally{
	            session.close();
	        }
	}

	public Adqualitybill getbyuuid(String uuid)
	{
		String hql="from Adqualitybill where uuid = :uuid";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuid",uuid);
		Adqualitybill adqualitybill = (Adqualitybill)query.uniqueResult();
		session.close();
		return adqualitybill;
	}


	
	public List<Adqualitybill> getall(){
		String hql="from Adqualitybill";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<Adqualitybill> adqualitybills = query.list();
		session.close();
		return adqualitybills;		
	}
	
	public List<Adqualitybill> getbybillcode(String billcode){
		String hql="from Adqualitybill where billcode = :billcode";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("billcode",billcode);
		List<Adqualitybill> adqualitybills = query.list();
		session.close();
		return adqualitybills;		
	}
	
	public List queryBySql(String sql) {  
		Query query =DBUtil.currentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();    
        return list;    
    } 
	
	public List<Adqualitybill> getallbytype(Map<String,String> map){
		String hql="from Adqualitybill where type = :type";
		if(!ComUtil.isEmptyString(map.get("begindate"))){
			hql = hql + " and to_char(ts,'yyyy-MM-dd') >= :begindate ";
		}
		if(!ComUtil.isEmptyString(map.get("enddate"))){
			hql = hql + " and to_char(ts,'yyyy-MM-dd') <= :enddate ";				
		}
		if(!ComUtil.isEmptyString(map.get("billcode"))){
			hql = hql + " and billcode = :billcode ";
		}
		if(!ComUtil.isEmptyString(map.get("dept"))){
			hql = hql + " and dept = :dept ";
		}
		if(!ComUtil.isEmptyString(map.get("project"))){
			hql = hql + " and project = :project ";
		}
		if(!ComUtil.isEmptyString(map.get("appstatus"))){
			hql = hql + " and appstatus in :appstatus ";
		}
		if(!ComUtil.isEmptyString(map.get("fenxi"))){
			hql = hql + " and fenxi = :fenxi ";
		}
		if(!ComUtil.isEmptyString(map.get("manager"))){
			hql = hql + " and uuid in :manager ";
		}
		session =DBUtil.currentSession();
		try{
			Query query =session.createQuery(hql);
			query.setParameter("type",map.get("resourceid")); 
			
			if(!ComUtil.isEmptyString(map.get("begindate"))){
				query.setParameter("begindate", map.get("begindate").toString());
			}
			if(!ComUtil.isEmptyString(map.get("enddate"))){
				query.setParameter("enddate", map.get("enddate").toString());		
			}
			if(!ComUtil.isEmptyString(map.get("billcode"))){
				query.setParameter("billcode",map.get("billcode").toString());	
			}
			if(!ComUtil.isEmptyString(map.get("dept"))){
				query.setParameter("dept",map.get("dept").toString());
			}
			if(!ComUtil.isEmptyString(map.get("dept"))){
				query.setParameter("dept",map.get("dept").toString());
			}
			if(!ComUtil.isEmptyString(map.get("project"))){
				query.setParameter("project",map.get("project").toString());
			}
			if(!ComUtil.isEmptyString(map.get("appstatus"))){
				query.setParameterList("appstatus",map.get("appstatus").toString().split(","));
			}
			if(!ComUtil.isEmptyString(map.get("fenxi"))){
				query.setParameter("fenxi",map.get("fenxi").toString());
			}
			if(!ComUtil.isEmptyString(map.get("manager"))){
				query.setParameterList("manager",map.get("manager").toString().split(","));
			}
		
			List<Adqualitybill> adqualitybills = query.list();
			return adqualitybills;
	       }catch(Exception e){
	        return null;
	       }finally{
	            session.close();
	        }				
	}
}
