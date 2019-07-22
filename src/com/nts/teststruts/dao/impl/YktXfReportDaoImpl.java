package com.nts.teststruts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nts.teststruts.model.YktDept;
import com.nts.teststruts.model.YktUser;
import com.nts.teststruts.service.impl.YktUserXfList;
import com.nts.teststruts.service.impl.YktXfReport;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.sqlDBUtil;

public class YktXfReportDaoImpl {

	public List<YktXfReport> getxfreport(String startTime ,String endTime , String stname){
		
		List<YktXfReport>  xfreports = new ArrayList<YktXfReport>();
		
		Connection conn = sqlDBUtil.getConnection();
		String sql = "SELECT bmbh, COUNT(*) t FROM xf_xfjl WHERE (xfdate BETWEEN ? AND ?) and bmbh not like '500%' AND (xfjbh IN (SELECT xfjbh FROM xf_xfj WHERE stname like ? )) GROUP BY bmbh";
		
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, startTime);
			ps.setString(2, endTime);
			ps.setString(3, stname);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				YktXfReport xfreport = new YktXfReport();
				xfreport.setDeptcode(rs.getString("bmbh"));
				xfreport.setTime1Total(rs.getInt("t"));
				xfreports.add(xfreport);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}   	
	
		return xfreports;
	}
	
	public List<YktUserXfList> getxfreportList(String date){
		List<YktUserXfList>  xfreports = new ArrayList<YktUserXfList>();
		
		Connection conn = sqlDBUtil.getConnection();
		String sql ="SELECT l.bh, l.xm, l.bmbh, l.bm, l.xfdate, l.stname, j.ip, j.bz ,l.xfjbh  "+
					  " FROM xf_xfjl l LEFT OUTER JOIN xf_xfj j ON l.xfjbh = j.xfjbh "
					 +" WHERE (l.xfdate BETWEEN ? AND ? ) ORDER BY l.xfdate ";
		PreparedStatement ps;
		// '2016-8-17 10:30:00' AND '2016-8-17 11:15:00')

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, date+" 10:30:00");
			ps.setString(2, date+" 11:15:00");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				YktUserXfList xfreportlist = new YktUserXfList();
				xfreportlist.setYktusercode(rs.getString("bh"));
				xfreportlist.setYktusername(rs.getString("xm"));
				xfreportlist.setYktdeptcode(rs.getString("bmbh"));
				xfreportlist.setYktdeptname(rs.getString("bm"));
				xfreportlist.setYktstname(rs.getString("stname"));
				xfreportlist.setYktxftime(rs.getString("xfdate"));
				xfreportlist.setYktxfjbh(rs.getString("xfjbh"));
				xfreportlist.setYktxfjbz(rs.getString("bz"));
				xfreportlist.setYktxfjip(rs.getString("ip"));
				xfreports.add(xfreportlist);
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}   	
		
		return xfreports;
	}
	
	public List<YktXfReport> finalxfroport(String date){
		
		List<YktXfReport> xfroport1 =getxfreport(date+" 10:30:00", date+" 11:00:00", "NCS%");
		List<YktXfReport> xfroport2 =getxfreport(date+" 11:00:00", date+" 11:05:00", "NCS%");
		List<YktXfReport> xfroport3 =getxfreport(date+" 11:05:00", date+" 11:15:00", "NCS%");
		List<YktXfReport> xfroport4 =getxfreport(date+" 10:30:00", date+" 11:00:00", "NTS%");
		List<YktXfReport> xfroport5 =getxfreport(date+" 11:00:00", date+" 11:05:00", "NTS%");
		List<YktXfReport> xfroport6 =getxfreport(date+" 11:05:00", date+" 11:15:00", "NTS%");	
		
		List<YktDept> yktdepts = new YktDeptDaoImpl().getall();
		Map<String, YktXfReport> map0 = new HashMap<String, YktXfReport>();
		for(YktDept yktdept : yktdepts){
			YktXfReport yktxfreport = new YktXfReport(yktdept);
			map0.put(yktxfreport.getDeptcode(), yktxfreport);
		}
		for(YktXfReport xfroport : xfroport1 ){
			YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
			if(xfroportx!=null){
			xfroportx.setTime1Total(xfroport.getTime1Total());
			xfroportx.setStname("NCS");}
		}
		for(YktXfReport xfroport : xfroport2 ){
			YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
			if(xfroportx!=null){
			xfroportx.setTime2Total(xfroport.getTime1Total());
			xfroportx.setStname("NCS");}
		}
		for(YktXfReport xfroport : xfroport3 ){
			YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
			if(xfroportx!=null){
			xfroportx.setTime3Total(xfroport.getTime1Total());
			xfroportx.setStname("NCS");}
		}
		for(YktXfReport xfroport : xfroport4 ){
			YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
			if(xfroportx!=null){
			xfroportx.setTime4Total(xfroport.getTime1Total());
			xfroportx.setStname("NTS");}
		}
		for(YktXfReport xfroport : xfroport5 ){
			YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
			if(xfroportx!=null){
			xfroportx.setTime5Total(xfroport.getTime1Total());
			xfroportx.setStname("NTS");}
		}
		for(YktXfReport xfroport : xfroport6 ){
			YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
			if(xfroportx!=null){
			xfroportx.setTime6Total(xfroport.getTime1Total());
			xfroportx.setStname("NTS");}
		}
		
		Iterator it = map0.keySet().iterator();
		List<YktXfReport> xfroport0 = new ArrayList<YktXfReport>();
		 while (it.hasNext()) {  
			 String key = it.next().toString(); 
			 xfroport0.add(map0.get(key));
		 }	
//			new  ComUtil().excelSave(xfroport0,"d:/1.xls");
		 Collections.sort(xfroport0);
		 return  xfroport0;	
	}
}
