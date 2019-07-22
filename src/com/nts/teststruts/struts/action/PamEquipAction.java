package com.nts.teststruts.struts.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nts.teststruts.dao.impl.AdEquipckDaoImpl;
import com.nts.teststruts.dao.impl.PamEquipDaoImpl;
import com.nts.teststruts.dao.impl.PamLocationDaoImpl;
import com.nts.teststruts.dao.impl.SmUserDaoImpl;
import com.nts.teststruts.model.PamEquip;
import com.nts.teststruts.model.PamLocation;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.service.impl.PamEquipServ;
import com.opensymphony.xwork2.ActionSupport;

public class PamEquipAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {


	private PamEquip pamequip2;
	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	
	//http://127.0.0.1:8080/ntstruts/json/pamequip_toJson?pamequip.pkEquip=1002C11000000005A3TG
	public void toJson(){
		System.out.println(pamequip2.getPkEquip());
		PamEquipDaoImpl pamequipDao = new PamEquipDaoImpl();
		try {
			pamequip2  = pamequipDao.GetByPk(pamequip2.getPkEquip());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pamequip2==null){
			response.setCharacterEncoding("UTF-8");
			try {
				this.response.getWriter().write("false");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
		PamEquipServ pamequipsev = null;
		try {
			pamequipsev = new PamEquipServ(pamequip2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String pamequipsevjson = gson.toJson(pamequipsev);
		System.out.println(pamequipsevjson);
		response.setCharacterEncoding("UTF-8");
		try {
			this.response.getWriter().write(pamequipsevjson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
	
	//http://127.0.0.1:8080/ntstruts/json/pamequip_toJson?pamequip.pkEquip=1002C11000000005A3TG
	public void toJsonweb() throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		String pkequip = request.getParameter("pkEquip");
		
		PamEquipDaoImpl pamequipDao = new PamEquipDaoImpl();
		try {
			pamequip2  = pamequipDao.GetByPk(pkequip);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pamequip2==null){
			response.setCharacterEncoding("UTF-8");
			try {
				this.response.getWriter().write("false");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
		PamEquipServ pamequipsev = null;
		try {
			pamequipsev = new PamEquipServ(pamequip2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String pamequipsevjson = gson.toJson(pamequipsev);
		System.out.println(pamequipsevjson);
		response.setCharacterEncoding("UTF-8");
		try {
			this.response.getWriter().write(pamequipsevjson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void alltoJson() throws SQLException, IOException{
		response.setCharacterEncoding("UTF-8");
		PamEquipDaoImpl pamequipDao = new PamEquipDaoImpl();
		List<PamEquip> equipments = pamequipDao.GetAll();
		Gson gson = new Gson();
		String equipmentsjson = gson.toJson(equipments);
		this.response.getWriter().write(equipmentsjson);
	//	System.out.println(equipmentsjson);		
	}
	
	public void pagetoJson() throws SQLException, IOException{
		request.setCharacterEncoding("UTF-8");
		int pageNo = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		String deptpk = request.getParameter("deptpk");
		int total = 0;
		List<PamEquip> equipments = new ArrayList<PamEquip>(); 
		
		response.setCharacterEncoding("UTF-8");
		
		PamEquipDaoImpl pamequipDao = new PamEquipDaoImpl();
		if(deptpk==null || deptpk ==""){
			total = pamequipDao.getTotal();
			equipments = pamequipDao.GetByPage(pageNo, pageSize);

		}else{
			total = pamequipDao.getDeptTotal(deptpk);
			equipments = pamequipDao.GetByDeptPage(pageNo, pageSize, deptpk);
		}
		Gson gson = new Gson();
		String equipmentsjson = gson.toJson(equipments);
		String jsonString = "{\"total\":"+ total+",\"data\":" +equipmentsjson +"}";
		this.response.getWriter().write(jsonString);
	//	System.out.println(equipmentsjson);		
	}
	
	public void localtoJson() throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String localpk = request.getParameter("data");
		System.out.println(localpk);
		if(localpk!=null || localpk!=""){
			PamLocation location = new PamLocationDaoImpl().GetByPk(localpk);
			System.out.println(location.getLocationName());
			this.response.getWriter().write("{success:true,locationName:'"+location.getLocationName()+"'}");
		}else{
			this.response.getWriter().write("{success:false}");
		}
		}
	

	public void reportJson() throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String deptpk = request.getParameter("deptpk");
		String startdate = request.getParameter("startdate").substring(0, 10);
		String enddate = request.getParameter("enddate").substring(0, 10);
		System.out.println(deptpk +startdate +enddate);
		List<MapInfo> maps2 = new ArrayList<MapInfo>();
		if(deptpk!=null || deptpk!=""){
			List maps = new AdEquipckDaoImpl().groupByPk(deptpk,startdate,enddate);			
			for (int i = 0; i < maps.size(); i++) {
	             Object[] object = (Object[]) maps.get(i);
	             if(object[2]==null){ object[2] =0;}
	           //  System.out.println(object[0].toString()+","+object[1].toString()+","+object[2].toString());	
	             MapInfo mapinfo = new  MapInfo();
	             mapinfo.setS1(object[0].toString());
	             int s2 = ((Number)object[1]).intValue();
	             int s3= ((Number)object[2]).intValue();
	             mapinfo.setS2(s2 - s3);
	             mapinfo.setS3(s3);
	             maps2.add(mapinfo);
		}
		}
		Gson gson = new Gson();
		String beanList = gson.toJson(maps2);
		System.out.println(beanList);
	//	this.response.getWriter().write("{\"data\":"+beanList+"}");
		this.response.getWriter().write(beanList);
	}

	public PamEquip getPamequip() {
		return pamequip2;
	}

	public void setPamequip(PamEquip pamequip) {
		this.pamequip2 = pamequip;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
		
	}
	
}
