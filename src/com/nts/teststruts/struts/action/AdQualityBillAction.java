package com.nts.teststruts.struts.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nts.teststruts.dao.impl.AdqualitybillDaoImpl;
import com.nts.teststruts.dao.impl.AdqualitybillSubDaoImpl;
import com.nts.teststruts.dao.impl.BaseQueryDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdJobbasfilDaoImpl;
import com.nts.teststruts.dao.impl.BdPsnbasdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.model.Adqualitybill;
import com.nts.teststruts.model.AdqualitybillSub;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.util.BillCodeUtil;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.DateUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import jxl.write.WriteException;


public class AdQualityBillAction extends ActionSupport implements ServletResponseAware,ServletRequestAware,SessionAware {
	
	private  HttpServletResponse  response;
	private  HttpServletRequest request;
	private Adqualitybill adqualitybill;
	private AdqualitybillSub adqualitybillsub;
	private Map session ;
	private String data;
	BdDeptdocDaoImpl bddeptdao = new BdDeptdocDaoImpl();
	BdPsndocDaoImpl bdpsndocDao = new BdPsndocDaoImpl();
	BdJobbasfilDaoImpl jobdao = new BdJobbasfilDaoImpl();
	AdqualitybillSubDaoImpl adqualitybillsubdao = new AdqualitybillSubDaoImpl();
	AdqualitybillDaoImpl adqualitybilldao = new AdqualitybillDaoImpl();
	
	
	public void insert() throws IOException{
		System.out.println(data);
		response.setContentType("text/json;charset=UTF-8");
		Gson gson = new Gson();
		try{
			adqualitybill = gson.fromJson(data, Adqualitybill.class);
	       }catch(Exception e){
	    	   this.response.getWriter().write("{success:false,msg:'"+e.toString()+"'}");
			   return;
	    }	
		
		Boolean s = false;
		
			//判断登陆状态
		BdPsndoc bdpsndoc = (BdPsndoc)session.get("bdpsndoc");
			if(bdpsndoc!=null){
				if(ComUtil.isEmptyString(adqualitybill.getUuid())){
					//判断单据号是否重复
					List<Adqualitybill> adqualitybills = adqualitybilldao.getbybillcode(adqualitybill.getBillcode());
					if(adqualitybills.size()<=0){
						adqualitybill.setTs(new Date());
						adqualitybill.setAppts(new Date());
						adqualitybill.setTotalmulct(0);
						adqualitybill.setTotalreward(0);
						adqualitybill.setAppstatus("0");
						adqualitybill.setDr("0");
				//		adqualitybill.setBillcode("GYX"+BillCodeUtil.getbillcode());			
						adqualitybill.setOperator(bdpsndoc.getPkPsnbasdoc());
						s = adqualitybilldao.insert(adqualitybill);}
					else{
						this.response.getWriter().write("{success:false,msg:'单据号重复，请修改当前单据号！'}");
						return;
					}
				}else{
		//			adqualitybill.setTs(new Date());
					adqualitybill.setAppts(new Date());
					adqualitybill.setDr("0");
					adqualitybill.setOperator(bdpsndoc.getPkPsnbasdoc());
					s = adqualitybilldao.update(adqualitybill);
				}
				
				if(s){
				    this.response.getWriter().write("{success:true,msg:'保存成功'}");	
				}else{
					this.response.getWriter().write("{success:false,msg:'数据录入失败，请重新录入'}");	
				}
			}else{
				this.response.getWriter().write("{success:false,msg:'30分钟登陆未有动作，请刷新网页重新登陆后录入数据'}");
			}
		}
	
	
	public void delete() throws IOException{
		Adqualitybill adqualitybill2 = adqualitybilldao.getbyuuid(data);
		List<AdqualitybillSub> adqualitybillSubs = adqualitybillsubdao.getbyHuuid(data);
		for(AdqualitybillSub adqualitybillSub : adqualitybillSubs){
			adqualitybillsubdao.delete(adqualitybillSub);
		}
		response.setContentType("text/json;charset=UTF-8");
		if(adqualitybilldao.delete(adqualitybill2)){
		    this.response.getWriter().write("{success:true}");	
		}else{
			this.response.getWriter().write("{success:false}");	
		}
	}
	
	public void getall() throws SQLException {
		
		String data = request.getParameter("data");
		System.out.println(data);
		Map<String,String> maps = (Map<String,String>)JSON.parse(data);
	//	System.out.println(maps.toString());
		List<Adqualitybill> adqualitybills= adqualitybilldao.getallbytype(maps);
		for(Adqualitybill adqualitybill : adqualitybills){
			adqualitybill.setDiaocha(DateUtil.getWeekOfYear(adqualitybill.getTs())+"");
			adqualitybill.setDept(bddeptdao.GetByPk(adqualitybill.getDept()).getDeptname());
			if(adqualitybill.getWbdept()!=null){
			adqualitybill.setWbdept(bddeptdao.GetByPk(adqualitybill.getWbdept()).getDeptname());}
			if(adqualitybill.getProject()!=null){
				adqualitybill.setProject(jobdao.GetByPk(adqualitybill.getProject()).getJobname());
			}
			if(adqualitybill.getOperator()!=null){
				adqualitybill.setOperator(bdpsndocDao.GetByPk(adqualitybill.getOperator()).getPsnname());
			}
		}
		
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
		try {
			this.response.getWriter().write(gson.toJson(adqualitybills));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getbyuuid(){
		Adqualitybill adqualitybill = new AdqualitybillDaoImpl().getbyuuid(data);
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
		try {
			this.response.getWriter().write("{success:true,data:"+gson.toJson(adqualitybill)+"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void subinsert() throws IOException{
		System.out.println(data);
		Gson gson = new Gson();
		adqualitybillsub = gson.fromJson(data, AdqualitybillSub.class);
		adqualitybillsub.setTs(new Date());
		Boolean s = false;
		if(ComUtil.isEmptyString(adqualitybillsub.getUuid())){
			 s = new AdqualitybillSubDaoImpl().insert(adqualitybillsub);
			 if(s){
					Adqualitybill adqualitybill = adqualitybilldao.getbyuuid(adqualitybillsub.getHuuid());
					if(adqualitybillsub.getMulct()!=0){
						adqualitybill.setTotalmulct(adqualitybill.getTotalmulct()+adqualitybillsub.getMulct());
						adqualitybilldao.update(adqualitybill);
					}
					if(adqualitybillsub.getReward()!=0){
						adqualitybill.setTotalreward(adqualitybill.getTotalreward()+adqualitybillsub.getReward());
						adqualitybilldao.update(adqualitybill);
					}
					 adqualitybillsub.setTs(new Date()); 
			 }
		}else{
			 adqualitybillsub.setTs(new Date());
			 s = new AdqualitybillSubDaoImpl().update(adqualitybillsub);	
		}
		response.setContentType("text/json;charset=UTF-8");
		if(s){
		    this.response.getWriter().write("{success:true}");	
		}else{
			this.response.getWriter().write("{success:false}");	
		}
	}
	
	public void subdelete() throws IOException{
		AdqualitybillSub adqualitybillsub = adqualitybillsubdao.getbyuuid(data);
		response.setContentType("text/json;charset=UTF-8");
		Adqualitybill adqualitybill = adqualitybilldao.getbyuuid(adqualitybillsub.getHuuid());
		if(adqualitybillsub.getMulct()!=0){
			adqualitybill.setTotalmulct(adqualitybill.getTotalmulct()-adqualitybillsub.getMulct());
			adqualitybilldao.update(adqualitybill);
		}
		if(adqualitybillsub.getReward()!=0){
			adqualitybill.setTotalreward(adqualitybill.getTotalreward()-adqualitybillsub.getReward());
			adqualitybilldao.update(adqualitybill);
		}
		if(adqualitybillsubdao.delete(adqualitybillsub)){
		    this.response.getWriter().write("{success:true}");	
		}else{
			this.response.getWriter().write("{success:false}");	
		}
	}
	
	public void getsuball() throws IOException, SQLException{
		List<AdqualitybillSub> adqualitybillsubs=  adqualitybillsubdao.getbyHuuid(data);
		for(AdqualitybillSub adqualitybillsub : adqualitybillsubs){
			adqualitybillsub.setDept(bddeptdao.GetByPk(adqualitybillsub.getDept()).getDeptname());
			if(adqualitybillsub.getPsnname()!=null){
				adqualitybillsub.setPsnname(bdpsndocDao.GetByPk(adqualitybillsub.getPsnname()).getPsnname());
			}
		}
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
		try {
			this.response.getWriter().write(gson.toJson(adqualitybillsubs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getbysubuuid(){
		AdqualitybillSub adqualitybill = adqualitybillsubdao.getbyuuid(data);
		Gson gson = new Gson();
		response.setContentType("text/json;charset=UTF-8");
		System.out.println("{success:true,data:"+gson.toJson(adqualitybill)+"}");
		try {
			this.response.getWriter().write("{success:true,data:"+gson.toJson(adqualitybill)+"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reviewHtml() throws SQLException{
		System.out.println(data);
		Adqualitybill adquality = adqualitybilldao.getbyuuid(data);
		List<AdqualitybillSub> adqualitybillsubs=  adqualitybillsubdao.getbyHuuid(data);
		String htmlPath =ServletActionContext.getServletContext().getRealPath("/fkhtml");
		String pdfPath =ServletActionContext.getServletContext().getRealPath("/fkpdf");
		String qrpath = htmlPath+"\\"+adquality.getUuid()+".jpg";
		

		try {
			ComUtil.PrintQRcode(adquality.getUuid(), qrpath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(adquality.getDept() != null){
		adquality.setDept(bddeptdao.GetByPk(adquality.getDept()).getDeptname());}
		if(adquality.getWbdept() != null){
		adquality.setWbdept(bddeptdao.GetByPk(adquality.getWbdept()).getDeptname());}
		if(adquality.getProject() != null){
		adquality.setProject(jobdao.GetByPk(adquality.getProject()).getJobname());}

		
		for(AdqualitybillSub adqualitybillsub : adqualitybillsubs){
			adqualitybillsub.setDept(bddeptdao.GetByPk(adqualitybillsub.getDept()).getDeptname());
			if(adqualitybillsub.getPsnname()!=null){
				adqualitybillsub.setPsnname(bdpsndocDao.GetByPk(adqualitybillsub.getPsnname()).getPsnname());
			}
		}
		
				
		Map map = new HashMap();
		map.put("adquality", adquality);
		map.put("adqualitybillsublist", adqualitybillsubs);
		map.put("QRimage", adquality.getUuid()+".jpg");
		ComUtil.freemaker(adquality.getType().toLowerCase()+".ftl", adquality.getBillcode()+".html", map);
		ComUtil.printpdf(htmlPath+"\\"+adquality.getBillcode()+".html", pdfPath+"\\"+adquality.getBillcode()+".pdf");
		try {
			this.response.getWriter().write("{success:true,msg:'"+adquality.getBillcode()+"'}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void approve(){
		String approvestatus = request.getParameter("approvestatus");
		Adqualitybill adquality = adqualitybilldao.getbyuuid(data);
		adquality.setAppstatus(approvestatus);
		adquality.setAppts(new Date());
		BdPsndoc bdpsndoc = (BdPsndoc)session.get("bdpsndoc");
		adquality.setAppoperator(bdpsndoc.getPkPsnbasdoc());
		response.setContentType("text/json;charset=UTF-8");
		adqualitybilldao.update(adquality);
	}		
	
	public void getRewardByName() throws IOException{
		BdPsndoc bdpsndoc = (BdPsndoc)session.get("bdpsndoc");
		List<AdqualitybillSub> adqualitybillsubs = adqualitybillsubdao.getbypsnname(bdpsndoc.getPkPsnbasdoc());
		if(adqualitybillsubs.size()>0){
			List<Adqualitybill> adqualitybills = new ArrayList<Adqualitybill>();
			for(AdqualitybillSub adqualitybillsub : adqualitybillsubs){
				adqualitybills.add(adqualitybilldao.getbyuuid(adqualitybillsub.getHuuid()));				
			}
			Gson gson = new Gson();
			response.setContentType("text/json;charset=gbk");
			System.out.println("{success:true,data:"+gson.toJson(adqualitybill)+"}");
			this.response.getWriter().write("{\"success\":true,\"record\":"+gson.toJson(adqualitybills)+"}");			
		}else{
			this.response.getWriter().write("{\"success\":false,\"record\":\"错误\"}");
			
		}		
	}
	
	public void getreportgriddata() throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String begindate = request.getParameter("begindate");
		String enddate = request.getParameter("enddate");
		String appstatus = request.getParameter("appstatus");
		String manager = request.getParameter("manager");
		String project = request.getParameter("project");
		String dept = request.getParameter("dept");
		String fenxi = request.getParameter("fenxi");
		String type = request.getParameter("type");

		Map<String,String> maps = new HashMap<String,String>();
		maps.put("begindate", begindate);
		maps.put("enddate", enddate);
		maps.put("appstatus", appstatus);
		maps.put("project", project);
		maps.put("dept", dept);
		maps.put("fenxi", fenxi);
		maps.put("resourceid", type);
		
		if(!ComUtil.isEmptyString(manager)){
			List<AdqualitybillSub> adqubillsubs = adqualitybillsubdao.getbypsnname(manager);
			if(adqubillsubs.size()>0){
				String a3="";
				for(AdqualitybillSub sub : adqubillsubs){
					a3 = a3+ sub.getHuuid() +",";
				}
				maps.put("manager",a3);
			}
		}
		
		try {
			List<Adqualitybill> adqualitybills= adqualitybilldao.getallbytype(maps);
			for(Adqualitybill adqualitybill : adqualitybills){
				adqualitybill.setDiaocha(DateUtil.getWeekOfYear(adqualitybill.getTs())+"");
				
				adqualitybill.setDept(bddeptdao.GetByPk(adqualitybill.getDept()).getDeptname());
				
				if(adqualitybill.getWbdept()!=null){
					adqualitybill.setWbdept(bddeptdao.GetByPk(adqualitybill.getWbdept()).getDeptname());
				}
				if(adqualitybill.getProject()!=null){
					adqualitybill.setProject(jobdao.GetByPk(adqualitybill.getProject()).getJobname());
				}
				if(adqualitybill.getOperator()!=null){
					adqualitybill.setOperator(bdpsndocDao.GetByPk(adqualitybill.getOperator()).getPsnname());
				}		
			}
			
			Gson gson = new Gson();
			response.setContentType("text/json;charset=UTF-8");
			this.response.getWriter().write(gson.toJson(adqualitybills));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public void byproject() throws IOException, SQLException{
		request.setCharacterEncoding("utf-8");
		String startdate = request.getParameter("startdate");
		String enddate   = request.getParameter("enddate");
		String appstatus = request.getParameter("appstatus");
		String[] status = appstatus.split(",");
		String type = request.getParameter("type");
		
		List<Object[]> aa = new ArrayList<Object[]>();
		
		if(type.equals("ZLGYX")){
			String sql ="select wzlistcode,wzlistname from ad_wzlist where wztype ='8a87819e578fcbab0157922ea1340019' order by wzlistcode";
			aa = new BaseQueryDaoImpl().objBySql(sql);
		 }else if(type.equals("QCJL")){
			aa.add(new Object[]{"JLA1","项目一次性通过奖励"});
			aa.add(new Object[]{"JLA2","月度通过率奖励"});
		 }
		StringBuilder sb = new StringBuilder();	
		sb.append("select project project1,project project2");
		for(Object[] a : aa){
			sb.append(",max(decode(fenxi,'"+a[0]+"',shuliang)) as "+a[0]);
		}
		sb.append(" from (select fenxi,project,count(fenxi) shuliang from adqualitybill l where type='");
		sb.append(type);
		sb.append("' and appstatus in ");
		sb.append(new DBUtil().sqlInString(status));
		sb.append(" and to_char(ts,'yyyy-MM-dd') >= '");
		sb.append(startdate);
		sb.append("' and to_char(ts,'yyyy-MM-dd') <=  '");
		sb.append(enddate);
	    sb.append( "' group by fenxi,project) group by project");
		
	    System.out.println(sb.toString());
	    
		List<Object[]>  objs    = new BaseQueryDaoImpl().objBySql(sb.toString());
		
		for(Object[] a : objs){					
			a[1] = jobdao.GetByPk(a[1].toString()).getJobcode();
		}		
		
		Gson gson = new Gson();	
		
		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);		
	}
	
	public void bydept() throws IOException, SQLException{
		request.setCharacterEncoding("utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String appstatus = request.getParameter("appstatus");
		String type = request.getParameter("type");
		
		List<Object[]> aa = new ArrayList<Object[]>();
		
		if(type.equals("ZLGYX")){
			String sql ="select wzlistcode,wzlistname from ad_wzlist where wztype ='8a87819e578fcbab0157922ea1340019' order by wzlistcode";
			aa = new BaseQueryDaoImpl().objBySql(sql);
		 }else if(type.equals("QCJL")){
			aa.add(new Object[]{"JLA1","项目一次性通过奖励"});
			aa.add(new Object[]{"JLA2","月度通过率奖励"});
		 }
		
		StringBuilder sb = new StringBuilder();	
		sb.append("select dept dept1,dept dept2");
		for(Object[] a : aa){
			sb.append(",max(decode(fenxi,'"+a[0]+"',shuliang)) as "+a[0]);
		}
		sb.append(" from (select fenxi,dept,count(fenxi) shuliang from adqualitybill l where type='");
		sb.append(type);
		sb.append("' and appstatus in  ");
		sb.append(new DBUtil().sqlInString(appstatus.split(",")));
		sb.append(" and to_char(ts,'yyyy-MM-dd') >= '");
		sb.append(startdate);
		sb.append("' and to_char(ts,'yyyy-MM-dd') <=  '");
		sb.append(enddate);
		sb.append("' group by fenxi,dept) group by dept");
		
		List<Object[]>  objs    = new BaseQueryDaoImpl().objBySql(sb.toString());
		
		for(Object[] a : objs){							
			
			a[1] = bddeptdao.GetByPk(a[1].toString()).getDeptname();
		}
		
		Gson gson = new Gson();	
		
		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);		
	}
	
	
	public void bymanager() throws IOException, SQLException{
		request.setCharacterEncoding("utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String appstatus = request.getParameter("appstatus");
		String type = request.getParameter("type");
		
		List<Object[]> aa = new ArrayList<Object[]>();
		
		if(type.equals("ZLGYX")){
			String sql ="select wzlistcode,wzlistname from ad_wzlist where wztype ='8a87819e578fcbab0157922ea1340019' order by wzlistcode";
			aa = new BaseQueryDaoImpl().objBySql(sql);
		 }else if(type.equals("QCJL")){
			aa.add(new Object[]{"JLA1","项目一次性通过奖励"});
			aa.add(new Object[]{"JLA2","月度通过率奖励"});
		 }
		
		StringBuilder sb = new StringBuilder();	
		sb.append("select psnname psnname1,psnname psnname2 ");
		for(Object[] a : aa){
			sb.append(",max(decode(fenxi,'"+a[0]+"',shuliang)) as "+a[0]);
		}
		sb.append(" from (select l.fenxi,b.psnname,count(*) as shuliang from adqualitybill_sub b left join adqualitybill l on l.uuid = b.huuid where l.type='");
		sb.append(type);
		sb.append("' and l.appstatus in ");
		sb.append(new DBUtil().sqlInString(appstatus.split(",")));
		sb.append(" and b.joblevel = '主管领导' and to_char(l.ts,'yyyy-MM-dd') >= '");
		sb.append(startdate);
		sb.append("' and to_char(l.ts,'yyyy-MM-dd') <=  '");
		sb.append(enddate);	
		sb.append("' group by l.fenxi,b.psnname)  group by psnname");
		
		List<Object[]>  objs    = new BaseQueryDaoImpl().objBySql(sb.toString());
		
		for(Object[] a : objs){							
			a[1] = bdpsndocDao.GetByPk(a[1].toString()).getPsnname();
		}
		
		
		Gson gson = new Gson();
		
		
		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);		
	}
	
	public void exportlist() throws Exception{
		request.setCharacterEncoding("utf-8");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String appstatus = request.getParameter("appstatus");
		String type = request.getParameter("type");
		
		List<Object[]> aa = new ArrayList<Object[]>();
		if(type.equals("ZLGYX")){
			String sql ="select wzlistcode,wzlistname from ad_wzlist where wztype ='8a87819e578fcbab0157922ea1340019' order by wzlistcode";
			aa = new BaseQueryDaoImpl().objBySql(sql);
		 }else if(type.equals("QCJL")){
			aa.add(new Object[]{"JLA1","项目一次性通过奖励"});
			aa.add(new Object[]{"JLA2","月度通过率奖励"});
		 }
		
		
		StringBuilder sb = new StringBuilder();	
		sb.append("select l.billcode,f.jobname,o.deptname deptname1,p.deptname deptname2,decode(l.appstatus,'2','通报不处罚','1','审核通过','3','审核不通过','未审核'),l.totalmulct,c.psnname,d.deptname deptname3,b.mulct,b.reward from adqualitybill l ");
		sb.append("left join adqualitybill_sub b on l.uuid = b.huuid ");
		sb.append("left join bd_psnbasdoc c on c.pk_psnbasdoc = b.psnname ");
		sb.append("left join bd_deptdoc d on d.pk_deptdoc = b.dept ");
		sb.append("left join bd_deptdoc o on o.pk_deptdoc = l.dept ");
		sb.append("left join bd_deptdoc p on p.pk_deptdoc = l.wbdept ");
		sb.append("left join bd_jobbasfil f on f.pk_jobbasfil = l.project ");
		sb.append("where l.type='");
		sb.append(type);
		sb.append("' and l.appstatus in  ");
		sb.append(new DBUtil().sqlInString(appstatus.split(",")));
		sb.append(" and to_char(l.ts,'yyyy-MM-dd') >= '");
		sb.append(startdate);
		sb.append("' and to_char(l.ts,'yyyy-MM-dd') <=  '");
		sb.append(enddate);	
		sb.append("' order by l.billcode ");
		
		List<Object[]>  aa2    = new BaseQueryDaoImpl().objBySql(sb.toString());
		String[] header =  new String[]{"单据号","项目号","责任部门","责任外包队","单据状态","合计处罚费用","责任人姓名","所在部门","处罚金额","奖励金额"};
		
		String filePath = "d:/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xls";
		
		response.setCharacterEncoding("UTF-8");
		
		try {
			new ComUtil().excelSavelistobj(aa2,filePath+fileName,header);
			response.getWriter().write("{success:true , filePath :'"+filePath+"',fileName:'"+fileName+"'}");
		} catch (IllegalArgumentException | IllegalAccessException | WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.getWriter().write("{success:false}");
		}		
		
	}
	
	public void exportpsn() throws Exception{
		
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String appstatus = request.getParameter("appstatus");
		
		StringBuilder sb = new StringBuilder();	
		sb.append("select d.deptname deptname1,o.deptname deptname2,c.psnname,a.joblevel,a.summulct,a.sumreward ");
		sb.append("from (select l.dept dept1,b.dept dept2,b.psnname,b.joblevel,sum(b.mulct) summulct,sum(b.reward) sumreward from adqualitybill l  ");
		sb.append("right join adqualitybill_sub b on l.uuid = b.huuid ");
		sb.append("where l.type in ('ZLGYX','QCJL') and l.appstatus in   ");
		sb.append(new DBUtil().sqlInString(appstatus.split(",")));
		sb.append(" and to_char(l.ts,'yyyy-MM-dd') >= '");
		sb.append(startdate);
		sb.append("' and to_char(l.ts,'yyyy-MM-dd') <=  '");
		sb.append(enddate);	
		sb.append("' group by b.psnname,b.dept,b.joblevel,l.dept) a ");
		sb.append("left join bd_deptdoc d on d.pk_deptdoc = a.dept1 ");
		sb.append("left join bd_deptdoc o on o.pk_deptdoc = a.dept2 ");
		sb.append("left join bd_psnbasdoc c on c.pk_psnbasdoc = a.psnname ");
		sb.append("order by d.deptname,o.deptname,c.psnname ");
		
		List<Object[]>  aa2    = new BaseQueryDaoImpl().objBySql(sb.toString());
		String[] header =  new String[]{"责任部门","责任人所在部门","责任人姓名","责任级别","合计处罚费用","合计奖励费用"};
		
		String filePath = "d:/";
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xls";
		
		response.setCharacterEncoding("UTF-8");
		
		try {
			new ComUtil().excelSavelistobj(aa2,filePath+fileName,header);
			response.getWriter().write("{success:true , filePath :'"+filePath+"',fileName:'"+fileName+"'}");
		} catch (IllegalArgumentException | IllegalAccessException | WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			response.getWriter().write("{success:false}");
		}		
		
	}
	
	public void upscanfile() throws IOException{
		String scanfilename = request.getParameter("scanfilename");
		System.out.println(scanfilename +"    "  +data );
		Adqualitybill adquality = new AdqualitybillDaoImpl().getbyuuid(data);
		adquality.setScanfilename(scanfilename);
		if(adqualitybilldao.update(adquality)){
		    this.response.getWriter().write("{success:true}");	
		}else{
			this.response.getWriter().write("{success:false}");	
		}
	}

	public Adqualitybill getAdqualitybill() {
		return adqualitybill;
	}



	public void setAdqualitybill(Adqualitybill adqualitybill) {
		this.adqualitybill = adqualitybill;
	}



	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;		
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;		
	}

	public AdqualitybillSub getAdqualitybillsub() {
		return adqualitybillsub;
	}

	public void setAdqualitybillsub(AdqualitybillSub adqualitybillsub) {
		this.adqualitybillsub = adqualitybillsub;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public void setSession(Map session) {
		// TODO Auto-generated method stub
		this.session=session;
	}  

	
	
}
