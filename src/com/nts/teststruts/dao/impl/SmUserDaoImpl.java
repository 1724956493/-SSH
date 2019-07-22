package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.util.DBUtil;

public class SmUserDaoImpl {

	Session session;

	public List<SmUser> GetAll(String usercode) throws SQLException {
		String hql = "from SmUser where user_code =:user_code and  locked_tag = 'N'";
		session = DBUtil.currentSession();
		Query query = session.createQuery(hql);
		query.setParameter("user_code", usercode);
		List<SmUser> user = query.list();
		DBUtil.closeSession();
		return user;
	}

	public List<SmUser> getall() throws SQLException {
		String hql = "from SmUser";
		session = DBUtil.currentSession();
		Query query = session.createQuery(hql);
		List<SmUser> user = query.list();
		DBUtil.closeSession();
		return user;
	}

	public List<SmUser> getbyphone(String phone) throws SQLException {
		String sql = "select * from SM_USER a where a.cuserid in( select s.userid from SM_USERANDCLERK  s where s.pk_psndoc in (select pk_psnbasdoc from bd_psnbasdoc where mobile='"
				+ phone + "'))";
		session = DBUtil.currentSession();
		
		
		Query query = session.createSQLQuery(sql).addEntity(SmUser.class);
		List<SmUser> data = query.list();
		if (data.size() > 1) {
			sql += " and a.user_name in (select psnname from bd_psnbasdoc where mobile='" + phone + "')";
			session = DBUtil.currentSession();
			query = session.createSQLQuery(sql).addEntity(SmUser.class);
			data = query.list();
		}
		return data;
	}

}
