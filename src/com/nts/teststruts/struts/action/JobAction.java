package com.nts.teststruts.struts.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.BdJobbasfilDaoImpl;
import com.nts.teststruts.model.BdJobbasfil;
import com.opensymphony.xwork2.ActionSupport;

public class JobAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {

	
	private BdJobbasfil bdjobbasfil ;
	private  HttpServletResponse  response;
	private  HttpServletRequest request ;  
	
	
	public void getjobbasfils() throws Exception{
		List<BdJobbasfil> bdjobbasfils = new BdJobbasfilDaoImpl().GetByType("0001C110000000000DF7");
		if(bdjobbasfils.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(bdjobbasfils);		
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}		
	}


	public BdJobbasfil getBdjobbasfil() {
		return bdjobbasfil;
	}

	public void setBdjobbasfil(BdJobbasfil bdjobbasfil) {
		this.bdjobbasfil = bdjobbasfil;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
		
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}


}
