package com.nts.teststruts.struts.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.BdCorpDaoImpl;
import com.nts.teststruts.dao.impl.BdDefdocDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.BdCorp;
import com.nts.teststruts.model.BdDefdoc;
import com.nts.teststruts.model.BdDeptdoc;
import com.nts.teststruts.service.impl.TreeNode;
import com.opensymphony.xwork2.ActionSupport;

public class CorpAction extends ActionSupport implements ServletResponseAware,ServletRequestAware {

	
	private BdCorp corp ;
	private BdDeptdoc deptdoc;
	private String corpresult;
	private  HttpServletResponse  response;
	private  HttpServletRequest request ;  
	private List<BdCorp> corps = new ArrayList<BdCorp>();
	
	
	public String get()
	{
		BdCorpDaoImpl  bdCorp = new BdCorpDaoImpl();
		try {
			 corps = bdCorp.GetAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void getdeptdocs() throws Exception{
		List<BdDeptdoc> deptdocs = new BdDeptdocDaoImpl().GetAll();
		if(deptdocs.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(deptdocs);		
	//	beanListToJson = "{\"root\":"+beanListToJson+"}";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}		
	}

	public void getTreeDeptdocs() throws Exception{
		BdCorp bdcorp = new BdCorpDaoImpl().GetByPk("1004");
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(new TreeNode(bdcorp));		
	//	beanListToJson = "{\"root\":"+beanListToJson+"}";
		beanListToJson = "["+beanListToJson+"]";
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);
		}		

	
	public void getjobs() throws Exception{
		List<BdDefdoc> jobs = new BdDefdocDaoImpl().GetByListPk("0001C1100000000001QI");
		if(jobs.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(jobs);		
	//	beanListToJson = "{\"root\":"+beanListToJson+"}";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}		
	}
	
	public String getCorpresult() {
		return corpresult;
	}

	public void setCorpresult(String corpresult) {
		this.corpresult = corpresult;
	}

	public BdCorp getCorp() {
		return corp;
	}

	public void setCorp(BdCorp corp) {
		this.corp = corp;
	}
	
	
	public List<BdCorp> getCorps() {
		return corps;
	}


	public void setCorps(List<BdCorp> corps) {
		this.corps = corps;
	}

	public BdDeptdoc getDeptdoc() {
		return deptdoc;
	}

	public void setDeptdoc(BdDeptdoc deptdoc) {
		this.deptdoc = deptdoc;
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
