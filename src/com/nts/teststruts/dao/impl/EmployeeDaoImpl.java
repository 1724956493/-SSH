package com.nts.teststruts.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.Employee;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.util.DBUtil;

public class EmployeeDaoImpl {

	 Session session;
		
		public List<Employee> getall() throws SQLException
		{
			String hql ="from Employee";
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			List<Employee> employees = query.list();	
			DBUtil.closeSession();
			return employees;
		}
		
		public Employee getByUuid(String uuid) throws SQLException
		{
			String hql ="from Employee where employeeuuid = :employeeuuid";
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			query.setParameter("employeeuuid",uuid);
			Employee employee = (Employee)query.uniqueResult();	
			DBUtil.closeSession();
			return employee;
		}
		
		public List<Employee> getById(String id) throws SQLException
		{
			String hql ="from Employee where id = :id";
			session =DBUtil.currentSession();
			Query query =session.createQuery(hql);
			query.setParameter("id",id);
			List<Employee> employees = query.list();	
			DBUtil.closeSession();
			return employees;
		}
		
		public String insert(Employee employee)
		{
			String s =null;
			session =DBUtil.currentSession();
			Transaction tx=session.beginTransaction();
			
			
			try{
		        session.save(employee);
		        // 提交事务
		        tx.commit();       // 关闭session	 
		         
		        return s = "success";
		       }catch(Exception e){
		        return s;
		       }finally{
		            session.close();
		        }
		}
		
		public String update(Employee employee)
		{
			session =DBUtil.currentSession();
			Transaction tx=session.beginTransaction();
			try{
		        session.update(employee);
		        // 提交事务
		        tx.commit();
		        return "success";        // 关闭session	 
		       }catch(Exception e){
		        return "false";
		       }finally{
		            session.close();
		        }
		}
		
		public String delete(Employee employee)
		{
			session =DBUtil.currentSession();
			Transaction tx=session.beginTransaction();
			try{
		        session.delete(employee);
		        // 提交事务
		        tx.commit();
		        return "success";        // 关闭session	 
		       }catch(Exception e){
		        return "false";
		       }finally{
		            session.close();
		        }
		}
}
