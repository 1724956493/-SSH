package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import com.nts.teststruts.model.AdEquipckCheckcontent;
import com.nts.teststruts.util.DBUtil;

public class AdEquipckCheckcontentDaoImpl {
	Session session;

	public List<AdEquipckCheckcontent> queryList(String className,String type)
	{

		String hql="from AdEquipckCheckcontent where className = '"+className+"' and "+type+"=1";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<AdEquipckCheckcontent> r = query.list();
		session.close();
		return r;
	}
	
	public AdEquipckCheckcontent query(String uuid)
	{

		String hql="from AdEquipckCheckcontent where uuid = '"+uuid+"'";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		AdEquipckCheckcontent r = (AdEquipckCheckcontent) query.uniqueResult();
		session.close();
		return r;
	}
}
