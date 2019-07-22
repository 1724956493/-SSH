package com.nts.teststruts.struts.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdCarinfoDaoImpl;
import com.nts.teststruts.model.AdCarinfo;
import com.nts.teststruts.model.BdJobbasfil;
import com.nts.teststruts.util.ComUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdCarinfoAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {
	
	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	
	public void getbycarid() throws Exception{
		 request.setCharacterEncoding("UTF-8");
		 String carid = request.getParameter("carid");
		List<AdCarinfo> carinfos =new  AdCarinfoDaoImpl().getbyCarid(carid.toUpperCase());
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
	//	this.response.getWriter().write("123123123");
		this.response.getWriter().write(gson.toJson(carinfos));
	}
	
	public void alltojson() throws Exception{
		request.setCharacterEncoding("UTF-8");
		List<AdCarinfo> carinfos =new  AdCarinfoDaoImpl().getall();
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(gson.toJson(carinfos));
	}
	
	public void delete() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String data =  request.getParameter("data");
		if(data!=""||data!=null){		
			AdCarinfo carinfo = new AdCarinfoDaoImpl().getbyuuid(data);
			String s = "false";
			s  =new  AdCarinfoDaoImpl().delete(carinfo);
			response.setContentType("text/json;charset=UTF-8");
			this.response.getWriter().write("{success:true}");
		}
	}
	
	public void insert() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String data =  request.getParameter("data");
		Gson gson = new Gson();
		AdCarinfo carinfo = gson.fromJson(data, AdCarinfo.class);
		System.out.println(carinfo.toString());
		carinfo.setCreateTime(new Date());
		String s = "false";
		String error = "";
		AdCarinfoDaoImpl adcarinfodaoimpl = new AdCarinfoDaoImpl();
		if(ComUtil.isEmptyString(carinfo.getUuid())){
			List<AdCarinfo> carinfos = adcarinfodaoimpl.getbyCarid(carinfo.getCarid());
			if(carinfos.size()==0){
				s  =adcarinfodaoimpl.insert(carinfo);
			}else{
				error = "此车牌号已存在，请查询！";
			}
		}else{
			s = adcarinfodaoimpl.update(carinfo);
		}
		response.setContentType("text/json;charset=UTF-8");
		if(s=="success"){
			 response.getWriter().write("{success:true}");
		 }else{
			 response.getWriter().write("{success:false,errors:{error:'"+error+"'}}");
		 }
	}
		
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}

}
