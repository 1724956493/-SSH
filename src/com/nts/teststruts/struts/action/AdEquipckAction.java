package com.nts.teststruts.struts.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdEquipckCheckcontentDaoImpl;
import com.nts.teststruts.dao.impl.AdEquipckDaoImpl;
import com.nts.teststruts.dao.impl.AdEquipckdDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.dao.impl.PamEquipDaoImpl;
import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdEquipckCheckcontent;
import com.nts.teststruts.model.AdEquipckD;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.service.impl.PamEquipServ;
import com.opensymphony.xwork2.ActionSupport;

public class AdEquipckAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,SessionAware {

	private  AdEquipck adequipck;
	private  String  equipckjson;
	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	private Map session ;
	BdPsndocDaoImpl bdpsndocDao = new BdPsndocDaoImpl();
	BdDeptdocDaoImpl bddeptdao = new BdDeptdocDaoImpl();
	public void queryCheckContent() throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String className=this.request.getParameter("className");//"通用桥式起重机";
		String type=this.request.getParameter("type");//"dailycheck";
		List<AdEquipckCheckcontent> a=new AdEquipckCheckcontentDaoImpl().queryList(className,type);
		writeJson(a);
	}
	public void writeJson(Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//http://127.0.0.1:8080/ntstruts/json/pamequip_toJson?pamequip.pkEquip=1002C11000000005A3TG
	public void insert() throws Exception{
		InputStream inputstream =this.request.getInputStream();
		String result = IOUtils.toString(inputstream,"UTF-8");
		Gson gson = new Gson();
		AdEquipck adequipck =gson.fromJson(result, AdEquipck.class);
		adequipck.setCkdatetime(new Date());
		adequipck.setPkEquipckcode(Long.toString(System.currentTimeMillis()));
		System.out.println(gson.toJson(adequipck));
		String s = new AdEquipckDaoImpl().insert(adequipck);
		
		this.response.setHeader("Content-type","text/html;charset=UTF-8");
		OutputStream os = this.response.getOutputStream();
		os.write(s.getBytes("UTF-8"));		
	}
	
	//http://127.0.0.1:8080/ntstruts/json/pamequip_toJson?pamequip.pkEquip=1002C11000000005A3TG
	public void insertweb() throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String result =this.request.getParameter("submitData");	
		
		Gson gson = new Gson();
		
		try{
			BdPsndoc bdpsndoc = (BdPsndoc)session.get("bdpsndoc");
			if(bdpsndoc !=null){
			AdEquipckD adequipckd =gson.fromJson(result, AdEquipckD.class);
			adequipckd.setCkdatetime(new Date());
			adequipckd.setPkEquipckcode(Long.toString(System.currentTimeMillis()));
			adequipckd.setPkCkuser(bdpsndoc.getPkPsnbasdoc());
			adequipckd.setPkCkdept(bdpsndoc.getPkDeptdoc());
				System.out.println(gson.toJson(adequipckd));
				String s = new AdEquipckdDaoImpl().insert(adequipckd);
				
				this.response.getWriter().write(s);
			}else{
				this.response.getWriter().write("");
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public void tojson() 
	{
		System.out.println(adequipck.getPkEquip());

		adequipck  = new AdEquipckDaoImpl().getmaxid(adequipck.getPkEquip());
		if(adequipck==null){
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
		String adequipckjson = gson.toJson(adequipck);
		System.out.println(adequipckjson);
		response.setCharacterEncoding("UTF-8");
		try {
			this.response.getWriter().write(adequipckjson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void tojsonweb() throws UnsupportedEncodingException, SQLException 
	{
		request.setCharacterEncoding("UTF-8");
		String result =this.request.getParameter("pkEquip");	

	
		AdEquipckD adequipckd  = new AdEquipckdDaoImpl().getmaxid(result);
		if(adequipckd==null){
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
			adequipckd.setPkCkuser(bdpsndocDao.GetByPk(adequipckd.getPkCkuser()).getPsnname());
			adequipckd.setPkCkdept(bddeptdao.GetByPk(adequipckd.getPkCkdept()).getDeptname());
			String adequipckjson = gson.toJson(adequipckd);
			System.out.println(adequipckjson);
			response.setCharacterEncoding("UTF-8");
			try {
				this.response.getWriter().write(adequipckjson);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void reinsert() throws Exception{
		InputStream inputstream =this.request.getInputStream();
		String result = IOUtils.toString(inputstream);
		System.out.println(result);
		/*		Gson gson = new Gson();
		Equipck equipreck =gson.fromJson(result, Equipck.class);
		Equipck equipck = new AdEquipckDaoImpl().getmaxid(equipreck.getPkEquip());
		if(equipck != null){
		System.out.println(gson.toJson(equipck));
		equipck.setReckdatetime(new Date());
		equipck.setReckstatus(equipreck.getReckstatus());
		equipck.setPkReckuser(equipreck.getPkReckuser());
		equipck.setPkReckdept(equipreck.getPkReckdept());
		System.out.println(gson.toJson(equipck));
		new AdEquipckDaoImpl().insert(equipck);
		}
		else
		{
			response.setCharacterEncoding("GBK");
			this.response.getWriter().write("false");
		}
		Gson gson = new Gson();
		Equipck equipck =gson.fromJson(result, Equipck.class);
		equipck.setCkdatetime(new Date());
		new EquipckDaoImpl().insert(equipck);*/
	}
	
	
	public String getEquipckjson() {
		return equipckjson;
	}



	public void setEquipckjson(String equipckjson) {
		this.equipckjson = equipckjson;
	}





	public AdEquipck getAdequipck() {
		return adequipck;
	}

	public void setAdequipck(AdEquipck adequipck) {
		this.adequipck = adequipck;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	

	@Override
	public void setSession(Map session) {
		// TODO Auto-generated method stub
		this.session=session;
	}  
	
}
