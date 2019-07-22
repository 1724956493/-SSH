package com.nts.teststruts.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.nts.teststruts.util.DBUtil;

public class BaseQueryDaoImpl {

	Session session;
	
	public List<Object[]> objBySql(String sql) {    
        List<Object[]> list = DBUtil.currentSession().createSQLQuery(sql).list();    
        return list;    
    }
	
	public List<Map> mapBySql(String sql) {    
		Query query =DBUtil.currentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list=query.list();    
        return list;   
    }
	
	public int excuteBySql(String sql){    
		SQLQuery query = DBUtil.currentSession().createSQLQuery(sql);
		int result = query.executeUpdate();  
        return result;    
    }  
}
