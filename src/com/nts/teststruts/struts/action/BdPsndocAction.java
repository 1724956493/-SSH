package com.nts.teststruts.struts.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.opensymphony.xwork2.ActionSupport;

public class BdPsndocAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {

	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	
	
	public void searchEmp() throws IOException{
		 request.setCharacterEncoding("UTF-8");
		 String psnname =request.getParameter("psnname");
		 String deptparam =request.getParameter("deptparam");
		 System.out.println(psnname);
		 List objs = new BdPsndocDaoImpl().getAllByPsnname("%"+psnname+"%",deptparam) ;
		 Gson gson = new Gson();
		 String beanListToJson = gson.toJson(objs);
		 System.out.println(beanListToJson);
		 response.setContentType("text/json;charset=UTF-8");
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
