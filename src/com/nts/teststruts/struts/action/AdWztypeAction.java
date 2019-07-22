package com.nts.teststruts.struts.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdWzlistDaoImpl;
import com.nts.teststruts.dao.impl.AdWztypeDaoImpl;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdWzlist;
import com.nts.teststruts.model.AdWztype;
import com.nts.teststruts.service.impl.TreeNode;
import com.opensymphony.xwork2.ActionSupport;

public class AdWztypeAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {

	private  HttpServletResponse  response;
	private  HttpServletRequest request;  
	
	public void adWztypesoJson() throws Exception{
		AdWztype aswztype = new AdWztypeDaoImpl().getByUUID("1792ceb6b6094e8c8c780f2c88c90e35");

		Gson gson = new Gson();
		TreeNode treeNode = new TreeNode(aswztype,0);
		treeNode.setExpanded(true);
		String beanListToJson = gson.toJson(treeNode);	
		beanListToJson = "["+beanListToJson+"]";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);
	}
	
	public void adWzTypealltoJson() throws Exception{
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/json;charset=UTF-8");
		
		 List<AdWztype> adwztypes = new AdWztypeDaoImpl().getall();
			Gson gson = new Gson();
			String beanListToJson = gson.toJson(adwztypes);	
			System.out.println(beanListToJson);
			this.response.getWriter().write(beanListToJson);
	}
	
	
	public void saveadWztype() throws IOException{
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/json;charset=UTF-8");
		 String uuid =request.getParameter("uuid");
		 String wzcode =request.getParameter("wzcode");
		 String wzname =request.getParameter("wzname");
		 String wzparent =request.getParameter("wzparent");
		 String note =request.getParameter("note");
		 int type = Integer.parseInt(request.getParameter("type"));
		 
		 System.out.println(uuid+wzcode+wzname+wzparent+note+type);
		
		 AdWztype aswztype = new AdWztype();
		 aswztype.setUuid(uuid);
		 aswztype.setWzcode(wzcode);
		 aswztype.setWzname(wzname);
		 aswztype.setWzparent(wzparent);
		 aswztype.setNote(note);
		 aswztype.setType(0);
		 aswztype.setCreateTime(new Date());	
		 String s = "";
		 
		 if(aswztype.getUuid()==null || aswztype.getUuid()==""){
			s= new AdWztypeDaoImpl().insert(aswztype);
		 }else
		 {
			s= new AdWztypeDaoImpl().update(aswztype);
		 }
		 	 
		 if(s=="success"){
			 response.getWriter().write("{success:true}");
		 }else{
			 response.getWriter().write("{success:false,errors:{error:'"+s+"'}}");
		 }
	}
	
	public void adWzListsoJson() throws Exception{
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/json;charset=UTF-8");
		 String wztype =request.getParameter("wztype");
		 System.out.println(wztype);
		
		 List<AdWzlist> adwzlists = new AdWzlistDaoImpl().getByWztype(wztype);
			Gson gson = new Gson();
			String beanListToJson = gson.toJson(adwzlists);	
			System.out.println(beanListToJson);
			this.response.getWriter().write(beanListToJson);
	}
	
	public void adWzListalltoJson() throws Exception{
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/json;charset=UTF-8");
		
		 List<AdWzlist> adwzlists = new AdWzlistDaoImpl().getall();
			Gson gson = new Gson();
			String beanListToJson = gson.toJson(adwzlists);	
			System.out.println(beanListToJson);
			this.response.getWriter().write(beanListToJson);
	}
	
	
	
	public void saveadWzList() throws IOException{
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/json;charset=UTF-8");
		 String uuid =request.getParameter("uuid");
		 String wzlistcode =request.getParameter("wzlistcode");
		 String wzlistname =request.getParameter("wzlistname");
		 String wzlisttype =request.getParameter("wzlisttype");
		 String wztype =request.getParameter("wztype");
		 String note =request.getParameter("note");
		 int wzlistscore = Integer.parseInt(request.getParameter("wzlistscore"));
		 
		 System.out.println(uuid);
		
		 AdWzlist aswzlist = new AdWzlist();
		 aswzlist.setUuid(uuid);
		 aswzlist.setWzlistcode(wzlistcode);
		 aswzlist.setWzlistname(wzlistname);
		 aswzlist.setWzlisttype(wzlisttype);
		 aswzlist.setWzlistscore(wzlistscore);
		 aswzlist.setNote(note);
		 aswzlist.setWztype(wztype);
		 aswzlist.setCreateTime(new Date());	
		 String s = "";
		 
		 if(aswzlist.getUuid()==null || aswzlist.getUuid()==""){
			s= new AdWzlistDaoImpl().insert(aswzlist);
		 }else
		 {
			s= new AdWzlistDaoImpl().update(aswzlist);
		 }
		 	 
		 if(s=="success"){
			 response.getWriter().write("{success:true}");
		 }else{
			 response.getWriter().write("{success:false,errors:{error:'"+s+"'}}");
		 }
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
