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

public class YktUserDaoImpl {

	public YktUser getByUserCode( String yktusercode){
		Connection conn = sqlDBUtil.getConnection();
		String sql = "select * from employee where bh = ? and jn!='离职'";
		PreparedStatement ps;
		YktUser yktuser = new YktUser();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, yktusercode);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				yktuser.setYktusername(rs.getString("xm"));
				yktuser.setYktusercode(rs.getString("bh"));
				yktuser.setYktdept(rs.getString("bm"));
				yktuser.setYktusedept(rs.getString("lb"));
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}   
		
		System.out.println(yktuser.getYktusedept()+yktuser.getYktdept()+yktuser.getYktusercode()+yktuser.getYktusername());
		return yktuser;
	}
	
	
	public List<YktUser> getByDeptCode( String yktdeptcode,String date){
		List<YktUser>  yktusers = new ArrayList<YktUser>();
		Connection conn = sqlDBUtil.getConnection();
		String sql = "select * from employee where lb like ? and jn!='离职' and bh not in (select bh from yskq where rq = ?)";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, yktdeptcode+"%");
			ps.setString(2, date);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				YktUser yktuser = new YktUser();
				yktuser.setYktusername(rs.getString("xm"));
				yktuser.setYktusercode(rs.getString("bh"));
				yktuser.setYktdept(rs.getString("bm"));
				yktuser.setYktusedept(rs.getString("lb"));
				yktusers.add(yktuser);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}   	
		return yktusers;
	}
	
	
	public List<YktUser> getByDeptCodeAndName(String yktdeptcode,String name){
		List<YktUser>  yktusers = new ArrayList<YktUser>();
		Connection conn = sqlDBUtil.getConnection();
		String sql = "select *,(SELECT bmmc FROM department WHERE department.bmbh = employee.lb) bmmc from employee where xm like ? and jn!='离职' and bm like ?";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" +name+"%");
			ps.setString(2, "%" +yktdeptcode+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				YktUser yktuser = new YktUser();
				yktuser.setYktusername(rs.getString("xm"));
				yktuser.setYktusercode(rs.getString("bh"));
				yktuser.setYktdept(rs.getString("bm"));
				yktuser.setYktusedept(rs.getString("bmmc"));
				yktusers.add(yktuser);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}   	
		return yktusers;
	}
}
