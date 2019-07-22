package com.nts.teststruts.struts.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdEquipckDaoImpl;
import com.nts.teststruts.dao.impl.CollectionReportDaoImpl;
import com.nts.teststruts.dao.impl.PamEquipDaoImpl;
import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.CollectReport;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.PamEquipServ;
import com.opensymphony.xwork2.ActionSupport;

public class CollectionAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {

	private  CollectReport collectreport;
	private  String  collectreportjson;
	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	
	//http://127.0.0.1:8080/ntstruts/json/collection_toJson?pamequip.pkEquip=1002C11000000005A3TG
	public void updateCollecttime() throws Exception{
		InputStream inputstream =this.request.getInputStream();
		String result = IOUtils.toString(inputstream,"UTF-8");
		System.out.println(result);
		Gson gson = new Gson();
		CollectReport collectreport2 =gson.fromJson(result, CollectReport.class);
		collectreport = new CollectionReportDaoImpl().getBypkCollect(collectreport2.getPkCollect());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");		
		collectreport.setCollecttime(format.format(new Date()));
		collectreport.setVdef1(collectreport2.getVdef1());
		collectreport.setVdef2(collectreport2.getVdef2());
	//	System.out.println(gson.toJson(collectreport));
		String s = new CollectionReportDaoImpl().update(collectreport);		
		this.response.setHeader("Content-type","text/html;charset=UTF-8");
		OutputStream os = this.response.getOutputStream();
		os.write(s.getBytes("UTF-8"));		
	}
	
	
	public void updateReceivetime() throws Exception{
		InputStream inputstream =this.request.getInputStream();
		String result = IOUtils.toString(inputstream,"UTF-8");
		System.out.println(result);
		Gson gson = new Gson();
		CollectReport collectreport2 =gson.fromJson(result, CollectReport.class);
		collectreport = new CollectionReportDaoImpl().getBypkCollect(collectreport2.getPkCollect());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");		
		collectreport.setReceivetime(format.format(new Date()));
		collectreport.setVdef3(collectreport2.getVdef3());
		collectreport.setVdef4(collectreport2.getVdef4());
	//	System.out.println(gson.toJson(collectreport));
		String s = new CollectionReportDaoImpl().update(collectreport);		
		this.response.setHeader("Content-type","text/html;charset=UTF-8");
		OutputStream os = this.response.getOutputStream();
		os.write(s.getBytes("UTF-8"));		
	}
	
	
	//http://127.0.0.1:8080/ntstruts/json/collection_toJson?collectreport.onePkCode=ALC00003
	public void pktoJson() 
	{
		System.out.println(collectreport.getOnePkCode());

		collectreport  = new CollectionReportDaoImpl().getByPK(collectreport.getOnePkCode());
		if(collectreport==null){
			response.setCharacterEncoding("UTF-8");
			try {
				this.response.getWriter().write("false");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
		Gson gson = new Gson();
		String collectreportjson = gson.toJson(collectreport);
		System.out.println(collectreportjson);
		response.setCharacterEncoding("UTF-8");
		try {
			this.response.getWriter().write(collectreportjson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	//http://127.0.0.1:8080/ntstruts/json/collection_toJson?collectreport.mark=ALC00003
	public void codetoJson() 
	{
		System.out.println(collectreport.getMark());

		collectreport  = new CollectionReportDaoImpl().getByCode(collectreport.getMark());
		if(collectreport==null){
			response.setCharacterEncoding("UTF-8");
			try {
				this.response.getWriter().write("false");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
		Gson gson = new Gson();
		String collectreportjson = gson.toJson(collectreport);
		System.out.println(collectreportjson);
		response.setCharacterEncoding("UTF-8");
		try {
			this.response.getWriter().write(collectreportjson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	public CollectReport getCollectreport() {
		return collectreport;
	}

	public void setCollectreport(CollectReport collectreport) {
		this.collectreport = collectreport;
	}

	public String getCollectreportjson() {
		return collectreportjson;
	}

	public void setCollectreportjson(String collectreportjson) {
		this.collectreportjson = collectreportjson;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;		
	}
	
	
}
