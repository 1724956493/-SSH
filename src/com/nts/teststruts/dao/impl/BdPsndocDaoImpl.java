package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;

public class BdPsndocDaoImpl {

	
	 Session session;
		

	public List<BdPsndoc> GetAll() throws SQLException
	{
		String hql ="from BdPsndoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdPsndoc> BdPsndoc = query.list();	
		DBUtil.closeSession();
		return BdPsndoc;
	}
	
	public List<BdPsndoc> Getbypsnname(String psnname) throws SQLException
	{
		String hql ="from BdPsndoc where psnname = :psnname";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("psnname",psnname);
		List<BdPsndoc> BdPsndoc = query.list();	
		DBUtil.closeSession();
		return BdPsndoc;
	}
	
	public BdPsndoc GetByPk(String pk_psnbasdoc) throws SQLException
	{
		String hql ="from BdPsndoc where dr !=1 and psnclscope in (0,5) and pk_psnbasdoc =:pk_psnbasdoc ";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_psnbasdoc",pk_psnbasdoc);
		List<BdPsndoc> BdPsndocs = query.list();	
		BdPsndoc bdpsndoc = BdPsndocs.get(0);
		DBUtil.closeSession();
		return bdpsndoc;
	}
	
	public BdPsndoc GetByPsnPk(String pk_psndoc) throws SQLException
	{
		String hql ="from BdPsndoc where dr !=1 and pk_psndoc =:pk_psndoc";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_psndoc",pk_psndoc);
		List<BdPsndoc> BdPsndocs = query.list();	
		BdPsndoc bdpsndoc = BdPsndocs.get(0);
		DBUtil.closeSession();
		return bdpsndoc;
	}
	
	public BdPsndoc GetByYktCode(String groupdef10) throws SQLException
	{
		String hql ="from BdPsndoc where dr !=1 and groupdef10 =:groupdef10";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("groupdef10",groupdef10);
		List<BdPsndoc> BdPsndocs = query.list();	
		BdPsndoc bdpsndoc = BdPsndocs.get(0);
		DBUtil.closeSession();
		return bdpsndoc;
	}
	
	public List<BdPsndoc> getManager(){
		String hql ="select psnname,pkPsndoc from BdPsndoc where jobrank ='0001C1100000000001QC' and psnclscope = 0 order by psnname";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<BdPsndoc> bdPsndocs = query.list();	
		DBUtil.closeSession();
		return bdPsndocs;		
	}
	
	public List getByPsnname(String psnname){
		String sql ="select c.pk_psndoc,c.pk_psnbasdoc,c.psnname psnname,d.id id,t.deptname deptname from bd_psndoc c left join bd_psnbasdoc d on c.pk_psnbasdoc = d.pk_psnbasdoc" 
						+" left join bd_deptdoc t on t.pk_deptdoc = c.pk_deptdoc where c.psnclscope = 0 and c.psnname like :psnname";
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql)
				.addScalar("pk_psndoc", StandardBasicTypes.STRING)
				.addScalar("pk_psnbasdoc", StandardBasicTypes.STRING)
				.addScalar("psnname", StandardBasicTypes.STRING)
				.addScalar("id", StandardBasicTypes.STRING)
				.addScalar("deptname", StandardBasicTypes.STRING);

		query.setParameter("psnname", psnname);
		List list = query.list();
		DBUtil.closeSession();
		return list;		
	}
	
	public List getAllByPsnname(String psnname,String pkdeptdoc){
		String sql ="select c.pk_psndoc,c.pk_psnbasdoc,c.psnname psnname,d.id id,t.deptname deptname from bd_psndoc c left join bd_psnbasdoc d on c.pk_psnbasdoc = d.pk_psnbasdoc" 
						+" left join bd_deptdoc t on t.pk_deptdoc = c.pk_deptdoc where c.psnclscope in (0,5) and c.psnname like :psnname ";
		if(!ComUtil.isEmptyString(pkdeptdoc)){
			sql = sql+" and t.pk_deptdoc = :pk_deptdoc";
		}
		session =DBUtil.currentSession();
		Query query =session.createSQLQuery(sql)
				.addScalar("pk_psndoc", StandardBasicTypes.STRING)
				.addScalar("pk_psnbasdoc", StandardBasicTypes.STRING)
				.addScalar("psnname", StandardBasicTypes.STRING)
				.addScalar("id", StandardBasicTypes.STRING)
				.addScalar("deptname", StandardBasicTypes.STRING);

		query.setParameter("psnname", psnname);
		if(!ComUtil.isEmptyString(pkdeptdoc)){
			query.setParameter("pk_deptdoc", pkdeptdoc);
		}
		List list = query.list();
		DBUtil.closeSession();
		return list;		
	}
	
	
	
}
