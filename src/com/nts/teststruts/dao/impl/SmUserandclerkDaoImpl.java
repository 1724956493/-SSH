package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdDeptdoc;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.model.SmUserandclerk;
import com.nts.teststruts.util.DBUtil;

public class SmUserandclerkDaoImpl {

	Session session;
		

		public SmUserandclerk GetByPk(String userid) throws SQLException
		{
			String hql ="from SmUserandclerk where userid =:userid";
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			query.setParameter("userid",userid);
			List<SmUserandclerk> SmUserandclerks = query.list();	
			SmUserandclerk smuserclerk = SmUserandclerks.get(0);
			DBUtil.closeSession();
			return smuserclerk;
		}

}
