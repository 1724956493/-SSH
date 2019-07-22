package com.nts.teststruts.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.mypage.QuyerBonues;
import com.nts.teststruts.util.DBUtil;

public class QueryBonuseDaoImpl {
	Session session;

	public List<QuyerBonues> getDetailbyPhone(String mobile) {
		
		String sql = "select h.billcode,h.billhead,h.project,h.yiju,h.createdate,b.mulct,b.reward,h.type,h.appstatus ,d.jobname from ADQUALITYBILL_SUB b left join ADQUALITYBILL h on h.uuid = b.huuid left join bd_jobbasfil d on d.pk_jobbasfil=h.project where h.appstatus != '0' and  b.psnname in (select pk_psnbasdoc from bd_psnbasdoc where mobile='"
				+ mobile + "')";
		session = DBUtil.currentSession();
		Query query = session.createSQLQuery(sql);
		List<QuyerBonues> qb_list=new ArrayList<QuyerBonues>();
		for(Object o:query.list()){
			QuyerBonues qb = new QuyerBonues((Object[]) o);
			qb_list.add(qb);
		}
		return qb_list;
	}
}
