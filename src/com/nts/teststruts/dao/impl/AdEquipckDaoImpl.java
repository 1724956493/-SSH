package com.nts.teststruts.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.util.DBUtil;

public class AdEquipckDaoImpl {

	Session session;
	
	public String insert(AdEquipck adequipck)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(adequipck);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = adequipck.getPkEquipckcode() ;
	       }catch(Exception e){
	        return s;
	       }finally{
	            session.close();
	        }
	}

	public AdEquipck getmaxid(String pk_equip)
	{
		String hql="from AdEquipck where pkEquipck=( select max(pkEquipck) FROM AdEquipck where pk_equip=:pk_equip)";

		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_equip",pk_equip);
		AdEquipck adequipck = (AdEquipck) query.uniqueResult();
		session.close();
		return adequipck;
	}
	
	public List groupByPk(String pkMandept,String startdate,String enddate){
		String sql = "select r1.equip_name s1,r1.c1  ,r2.c2  from (select count(*) c1 ,equip_name from pam_equip where pk_mandept = :pkMandept and pk_category !='0001C11000000001SOYZ' group by equip_name) r1 left join (select count(distinct k.pk_equip) c2,p.equip_name from ad_equipck k left join pam_equip p on p.pk_equip = k.pk_equip where p.pk_mandept = :pkMandept and p.pk_category !='0001C11000000001SOYZ' and trunc(k.ckdatetime) between to_date(:startdate,'yyyy-mm-dd')  and to_date(:enddate,'yyyy-mm-dd') group by p.equip_name) r2 on r1.equip_name = r2.equip_name";
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql);
		query.setParameter("pkMandept",pkMandept);
		query.setParameter("startdate",startdate);
		query.setParameter("enddate",enddate);
		List mapinfos = query.list();
	//	Object object = query.list();
		DBUtil.closeSession();		
		return mapinfos;
	}
}
