package com.nts.teststruts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdCorp;
import com.nts.teststruts.model.HiPsndocGrpdef2;
import com.nts.teststruts.model.HiPsndocGrpdef4;
import com.nts.teststruts.model.IcSapplyH;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.util.DBUtil;

public class HiPsndocGrpdef2DaoImpl {

	 Session session;
	

	public List<HiPsndocGrpdef2> GetByPK(String pkPsndoc) throws SQLException
	{
		String hql = "from HiPsndocGrpdef2 where pkPsndoc =:pkPsndoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkPsndoc",pkPsndoc);
		List<HiPsndocGrpdef2> hipsndocgrpdef2s = query.list();	
		DBUtil.closeSession();
		return hipsndocgrpdef2s;
	}
	
}
