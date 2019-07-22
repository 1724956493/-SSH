package com.nts.teststruts.struts.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.model.AdMenu;
import com.opensymphony.xwork2.ActionSupport;

public class ResourceAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,SessionAware{
	
	private  Map session ;
	private  HttpServletResponse  response;
	private  HttpServletRequest request ;
	private  AdMenuDaoImpl admenudao = new AdMenuDaoImpl();
	
	
	public void getResource() throws UnsupportedEncodingException{
		
		request.setCharacterEncoding("UTF-8");
		String mainview = request.getParameter("mainview");
		
		AdMenu admenu  =  admenudao.getByAction(mainview);

		
		List<AdMenu> admenus = admenudao.getResourceByCuserid(admenu.getUuidMenu(),session.get("cuserid").toString());
		
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
		try {
			this.response.getWriter().write(gson.toJson(admenus));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void setSession(Map session) {
		this.session=session;
	}

}
