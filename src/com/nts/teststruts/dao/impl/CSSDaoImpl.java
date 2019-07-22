package com.nts.teststruts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.util.CssSqlDBUtil;

public class CSSDaoImpl {

	public List<String> getmanager(){
		List<String> names = new ArrayList<String>();
		Connection conn = CssSqlDBUtil.getConnection();
		String sql = "select e.workpos,f.name from BasicConfig_ManuCharge e left join BasicConfig_Staff  f on e.staffid = f.id";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				names.add(rs.getString("name"));			
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}  
		
		return names;		
	}
	
	public List<MapInfo> getmanager2(){
		List<MapInfo> map = new ArrayList<MapInfo>();
		Connection conn = CssSqlDBUtil.getConnection();
		String sql = "select f.name from BasicConfig_ManuCharge e left join BasicConfig_Staff  f on e.staffid = f.id";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){	
				MapInfo mapinfo = new MapInfo();
				mapinfo.setS1(rs.getString("name"));
				map.add(mapinfo);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}  
		
		return map;		
	}
}
