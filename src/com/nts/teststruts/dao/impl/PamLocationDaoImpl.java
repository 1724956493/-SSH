package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.nts.teststruts.model.BdCorp;
import com.nts.teststruts.model.PamLocation;
import com.nts.teststruts.util.DBUtil;

public class PamLocationDaoImpl {

	 Session session;
		

	public List<PamLocation> GetAll() throws SQLException
	{
		String hql ="from PamLocation";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<PamLocation> PamLocation = query.list();	
		DBUtil.closeSession();
		return PamLocation;
	}
	
	public PamLocation GetByPk(String pk_Location) throws SQLException
	{
		String hql ="from PamLocation where pk_Location =:pk_Location";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_Location",pk_Location);
		List<PamLocation> PamLocations = query.list();	
		PamLocation pamlocation = PamLocations.get(0);
		DBUtil.closeSession();
		return pamlocation;
	}
}
