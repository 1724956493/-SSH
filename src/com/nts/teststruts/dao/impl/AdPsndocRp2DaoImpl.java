package com.nts.teststruts.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.nts.teststruts.model.AdPsndocRp;
import com.nts.teststruts.model.AdPsndocRp2;
import com.nts.teststruts.service.impl.AdPsndocRpExtServ;
import com.nts.teststruts.service.impl.AdPsndocRpServ;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;

public class AdPsndocRp2DaoImpl {
	
	Session session;
	
	public String insert(AdPsndocRp2 adpsndocrp2)
	{
		String s ="false";
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		
		
		try{
	        session.save(adpsndocrp2);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "success" ;
	       }catch(Exception e){
	        return s;
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdPsndocRp2 adpsndocrp2)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adpsndocrp2);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	    	   System.out.println(e);
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String delete(AdPsndocRp2 adpsndocrp2)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adpsndocrp2);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public AdPsndocRp2 getbyUUID(String uuidRp){
		String hql="from AdPsndocRp2 where uuid = :uuidRp";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidRp",uuidRp);
		AdPsndocRp2 aspsndocrp2 = (AdPsndocRp2)query.uniqueResult();
		session.close();
		return aspsndocrp2;		
	}
	
	public List<AdPsndocRp2> getbyEmpPK(String pkPsndoc, int type ){
		String hql="from AdPsndocRp2 where pkPsndoc=:pkPsndoc and type = :type and status ='违规'";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkPsndoc",pkPsndoc);
		query.setParameter("type",type);
		List<AdPsndocRp2> aspsndocrps = query.list();
		session.close();
		return aspsndocrps;		
	}
	
	
	public List getbyParam2(AdPsndocRpServ serv){
		String sql="select rp.uuid uuidRp,f.jobname project,c.groupdef10 yktcode,c.psnname psnname,(select deptname from bd_deptdoc where rp.empdept = bd_deptdoc.pk_deptdoc) empdept,(select deptname from bd_deptdoc where rp.empuserdept = bd_deptdoc.pk_deptdoc) empuserdept,"
				+" e.psnname empleader, TO_CHAR(rp.create_time,'YYYY-MM-DD HH24:MI:SS') create_time,t.wzlistname bonus,rp.status status,rp.cknote cknote,rp.operate operate,rp.image image,rp.type type,rp.right right,rp.solvestatus solvestatus,rp.paystatus paystatus from ad_psndoc_rp2 rp "
				+" left join ad_psntypelist e on e.uuid = rp.empleader "
				+" left join ad_wzlist t on t.uuid = rp.bonus "
				+" left join bd_psndoc c on c.pk_psndoc = rp.pk_psndoc "
				+" left join bd_jobbasfil f on f.pk_jobbasfil = rp.project "
				+ "where trunc(rp.create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and rp.type = :type and rp.dr =0 ";
		if(!ComUtil.isEmptyString(serv.getBonus())){
			sql = sql+" and rp.bonus = :bonus ";
		}
		if(!ComUtil.isEmptyString(serv.getEmpleader())){
			sql = sql+" and rp.empleader = :empleader ";
		}
		if(!ComUtil.isEmptyString(serv.getProject())){
			sql = sql+" and rp.project = :project ";
		}
		if(!ComUtil.isEmptyString(serv.getOperate())){
			sql = sql+" and rp.operate = :operate ";
		}
		if(!ComUtil.isEmptyString(serv.getStatus())){
			sql = sql+" and rp.status = :status ";
		}
		if(!ComUtil.isEmptyString(serv.getCknote())){
			sql = sql+" and rp.cknote = :cknote ";
		}
		if(serv.getPaystatus() == 1){
			sql = sql+" and rp.paystatus = :paystatus ";
		}	
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter("startdate",serv.getBegindate());
		query.setParameter("enddate",serv.getEnddate());
		query.setParameter("type",serv.getType());
		if(!ComUtil.isEmptyString(serv.getBonus())){
			query.setParameter("bonus",serv.getBonus());  
		}
		if(!ComUtil.isEmptyString(serv.getEmpleader())){
			query.setParameter("empleader",serv.getEmpleader());
		}
		if(!ComUtil.isEmptyString(serv.getProject())){
			query.setParameter("project",serv.getProject());
		}
		if(!ComUtil.isEmptyString(serv.getOperate())){
			query.setParameter("operate",serv.getOperate());
		}
		if(!ComUtil.isEmptyString(serv.getStatus())){
			query.setParameter("status",serv.getStatus());
		}	
		if(!ComUtil.isEmptyString(serv.getCknote())){
			query.setParameter("cknote",serv.getCknote()); 
		}
		if(serv.getPaystatus() == 1){
			query.setParameter("paystatus",0);
		}	
	
		List<Map> aspsndocrps = query.list();
		session.close();
		return aspsndocrps;				
	}
	
}
