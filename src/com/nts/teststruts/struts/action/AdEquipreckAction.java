package com.nts.teststruts.struts.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdEquipckDaoImpl;
import com.nts.teststruts.dao.impl.AdEquipreckDaoImpl;
import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdEquipreck;
import com.opensymphony.xwork2.ActionSupport;

public class AdEquipreckAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {

	private  AdEquipreck adequipreck;
	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	
	
	
	public void insert() throws Exception{
		InputStream inputstream =this.request.getInputStream();
		String result = IOUtils.toString(inputstream,"UTF-8");
		System.out.println(result);
		Gson gson = new Gson();
		AdEquipreck adequipreck =gson.fromJson(result, AdEquipreck.class);
		adequipreck.setReckdatetime(new Date());
		System.out.println(gson.toJson(adequipreck));
		
		
		String s = new AdEquipreckDaoImpl().insert(adequipreck);
		
		this.response.setHeader("Content-type","text/html;charset=UTF-8");
		OutputStream os = this.response.getOutputStream();
		os.write(s.getBytes("UTF-8"));	
	}	
	
	public AdEquipreck getAdequipreck() {
		return adequipreck;
	}

	public void setAdequipreck(AdEquipreck adequipreck) {
		this.adequipreck = adequipreck;
	}


	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}

	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;		
	}
	
}
