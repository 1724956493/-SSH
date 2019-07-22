package com.nts.teststruts.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdEquipreck;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.Employee;
import com.nts.teststruts.util.DBUtil;

public class AdMenuDaoImpl {
	Session session;
	
	public List<AdMenu> getByRole(String uuidMenu ,String cuserid)
	{
		String hql="from AdMenu where type =1 and parentid = :uuidMenu "  
				  +	" and uuidMenu in ( select uuidMenu from AdRolemenu where uuidRole in (select uuidRole from AdUserrole where cuserid = :cuserid))" 
				;
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidMenu",uuidMenu);
		query.setParameter("cuserid",cuserid);
		List<AdMenu> menus = query.list();
		session.close();
		return menus;
	}
	
	public List<AdMenu> getResourceByCuserid(String uuidMenu ,String cuserid)
	{
		String hql="from AdMenu where type !=0 and parentid = :uuidMenu "  
				  +	" and uuidMenu in ( select uuidMenu from AdRolemenu where uuidRole in (select uuidRole from AdUserrole where cuserid = :cuserid))" 
				;
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidMenu",uuidMenu);
		query.setParameter("cuserid",cuserid);
		List<AdMenu> menus = query.list();
		session.close();
		return menus;
	}
	
	
	
	public List<AdMenu> getbyUserPk(String cuserid)
	{
		List<AdMenu> menus;
		
		if(cuserid!=null)
		{
			String hql="from AdMenu where type =0 and uuidMenu in " +
					"( select uuidMenu from AdRolemenu where uuidRole in " +
					" (select uuidRole from AdUserrole where cuserid = :cuserid)) order by menucode";
	
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			query.setParameter("cuserid",cuserid);
			menus = query.list();
			session.close();
			return menus;
		}
		
		return null;
	}
	
	public List<AdMenu> getall()
	{
		List<AdMenu> menus;	

			String hql="from AdMenu";
	
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			menus = query.list();
			session.close();
			return menus;
	}
	
	public List<AdMenu> getByParentID(String uuidMenu)
	{
		String hql="from AdMenu where parentid = :uuidMenu and type =1";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidMenu",uuidMenu);
		List<AdMenu> menus = query.list();
		session.close();
		return menus;
	}
	
	public List<AdMenu> getByResourceParentID(String uuidMenu)
	{
		String hql="from AdMenu where parentid = :uuidMenu and type != 0";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidMenu",uuidMenu);
		List<AdMenu> menus = query.list();
		session.close();
		return menus;
	}
	

	public AdMenu getByAction(String mainview)
	{
		String hql="from AdMenu where action like :action and type = 1";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("action","%"+mainview+"%");
		AdMenu menu = (AdMenu) query.uniqueResult();
		session.close();
		return menu;
	}
	
	public AdMenu getByUUID(String uuidMenu)
	{
		String hql="from AdMenu where uuidMenu = :uuidMenu";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("uuidMenu",uuidMenu);
		AdMenu menu = (AdMenu) query.uniqueResult();
		session.close();
		return menu;
	}
	
	public String insert(AdMenu admenu)
	{
		String s =null;
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();		
		
		try{
	        session.save(admenu);
	        // 提交事务
	        tx.commit();       // 关闭session	 
	         
	        return s = "success" ;
	       }catch(Exception e){
	        return s = e.toString();
	       }finally{
	            session.close();
	        }
	}
	
	public String update(AdMenu admenu)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.update(admenu);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return "false";
	       }finally{
	            session.close();
	        }
	}
	
	public String delete(AdMenu admenu)
	{
		session =DBUtil.currentSession();
		Transaction tx=session.beginTransaction();
		try{
	        session.delete(admenu);
	        // 提交事务
	        tx.commit();
	        return "success";        // 关闭session	 
	       }catch(Exception e){
	        return e.toString();
	       }finally{
	            session.close();
	        }
	}
	
}
