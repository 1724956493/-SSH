package com.nts.teststruts.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nts.teststruts.model.AdUserrole;
import com.nts.teststruts.util.DBUtil;

public class AdUserroleDaoImpl {
	Session session;
	String[] roleOnle = { "89e5f8ce299d47e68e21ac8099b92dad", "bc81e16fb1f84e188bcfac8a93f9e08a",
			"224AF8C82F914CB6BD78BC3B7484C710", "F8E6F5A452EC4F56AEFC0804DCE7458B",
			"E14734E5F99F429D9823080CE421EBE9" };

	public Boolean insert(String role, List<String> getCode, Boolean isDelete) {
		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<String> getcuserid = new ArrayList<String>();
		try {
			String sql = "";
			Query query = null;

			for (int i = 0; i < getCode.size(); i++) {
				sql = "select cuserid from sm_user where  user_code like '" + getCode.get(i) + "'";
				query = session.createSQLQuery(sql);
				List m = query.list();
				getcuserid.add(m.get(0).toString());
			}

			for (int i = 0; i < getcuserid.size(); i++) {
				if (role != null) {
					// 是否删除以前权限
					if (isDelete) {
						String delete = "delete from ad_userrole  where  cuserid ='" + getcuserid.get(i) + "' ";
						query = session.createSQLQuery(delete);
						query.executeUpdate();
					}

					// 如果开通的角色是生产部一级保养的则要查询是否为一
					for (String s : roleOnle) {
						if (s.equals(role)) {
							String delete = "delete from ad_userrole  where  cuserid ='" + getcuserid.get(i)
									+ "' and  uuid_role in ('89e5f8ce299d47e68e21ac8099b92dad','bc81e16fb1f84e188bcfac8a93f9e08a','224AF8C82F914CB6BD78BC3B7484C710','F8E6F5A452EC4F56AEFC0804DCE7458B','E14734E5F99F429D9823080CE421EBE9')";
							query = session.createSQLQuery(delete);
							query.executeUpdate();
						}
					}
					sql = "insert into ad_userrole values" + " ((select get_uuid from dual),'" + getcuserid.get(i)
							+ "','" + role + "',(select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') " + "from dual))";
					query = session.createSQLQuery(sql);
					query.executeUpdate();
				}
			}
			System.out.println("本次开通权限人数："+getcuserid.size()+"人");
			tx.commit(); // 关闭session
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}
	}
}
