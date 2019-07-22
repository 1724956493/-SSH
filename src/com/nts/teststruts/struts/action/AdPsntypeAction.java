package com.nts.teststruts.struts.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdPsntypelistDaoImpl;
import com.nts.teststruts.dao.impl.AdWzlistDaoImpl;
import com.nts.teststruts.model.AdPsntypelist;
import com.nts.teststruts.model.AdWzlist;
import com.opensymphony.xwork2.ActionSupport;

public class AdPsntypeAction extends ActionSupport implements ServletResponseAware,ServletRequestAware{

	private  HttpServletResponse  response;
	private  HttpServletRequest request; 
	
	public void savepsntype() throws Exception{
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/json;charset=UTF-8");
		 String uuid =request.getParameter("uuid");
		 String psndocpk =request.getParameter("psndocpk");
		 String psnbasdocpk =request.getParameter("psnbasdocpk");
		 String psnname =request.getParameter("psnname");
		 String dept =request.getParameter("dept");
		 String parentdept =request.getParameter("parentdept");
		 String area =request.getParameter("area");
		 String project =request.getParameter("project");
		 String psntype =request.getParameter("psntype");
		 String note =request.getParameter("note");
		 
//		 System.out.println(uuid);
		
		 AdPsntypelist aspsntypelist = new AdPsntypelist();
		 aspsntypelist.setUuid(uuid);
		 aspsntypelist.setArea(area);
		 aspsntypelist.setDeptpk(dept);
		 aspsntypelist.setPsnname(psnname);
		 aspsntypelist.setParentdeptpk(parentdept);
		 aspsntypelist.setPsndocpk(psndocpk);
		 aspsntypelist.setPsnbasdocpk(psnbasdocpk);
		 aspsntypelist.setNote(note);
		 aspsntypelist.setPsntype(psntype);
		 aspsntypelist.setProject(project);
		 aspsntypelist.setCreateTime(new Date());	
		 String s = "";
		 
		 if(aspsntypelist.getUuid()==null || aspsntypelist.getUuid()==""){
			s= new AdPsntypelistDaoImpl().insert(aspsntypelist);
		 }else
		 {
			s= new AdPsntypelistDaoImpl().update(aspsntypelist);
		 }
		 	 
		 if(s=="success"){
			 response.getWriter().write("{success:true}");
		 }else{
			 response.getWriter().write("{success:false,errors:{error:'"+s+"'}}");
		 }		
	}
	
	public void getpsntypes() throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");		
		List<AdPsntypelist> adpsntypelist = new AdPsntypelistDaoImpl().getall();	
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(adpsntypelist);
		this.response.getWriter().write(beanListToJson);		
	}
	
	public void ad_getpsntypes() throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");		
		List<AdPsntypelist> adpsntypelist = new AdPsntypelistDaoImpl().getByPsntype("主管领导");	
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(adpsntypelist);
		this.response.getWriter().write(beanListToJson);		
	}
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
	
}
