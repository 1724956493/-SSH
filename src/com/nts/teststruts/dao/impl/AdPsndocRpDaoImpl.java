package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdPsndocRp;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.Employee;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.AdPsndocRpExtServ;
import com.nts.teststruts.service.impl.AdPsndocRpServ;
import com.nts.teststruts.service.impl.adPsndocRpReport;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;

public class AdPsndocRpDaoImpl {

	Session session;
	
	
	public String insert(AdPsndocRp adpsndocrp)
	{
		String s ="false";
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		
		
		try{
	        session.save(adpsndocrp);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "success" ;
	       }catch(Exception e){
	        return s;
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdPsndocRp adpsndocrp)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(adpsndocrp);
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
	
	public String delete(AdPsndocRp adpsndocrp)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(adpsndocrp);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public AdPsndocRp getbyUUID(String uuidRp){
		String hql="from AdPsndocRp where uuidRp = :uuidRp";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidRp",uuidRp);
		AdPsndocRp aspsndocrp = (AdPsndocRp)query.uniqueResult();
		session.close();
		return aspsndocrp;		
	}
	
	public List<AdPsndocRp> getbyEmpPK(String pkPsndoc, int type ){
		String hql="from AdPsndocRp2 where pkPsndoc=:pkPsndoc and type = :type";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkPsndoc",pkPsndoc);
		query.setParameter("type",type);
		List<AdPsndocRp> aspsndocrps = query.list();
		session.close();
		return aspsndocrps;		
	}
	
	
	public int getTotalbyParam(String startdate ,String enddate,int type)
	{
		String hql="select count(*) from AdPsndocRp where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("startdate",startdate);
		query.setParameter("enddate",enddate);
		query.setParameter("type",type);
		Number total = (Number)query.uniqueResult();	
		DBUtil.closeSession();
		return total.intValue();
	}
	

	public List<AdPsndocRp> getbyParam(AdPsndocRpServ serv){
		String hql="from AdPsndocRp where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type and dr =0";
		if(!ComUtil.isEmptyString(serv.getBonus())){
			hql = hql+" and bonus = :bonus ";
		}
		if(!ComUtil.isEmptyString(serv.getEmpleader())){
			hql = hql+" and empleader = :empleader ";
		}
		if(!ComUtil.isEmptyString(serv.getProject())){
			hql = hql+" and project = :project ";
		}
		if(!ComUtil.isEmptyString(serv.getOperate())){
			hql = hql+" and operate = :operate ";
		}
		if(!ComUtil.isEmptyString(serv.getStatus())){
			hql = hql+" and status = :status ";
		}
		if(!ComUtil.isEmptyString(serv.getCknote())){
			hql = hql+" and cknote = :cknote ";
		}	
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
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
	
		List<AdPsndocRp> aspsndocrps = query.list();
		session.close();
		return aspsndocrps;				
	}
	
	public List<AdPsndocRpExtServ> getbyParam2(AdPsndocRpServ serv){
		String sql="select * from AdPsndocRp "
				+ "where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type and dr =0";
		if(!ComUtil.isEmptyString(serv.getBonus())){
			sql = sql+" and bonus = :bonus ";
		}
		if(!ComUtil.isEmptyString(serv.getEmpleader())){
			sql = sql+" and empleader = :empleader ";
		}
		if(!ComUtil.isEmptyString(serv.getProject())){
			sql = sql+" and project = :project ";
		}
		if(!ComUtil.isEmptyString(serv.getOperate())){
			sql = sql+" and operate = :operate ";
		}
		if(!ComUtil.isEmptyString(serv.getStatus())){
			sql = sql+" and status = :status ";
		}
		if(!ComUtil.isEmptyString(serv.getCknote())){
			sql = sql+" and cknote = :cknote ";
		}	
		session =DBUtil.currentSession();
		Query query =session.createQuery(sql);
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
	
		List<AdPsndocRpExtServ> aspsndocrps = query.list();
		session.close();
		return aspsndocrps;				
	}
	
	
	public List getQCReport(String starttime,String endtime,int type){
		String sql= "select d.deptname parentdept,c.deptname dept,t.psnname,t.psndocpk,decode(rp.cnn,null,0,rp.cnn)  from (select * from ad_psntypelist where psntype in ('QC','QA')) t "
			+"	left join (select operate,count(*) as cnn from ad_psndoc_rp2 where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type and dr =0  group by operate) rp on t.psndocpk = rp.operate "
			+"	left join bd_deptdoc c on c.pk_deptdoc = t.deptpk "
			+"	left join bd_deptdoc d on d.pk_deptdoc = t.parentdeptpk "
			+"  order by parentdept desc,dept desc,rp.cnn desc"
					;
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql);
		query.setParameter("startdate",starttime);
		query.setParameter("enddate",endtime);
		query.setParameter("type",type);
		List adPsndocRpReport = query.list();	
		return adPsndocRpReport;
	}
	
	public List getProjectReport(String starttime,String endtime,int type){
		String sql= "select l.jobname,rp.project,rp.cnn from (select project,count(*) as cnn from ad_psndoc_rp2 where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type and dr =0  group by project) rp "
					+" left join bd_jobbasfil l on rp.project = l.pk_jobbasfil "
					+" order by rp.cnn desc "
					;
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql);
		query.setParameter("startdate",starttime);
		query.setParameter("enddate",endtime);
		query.setParameter("type",type);
		List adPsndocRpReport = query.list();	
		return adPsndocRpReport;
	}
	
	public List getWztypeReport(String starttime,String endtime,int type){
		String sql= "select p.wzname,t.wzlistname,t.uuid,decode(rp.cnn,null,0,rp.cnn) count,t.wzlisttype from ad_wzlist t left join "
				+ " (select bonus,count(*) cnn from ad_psndoc_rp2 where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type and dr =0 and status = '违规'  group by  bonus) rp on rp.bonus = t.uuid "
			    + " left join ad_wztype p on t.wztype = p.uuid "
				+ "  where p.wzcode like 'ZLGL%' order by p.wzname,count desc"	;
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql);
		query.setParameter("startdate",starttime);
		query.setParameter("enddate",endtime);
		query.setParameter("type",type);
		List adPsndocRpReport = query.list();	
		return adPsndocRpReport;
	}


	public List getEmpleaderReport(String starttime,String endtime,int type){
		String sql= "select d.deptname pardept,c.deptname dept,l.psnname,rp.empleader,rp.cnn from (select empleader,count(*) as cnn from ad_psndoc_rp2 where trunc(create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and type = :type and dr =0   group by  empleader)  rp "
				+ " left join ad_psntypelist l on rp.empleader = l.uuid "
				+ " left join bd_deptdoc c on c.pk_deptdoc = l.deptpk "
				+ " left join bd_deptdoc d on d.pk_deptdoc = l.parentdeptpk "
			 	+ " order by pardept,dept,rp.cnn desc " 
				;
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql);
		query.setParameter("startdate",starttime);
		query.setParameter("enddate",endtime);
		query.setParameter("type",type);
		List adPsndocRpReport = query.list();	
		return adPsndocRpReport;
	}

	public List getDeptReport(String starttime, String endtime, int type) {
		String sql="select e.deptname parentdept,d.deptname dept,w.wzname,sc.wzlistname,sc.cnn from "
				+" (select t.parentdeptpk,t.deptpk,l.wztype,l.wzlistname,count(*) cnn from ad_psndoc_rp2 p  "
				+" left join ad_psntypelist t on p.empleader = t.uuid  "
				+" left join ad_wzlist l on l.uuid = p.bonus "
				+" where trunc(p.create_Time) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') and p.type = :type and p.dr =0 and p.status ='违规' "
				+" group by l.wzlistname,l.wztype,t.deptpk,t.parentdeptpk order by t.parentdeptpk,t.deptpk,l.wztype,cnn desc,l.wzlistname ) sc "
				+" left join ad_wztype w on w.uuid = sc.wztype "
				+" left join bd_deptdoc d on d.pk_deptdoc = sc.deptpk "
				+" left join bd_deptdoc e on e.pk_deptdoc = sc.parentdeptpk "
				;
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql);
		query.setParameter("startdate",starttime);
		query.setParameter("enddate",endtime);
		query.setParameter("type",type);
		List adPsndocRpReport = query.list();	
		return adPsndocRpReport;
	}
}



