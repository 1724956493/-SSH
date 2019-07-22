package com.nts.teststruts.struts.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.YktDeptDaoImpl;
import com.nts.teststruts.dao.impl.YktUserDaoImpl;
import com.nts.teststruts.dao.impl.YktXfReportDaoImpl;
import com.nts.teststruts.model.YktDept;
import com.nts.teststruts.model.YktUser;
import com.nts.teststruts.service.impl.YktUserXfList;
import com.nts.teststruts.service.impl.YktXfReport;
import com.nts.teststruts.util.ComUtil;
import com.opensymphony.xwork2.ActionSupport;

public class YktServerAction extends ActionSupport implements ServletResponseAware,ServletRequestAware{

	private YktUser  yktuser;
	private YktDept yktdept;
	private String kqdate;
	private List<YktUser> yktusers;
	private List<YktDept> yktdepts;
	private  HttpServletResponse  response;
	private  HttpServletRequest request ;
	
	
	//http://localhost:8080/ntstruts/json/yktserver_yktusertoJson?yktuser.yktusercode=103010015
	public void yktusertoJson() throws IOException {
		yktuser =new YktUserDaoImpl().getByUserCode(yktuser.getYktusercode());
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(yktuser);
	//	System.out.println(yktuser.getDeptname()+beanListToJson);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);
	}
	
//	http://localhost:8080/ntstruts/json/yktserver_yktuserstoJson?yktdept.deptcode=103010015&kqdate=2015-11-04
	public void yktuserstoJson() throws IOException {
		String subdeptcode =yktdept.getDeptcode().substring(0,3);
		yktusers = new YktUserDaoImpl().getByDeptCode(subdeptcode, kqdate);
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(yktusers);
		//	System.out.println(yktuser.getDeptname()+beanListToJson);
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(beanListToJson);
	}
	
	
//	http://localhost:8080/ntstruts/json/yktserver_yktuserListtoJson?yktdept.deptcode=103010015&yktuser.yktusername=杨贵	
	public void yktuserListtoJson() throws IOException {
		String deptcode =new String(yktdept.getDeptcode().getBytes("ISO8859-1"), "UTF-8");
		String name = new String(yktuser.getYktusername().getBytes("ISO8859-1"), "UTF-8"); 
		System.out.println(deptcode+ "  " +name);
		yktusers = new YktUserDaoImpl().getByDeptCodeAndName(deptcode.trim(), name.trim());
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(yktusers);
		//	System.out.println(yktuser.getDeptname()+beanListToJson);
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(beanListToJson);
	}

	
//	http://localhost:8080/ntstruts/json/yktserver_yktdeptstoJson
	public void yktdeptstoJson() throws IOException {
		yktdepts = new YktDeptDaoImpl().getall();
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(yktdepts);
		//	System.out.println(yktuser.getDeptname()+beanListToJson);
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(beanListToJson);
	}
	
	public void xfReportdown() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String data = request.getParameter("data"); 
		System.out.println(data +1);
		String fileName2 ="d:/"+data+"消费明细表.xls";
		
		   File excelFile = new File(fileName2);  
		   if (!excelFile.exists())
		   {		
				String filename = "d:/1.xls";
				List<YktXfReport> a1 = new YktXfReportDaoImpl().finalxfroport(data);
				new  ComUtil().excelSave(a1,filename);
				List<YktUserXfList> a = new YktXfReportDaoImpl().getxfreportList(data);
				new  ComUtil().excelSave2(a,filename,data);
			}
		   
				this.response.setContentType("text/json;charset=UTF-8");
				this.response.getWriter().write("{success:true}");
	}
	
	public void xfReportdown2() throws Exception {
		request.setCharacterEncoding("UTF-8");
		String data = request.getParameter("data"); 
		System.out.println(data+2);		

		String filePath = "d:/";
		String fileName2 =data+"消费明细表.xls";
        new ComUtil().filedown(filePath, fileName2, response);     
	}
	
	
	
	public void xfReportToExcle() throws Exception  {
		request.setCharacterEncoding("UTF-8");
		String excelName = "";  
		String path="";  
		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + excelName+".xls");
		
		 PrintWriter out = response.getWriter();
		 BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path)); 
		 ServletOutputStream sos=response.getOutputStream();  
		 BufferedOutputStream bos = new BufferedOutputStream(sos);   
		 byte[] buffer = new byte[1024];   
		 int len = -1;   
		 while ((len = bis.read(buffer)) != -1) {   
             bos.write(buffer, 0, len);   
         }   
         bos.close();   
         sos.close();   
         bis.close(); 
	}
	
	public YktUser getYktuser() {
		return yktuser;
	}

	public void setYktuser(YktUser yktuser) {
		this.yktuser = yktuser;
	}

	
	
	public YktDept getYktdept() {
		return yktdept;
	}

	public void setYktdept(YktDept yktdept) {
		this.yktdept = yktdept;
	}

	public String getKqdate() {
		return kqdate;
	}

	public void setKqdate(String kqdate) {
		this.kqdate = kqdate;
	}

	public List<YktUser> getYktusers() {
		return yktusers;
	}

	public void setYktusers(List<YktUser> yktusers) {
		this.yktusers = yktusers;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;		
	}
	
}
