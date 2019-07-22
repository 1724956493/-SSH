package com.nts.teststruts.struts.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdPsntypelistDaoImpl;
import com.nts.teststruts.dao.impl.AdRoleDaoImpl;
import com.nts.teststruts.dao.impl.AdRoleMenuDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdJobbasfilDaoImpl;
import com.nts.teststruts.dao.impl.BdPsnbasdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.dao.impl.CSSDaoImpl;
import com.nts.teststruts.dao.impl.EmployeeDaoImpl;
import com.nts.teststruts.dao.impl.IcSapplyHDaoImpl;
import com.nts.teststruts.dao.impl.SmUserDaoImpl;
import com.nts.teststruts.dao.impl.SmUserandclerkDaoImpl;
import com.nts.teststruts.model.*;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.service.impl.SmUserServ;
import com.nts.teststruts.service.impl.TreeNode;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.CssSqlDBUtil;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.Encode;
import com.nts.teststruts.util.IDCard;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,SessionAware{
	


	private SmUser smuser;	
	private Employee employee;
	private String password;
	private SmUserServ smuserserv;
	private  HttpServletResponse  response;
	private  HttpServletRequest request ;  
	private Map session ;
	private AdRoleMenuDaoImpl adrolemenuDao = new AdRoleMenuDaoImpl();
	private  AdRoleDaoImpl adroledao = new AdRoleDaoImpl();
	



	public void login() throws SQLException, IOException {		
		System.out.println(smuser.getUserCode());
		List<SmUser> users = new SmUserDaoImpl().GetAll(smuser.getUserCode());
		Encode encode = new Encode();
        response.setContentType("text/json;charset=UTF-8");
		if(users.size()==1)
		{
			if(users.get(0).getUserPassword().equals(encode.encode(password)))
				{
					SmUser smuser = users.get(0);
					BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByPk(new SmUserandclerkDaoImpl().GetByPk(smuser.getCuserid()).getPkPsndoc());
					String pk_deptdoc = bdpsndoc.getPkDeptdoc() ;
					String deptname = new BdDeptdocDaoImpl().GetByPk(pk_deptdoc).getDeptname();
				//	System.out.println("密码正确"+users.get(0).getUserPassword());
					if(session.get("bdpsndoc")==null){
					session.put("bdpsndoc",bdpsndoc);
					session.put("cuserid",users.get(0).getCuserid());
					response.getWriter().write("{success:true,user:{username:'"+bdpsndoc.getPsnname()+"',userpk:'"+bdpsndoc.getPkPsnbasdoc()+"',deptname:'"+deptname+"'}}");
					}else{
						session.clear();
						session.put("bdpsndoc",bdpsndoc);
						session.put("cuserid",users.get(0).getCuserid());
						response.getWriter().write("{success:true,user:{username:'"+bdpsndoc.getPsnname()+"',userpk:'"+bdpsndoc.getPkPsnbasdoc()+"',deptname:'"+deptname+"'}}");
					}
				}
			else{			
				response.getWriter().write("{success:false,error:'用户名或密码错误'}");
				}
		}
		else
		{
			response.getWriter().write("{success:false,error:'用户名或密码错误'}");
		}
		
	}
	
	public void loginout(){
		if(session.get("bdpsndoc")!=null){
			session.clear();
		}
	}
	
	public void toJson() throws Exception{
		System.out.println(smuserserv.getUsercode());
		SmUserDaoImpl userDao = new SmUserDaoImpl();
		List<SmUser> users = userDao.GetAll(smuserserv.getUsercode());
		if(users.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		smuserserv = new SmUserServ(users.get(0));
		String beanListToJson = gson.toJson(smuserserv);
		System.out.println(smuserserv.getDeptname()+beanListToJson);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}
	
	
	public void usertoJson() throws Exception{
		List<SmUser> sumsers = new SmUserDaoImpl().getall();
		if(sumsers.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(sumsers);		
		//beanListToJson = "{root:"+beanListToJson+"}";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}
	
	
	
	public void roletoJson() throws Exception{
		List<AdRole> roles = new AdRoleDaoImpl().getall();
		if(roles.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(roles);		
	//	beanListToJson = "{root:"+beanListToJson+"}";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}
	
	public void Jsontorole() throws Exception{
		BufferedReader in = this.request.getReader();
		StringBuffer jsonStr= new StringBuffer();
		String str="";
		while((str=in.readLine())!=null){
			jsonStr.append(str);
		}
	}
	
	
	public void menutoJson() throws Exception{
		List<AdMenu> menus = new AdMenuDaoImpl().getall();
		if(menus.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(menus);		
	//	beanListToJson = "{\"root\":"+beanListToJson+"}";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}
	}
	
	
	public void treemenutoJson() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String node =request.getParameter("node");
		System.out.println(node);
		
	//	BdPsndoc psndoc = (BdPsndoc) session.get("bdpsndoc");
		AdMenu admenu = new AdMenuDaoImpl().getByUUID("634988a9e48843418a223249cec3bc44");

		Gson gson = new Gson();
		TreeNode treeNode = new TreeNode(admenu,(String)session.get("cuserid"));
		treeNode.setExpanded(true);
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		String beanListToJson = gson.toJson(treeNode);		
	//	beanListToJson = "{\"root\":"+beanListToJson+"}";
		beanListToJson = "["+beanListToJson+"]";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);
	}
	
	public void treemenucheckedtojson() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String roleuuid =request.getParameter("roleuuid");
		List<AdRolemenu> rolemenus = new AdRoleMenuDaoImpl().getbyrole(roleuuid);
		List<String> strList = new ArrayList<String>();
		for(int i =0;i<rolemenus.size();i++){
			strList.add(rolemenus.get(i).getUuidMenu());
		}
		
		TreeNode treenode = new TreeNode(new AdMenuDaoImpl().getByUUID("634988a9e48843418a223249cec3bc44"),strList);
		treenode.setExpanded(true);
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(treenode);	
		beanListToJson = "["+beanListToJson+"]";
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);
	}
	
	
	public void saverole() throws Exception
    {
		request.setCharacterEncoding("UTF-8");
       
     //   System.out.println(request.toString());
        BufferedReader in = this.request.getReader();
		StringBuffer jsonStr= new StringBuffer();
		String str="";
		while((str=in.readLine())!=null){
			jsonStr.append(str);
		}

		System.out.println(jsonStr.toString());
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write("{success:true}");
    }
	
	public void saverole2() throws Exception
    {
		request.setCharacterEncoding("UTF-8");
       
     //   System.out.println(request.toString());
        String uuidRole =request.getParameter("uuidRole");
        String rolename =request.getParameter("rolename");
        String createTime =request.getParameter("createTime");
        String roledesc =request.getParameter("roledesc");
        
        AdRole adrole = new AdRole();
        
        adrole.setRolename(rolename);
        adrole.setCreateTime(new Date().toString());
        adrole.setRoledesc(roledesc);
        
         Boolean s = adroledao.insert(adrole);

		System.out.println(uuidRole + "   " +rolename+"   " +createTime+"   " +roledesc);
        response.setContentType("text/json;charset=UTF-8");
        if(s){
        response.getWriter().write("{success:true}");}
        else{
        	response.getWriter().write("{success:false}");	}
    }
	
	
	public void saverole3() throws Exception
    {
		//System.out.println(request);
	//	request.setCharacterEncoding("UTF-8");
       
     //   System.out.println(request.toString());
     //   String psnname =request.getParameter("psnname");
   //     String data =request.getParameter("data");
   //     String id =request.getParameter("id");
		String IDStr  = employee.getId();
		String Ai = null;
        String idcheckinfo = IDCard.IDCardValidate(IDStr);        
        System.out.println(employee.getPsnname() + "   " +employee.getId()+"   ");
        response.setContentType("text/json;charset=UTF-8");

        
        if(idcheckinfo==""){
        	List<BdPsnbasdoc> BdPsnbasdocs = new BdPsnbasdocDaoImpl().GetById(employee.getId().trim());
        	List<Employee> employees = new EmployeeDaoImpl().getById(employee.getId().trim());
        	
        	if(BdPsnbasdocs.size()!=0)
        	{
        		BdPsndoc bdPsndoc = new BdPsndocDaoImpl().GetByPk(BdPsnbasdocs.get(0).getPkPsnbasdoc());
        		String deptname = new BdDeptdocDaoImpl().GetByPk(bdPsndoc.getPkDeptdoc()).getDeptname();
        		idcheckinfo = bdPsndoc.getPsnname()+"已在"+deptname+"工作，未离职";
        		response.getWriter().write("{success:false,errors:{id:'"+idcheckinfo+"'}}");
            } 
        	else if(employees.size()!=0)
        	{
        		response.getWriter().write("{success:false,errors:{id:'该身份证号码已登记'}}");
        	}
        	else if(BdPsnbasdocs.size()==0 && employees.size()==0)
        	{
	                if (IDStr.length() == 18) {
	                    Ai = IDStr.substring(0, 17);
	                } else if (IDStr.length() == 15) {
	                    Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
	                }                
	                
	             String birthdate = Ai.substring(6, 10) +"-"+Ai.substring(10, 12)+"-"+Ai.substring(12, 14);
	             String province = IDCard.GetAreaCode().get(Ai.substring(0, 2)).toString(); 
	             
	             if(session.get("bdpsndoc")!=null){	  
	             BdPsndoc bdpsndoc = (BdPsndoc) session.get("bdpsndoc");
	             String pk_deptdoc = bdpsndoc.getPkDeptdoc() ;
	             System.out.println(pk_deptdoc);
	             	response.getWriter().write("{success:true,employee:{birthdate:'"+birthdate+"',province:'"+province+"',psnname:'"+employee.getPsnname()+"',id:'"+IDStr+"',pk_deptdoc:'"+pk_deptdoc+"'}}");
	             }else{
	            	response.getWriter().write("{success:true,employee:{birthdate:'"+birthdate+"',province:'"+province+"',psnname:'"+employee.getPsnname()+"',id:'"+IDStr+"'}}"); 
	             }
        	} 
        	else
        	{
        		response.getWriter().write("{success:false,errors:{id:'"+idcheckinfo+"'}}");
        	}       	
        }else{
        	response.getWriter().write("{success:false,errors:{id:'"+idcheckinfo+"',psnname:'123'}}");      	
        }	
    }
	
	public void savemenu() throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		 String uuidmenu =request.getParameter("uuidmenu");
		 String parentId =request.getParameter("parentId");
		 String text =request.getParameter("text");
		 String type =request.getParameter("type");
		 String menucode =request.getParameter("menucode");		 
		 String aciton = request.getParameter("aciton");
		 String funViewXtype =request.getParameter("funViewXtype");
		 String funController =request.getParameter("funController");
		 String funViewName =request.getParameter("funViewName");
		 System.out.println(parentId + "--" +text+ "--" +funViewXtype+ "--" +funController+ "--" +funViewName);
		 
		 AdMenu menu = new AdMenu();
		 menu.setUuidMenu(uuidmenu);
		 if(funViewXtype !=""){
			 menu.setAction(funViewXtype+","+funController+","+funViewName);
		 }
		 if(!ComUtil.isEmptyString(aciton)){
			 menu.setAction(aciton);
		 }
		 if(menucode !=""){
			 menu.setMenucode(menucode);
		 }
		 menu.setParentid(parentId);
		 menu.setMenuname(text);
		 if(type==null){
			 menu.setType(1);
		 }else{
			 menu.setType(Integer.parseInt(type));
		 }
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");	
		 menu.setCreateTime(format.format(new Date()).toString());		
		 String s = "";
		 
		 if(uuidmenu==null || uuidmenu==""){
			s= new AdMenuDaoImpl().insert(menu);
		 }else
		 {
			s= new AdMenuDaoImpl().update(menu);
		 }
		 	 
		 if(s=="success"){
			 response.getWriter().write("{success:true}");
		 }else{
			 response.getWriter().write("{success:false,errors:{error:'"+s+"'}}");
		 }
	}
	
	public void deletemenu() throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		 String uuidmenu =request.getParameter("uuidmenu");
		 System.out.println(uuidmenu);
		 AdMenu menu = new AdMenuDaoImpl().getByUUID(uuidmenu);	
		 String s = new AdMenuDaoImpl().delete(menu); 
		 if(s=="success"){
			 response.getWriter().write("{success:true}");
		 }else{
			 response.getWriter().write("{success:false,errors:{error:'"+s+"'}}");
		 }			 
	}
	
	public void getmanager() throws Exception{
		//List<String> names = new CSSDaoImpl().getmanager();	
		List<AdPsntypelist> names = new AdPsntypelistDaoImpl().getByPsntype("主管领导");	
	
		if(names.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(names);		
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}		
	}
	
	public void getExtManager() throws Exception{
		List<AdPsntypelist> names = new AdPsntypelistDaoImpl().getByPsntype("主管领导");		
	
		if(names.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(names);		
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}		
	}
	
	public void getQAQC() throws Exception{
		List<AdPsntypelist> names = new AdPsntypelistDaoImpl().getQAQC();		
		
		if(names.size()==0)
		{
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(names);		
		System.out.println(beanListToJson);
		response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}
	
	
	public void reaccredit() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String menuStr = request.getParameter("strjoin");
		String roleid = request.getParameter("roleid");
		
		List<AdRolemenu> adrolemenus = adrolemenuDao.getwebbyrole(roleid);
		for(AdRolemenu adrolem : adrolemenus){
			adrolemenuDao.delete(adrolem);
		}
		
		String[] aa = menuStr.split(",");
		for(String a :aa){
			AdRolemenu adrolem = new AdRolemenu();
			adrolem.setUuidMenu(a);
			adrolem.setUuidRole(roleid);
			adrolem.setCreateTime(new Date().toString());
			adrolemenuDao.insert(adrolem);
		}
		
		this.response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write("{success:true}");
	}
	
	public SmUser getSmuser() {
		return smuser;
	}

	public void setSmuser(SmUser smuser) {
		this.smuser = smuser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public SmUserServ getSmuserserv() {
		return smuserserv;
	}

	public void setSmuserserv(SmUserServ smuserserv) {
		this.smuserserv = smuserserv;
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
