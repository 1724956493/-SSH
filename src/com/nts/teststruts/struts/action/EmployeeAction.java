package com.nts.teststruts.struts.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nts.teststruts.dao.impl.AdPsndocRp2DaoImpl;
import com.nts.teststruts.dao.impl.AdPsndocRpDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.dao.impl.EmployeeDaoImpl;
import com.nts.teststruts.dao.impl.HiPsndocGrpdef2DaoImpl;
import com.nts.teststruts.dao.impl.HiPsndocGrpdef4DaoImpl;
import com.nts.teststruts.model.*;
import com.nts.teststruts.service.impl.AdPsndocRpExtServ;
import com.nts.teststruts.service.impl.AdPsndocRpServ;
import com.nts.teststruts.service.impl.CertificateInfo;
import com.nts.teststruts.service.impl.EmployeeInfo;
import com.nts.teststruts.service.impl.RewardAndPunishmentInfo;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

import jxl.write.WriteException;

public class EmployeeAction extends ActionSupport implements ServletResponseAware,ServletRequestAware{
	

	private EmployeeInfo employee;
	private String data;
	private  HttpServletResponse  response;
	private  HttpServletRequest request;  

	public void toJson() throws Exception{
		System.out.println(employee.getEmployeeid());
		BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByPsnPk(employee.getEmployeeid());
		if(bdpsndoc == null)
		{			
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{			
			Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
		employee =  new EmployeeInfo(bdpsndoc);;
		String beanListToJson = gson.toJson(employee);
		System.out.println(employee.getUsedeptname()+beanListToJson);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}
	
	
	
	//http://localhost:8080/ntstruts/json/employee_toJson?employee.employeeid=0001AA1000000002CZVQ
	public void toJsonByPk() throws Exception{
		System.out.println(employee.getEmployeeid());
		BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByPsnPk(employee.getEmployeeid());
		if(bdpsndoc == null)
		{			
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{			
			Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
			employee =  new EmployeeInfo(bdpsndoc);;
		String beanListToJson = gson.toJson(employee);
		System.out.println(employee.getUsedeptname()+beanListToJson);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}
	
	public void toJsonByCode() throws Exception{
		System.out.println(employee.getEmployeeyktcode());
		BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByYktCode(employee.getEmployeeyktcode());
		if(bdpsndoc == null)
		{			
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{			
			Gson gson = new Gson();
		//Type type = new TypeToken<List<SmUser>>(){}.getType();
		//	String beanListToJson = gson.toJson(users, type);
			employee =  new EmployeeInfo(bdpsndoc);;
		String beanListToJson = gson.toJson(employee);
		System.out.println(employee.getUsedeptname()+beanListToJson);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);}
		
	}

	
	//http://localhost:8080/ntstruts/json/employee_tocertificateJson?employee.employeeid=0001AA1000000002CZVQ
	public void tocertificateJson() throws Exception{
		System.out.println(employee.getEmployeeid());
		List<HiPsndocGrpdef4> hipsndocgrpdef4s = new HiPsndocGrpdef4DaoImpl().GetByPK(employee.getEmployeeid());
		List<HiPsndocGrpdef2> hipsndocgrpdef2s = new HiPsndocGrpdef2DaoImpl().GetByPK(employee.getEmployeeid());
		if(hipsndocgrpdef4s.size() == 0 && hipsndocgrpdef2s.size() == 0)
		{			
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		}
		else{			
			Gson gson = new Gson();
			List<CertificateInfo> certificates =new ArrayList<CertificateInfo>();
			
			if(hipsndocgrpdef4s.size() != 0){
			for(int i = 0 ;i<hipsndocgrpdef4s.size();i++)
			{
				certificates.add(new CertificateInfo(hipsndocgrpdef4s.get(i)));
			}};
			
			if(hipsndocgrpdef2s.size() != 0){
			for(int i = 0 ;i<hipsndocgrpdef2s.size();i++)
			{
				certificates.add(new CertificateInfo(hipsndocgrpdef2s.get(i)));
			}};
			
		String beanListToJson = gson.toJson(certificates);
		System.out.println(employee.getUsedeptname()+beanListToJson);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);}		
	}
	

	
	public void getRPAllInfo() throws IOException, SQLException{
		
		request.setCharacterEncoding("UTF-8");
//		String data = request.getParameter("data222");
//		data = URLDecoder.decode(data, "UTF-8"); 
		System.out.println(data);
		Gson gson = new Gson();
		AdPsndocRpServ serv = gson.fromJson(data, AdPsndocRpServ.class);
		AdPsndocRp2DaoImpl aspsndocrp2dao = new AdPsndocRp2DaoImpl();
		List<AdPsndocRpExtServ>  adpsndocrps = aspsndocrp2dao.getbyParam2(serv);
//		int total = aspsndocrpdao.getTotalbyParam(serv.getBegindate(), serv.getEnddate(),serv.getType());
		
		List<AdPsndocRpExtServ>  extservs = new ArrayList<AdPsndocRpExtServ>();
	
		
		String beanListToJson = gson.toJson(extservs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
//		String jsonString = "{\"total\":"+ total+",\"data\":" +beanListToJson +"}";
		response.getWriter().write(beanListToJson);
	}
	
	public void downAllInfo() throws SQLException, IllegalArgumentException, IllegalAccessException, WriteException, IOException{
		System.out.println(data);
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		AdPsndocRpServ serv = gson.fromJson(data, AdPsndocRpServ.class);
		AdPsndocRp2DaoImpl aspsndocrpdao = new AdPsndocRp2DaoImpl();
		List<Map>  adpsndocrps = aspsndocrpdao.getbyParam2(serv);
		System.out.println(adpsndocrps.toString());
		
		String s = gson.toJson(adpsndocrps);
		List<AdPsndocRpExtServ> serv2 = gson.fromJson(s,new TypeToken<List<AdPsndocRpExtServ>>(){}.getType());
		BdDeptdocDaoImpl bddeptdocdaoimpl = new BdDeptdocDaoImpl();
		BdPsndocDaoImpl bdpsndocdaoimpl = new BdPsndocDaoImpl();
		List<AdPsndocRpExtServ>  adpsndocrpextserv = new ArrayList<AdPsndocRpExtServ>();
		
		for(AdPsndocRpExtServ adserv : serv2){
			if(adserv.getEmpuserdept()==null){					
				adserv.setEmpuserdept(adserv.getEmpdept());
			}
			BdPsndoc bdpsndoc =	bdpsndocdaoimpl.GetByPsnPk(adserv.getOperate());
			adserv.setOperate(bdpsndoc.getPsnname());
			adserv.setDept(bddeptdocdaoimpl.GetByPk(bdpsndoc.getPkDeptdoc()).getDeptname());
			adpsndocrpextserv.add(adserv);
		}
		
		List obj = adpsndocrpextserv;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String filname = sdf.format(new Date());
		
		String fileName = "d:/"+filname+".xls";
		
		
		new  ComUtil().excelSave2(obj, fileName);
		
		this.response.setContentType("text/json;charset=UTF-8");
		this.response.getWriter().write("{success:true,filename:'"+filname+"'}");
	}
	
	public void downAllInfo2() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String data = request.getParameter("data"); 
		System.out.println(data+2);		

		String filePath = "d:/";
		String fileName2 =data+".xls";
        new ComUtil().filedown(filePath, fileName2, response); 
		
	}
	
	//http://localhost:8080/ntstruts/insert/employee_insert
	public void insert() throws Exception
    {
		InputStream inputstream =this.request.getInputStream();
		String result = IOUtils.toString(inputstream,"UTF-8");
		System.out.println(result);
		Gson gson = new Gson();
		RewardAndPunishmentInfo rp = gson.fromJson(result, RewardAndPunishmentInfo.class);
		AdPsndocRp adpsndocrp = new AdPsndocRp();
		
		if(rp.getImage() != null && !rp.getImage().equals("")){
			String basepath=ServletActionContext.getServletContext().getRealPath("/")+"upload\\";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String imgname = sdf.format(new Date());
					
			File file = new File(basepath);
			if(!file.exists()){
				file.mkdir(); 
			}
			
			String imgPath  = basepath + imgname ;
			String imgStr = rp.getImage();
			File fileimg=new File(imgPath); 
			if(!fileimg.exists())
			{
				imgPath = imgPath +".jpg";
				imgname = imgname +".jpg";
			}else
			{
				imgPath = imgPath +"a.jpg";
				imgname = imgname +"a.jpg";
			}
			
			boolean flag = new DBUtil().string2Image(imgStr, imgPath); 
			adpsndocrp.setImage(imgname);
		}
		
		BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByPsnPk(rp.getEmployeeid());

	//  adpsndocrp.setUuidRp(UUID.randomUUID().toString().replace("-", ""));
		adpsndocrp.setBonus(rp.getBonus());		
		adpsndocrp.setCknote(rp.getCknote());
		adpsndocrp.setOperate(rp.getOperator());
		adpsndocrp.setDept(rp.getDept());
		adpsndocrp.setType(rp.getType());
		if(null!=rp.getProject()){
		adpsndocrp.setProject(rp.getProject());}
		adpsndocrp.setRight(rp.getStatus2());
		adpsndocrp.setEmpuserdept(bdpsndoc.getGroupdef6());
		adpsndocrp.setEmpdept(bdpsndoc.getPkDeptdoc());
		if(null!=rp.getEmpleader()){
		adpsndocrp.setEmpleader(rp.getEmpleader());}
		
	//	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s");	
		adpsndocrp.setCreateTime(new Date());
		adpsndocrp.setDr(0);

		adpsndocrp.setStatus(rp.getStatus());
		adpsndocrp.setPkPsndoc(rp.getEmployeeid());

		String s = new AdPsndocRpDaoImpl().insert(adpsndocrp);
		
		/*		String[] BonusArray = { "能源", "安全", "4S", "设备", "质量" };  

		if(s!="false")
		{
			if(!Arrays.asList(BonusArray).contains(rp.getBonus()))
			{
				String[] array = rp.getBonus().split(";");
				int newscore = ComUtil.employeescore(array);
				AdPsndocscore adpsndocscore =  new AdPsndocScoreDaoImpl().getbypsndoc(rp.getEmployeeid(),rp.getType());
				if(adpsndocscore !=null)
				{
				   int oldscore = adpsndocscore.getScore();
				   newscore = oldscore+newscore;
				   adpsndocscore.setScore(newscore);
				   new AdPsndocScoreDaoImpl().update(adpsndocscore);
				 }else
				 {
					 AdPsndocscore newadpsndocscore = new AdPsndocscore();
					 newadpsndocscore.setPkPsndoc(rp.getEmployeeid());
					 newadpsndocscore.setScore(newscore);
					 newadpsndocscore.setScoretype(rp.getType());
					 new AdPsndocScoreDaoImpl().insert(newadpsndocscore);
				 }
			 }
		}*/
		
		//this.response.setHeader("Content-type","text/html;charset=UTF-8");
		OutputStream os = this.response.getOutputStream();
		os.write(s.getBytes("UTF-8"));
    }

	public void employeeInfoInsert() throws Exception{
		String psnname =request.getParameter("psnname");
		System.out.println(psnname+"     "+data);
		
		Gson gson = new Gson();
		if(data != null){
			String s ="";
			Employee employee = gson.fromJson(data, Employee.class);
			if("".equals(employee.getEmployeeuuid()))
			{
				s = new EmployeeDaoImpl().insert(employee);
		    }
			else
			{
				s = new EmployeeDaoImpl().update(employee);
			}
		    if(s=="success")
		    {
		    	response.getWriter().write("{success:true}");
		    }else
		    {
		    	System.out.println(s);
		    	response.getWriter().write("{success:false}");
		    }
		}	
	}
	
	public void employeeInfoDelete() throws Exception{
		System.out.println(data);
		if(data!=""||data!=null){
		Employee employee = new EmployeeDaoImpl().getByUuid(data);
		String s = new EmployeeDaoImpl().delete(employee);
		 if(s=="success")
		    {
		    	response.getWriter().write("{success:true}");
		    }else
		    {
		    	System.out.println(s);
		    	response.getWriter().write("{success:false}");
		    }			
		}
	}
	
	
	public void toJsonall() throws Exception{
		List<Employee> employees = new EmployeeDaoImpl().getall();
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(employees);
		response.setCharacterEncoding("UTF-8");
		this.response.getWriter().write(beanListToJson);
	}
	

	public EmployeeInfo getEmployee() {
		return employee;
	}


	public void setEmployee(EmployeeInfo employee) {
		this.employee = employee;
	}

	public String getData() {
			return data;
	}

	public void setData(String data) {
		this.data = data;
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
