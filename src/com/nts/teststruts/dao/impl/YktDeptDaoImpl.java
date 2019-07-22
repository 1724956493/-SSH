package com.nts.teststruts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nts.teststruts.model.YktDept;
import com.nts.teststruts.model.YktUser;
import com.nts.teststruts.util.sqlDBUtil;

public class YktDeptDaoImpl {

	public List<YktDept> getall(){
		
		List<YktDept>  yktdepts = new ArrayList<YktDept>();
		
		Connection conn = sqlDBUtil.getConnection();
		String sql = "select bmbh,bmmc from department order by bmbh";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				YktDept dept = new YktDept();
				dept.setDeptcode(rs.getString("bmbh"));
				dept.setDeptname(rs.getString("bmmc"));
				yktdepts.add(dept);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}   	
	
		return yktdepts;
	}
}
