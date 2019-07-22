package com.nts.teststruts.struts.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nts.teststruts.dao.impl.AdEquipckCheckcontentDaoImpl;
import com.nts.teststruts.dao.impl.AdLogDaoImpl;
import com.nts.teststruts.dao.impl.AdPsndocRp2DaoImpl;
import com.nts.teststruts.dao.impl.AdPsndocRpDaoImpl;
import com.nts.teststruts.dao.impl.AdPsntypelistDaoImpl;
import com.nts.teststruts.dao.impl.AdWzlistDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdJobbasfilDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.model.*;
import com.nts.teststruts.service.impl.AdPsndocRpExtServ;
import com.nts.teststruts.service.impl.AdPsndocRpServ;
import com.nts.teststruts.service.impl.RewardAndPunishmentInfo;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

public class AdPsndocRPAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware {

	private String data;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map<String, Object> session = new HashMap<String, Object>();

	public void getRPInfo() throws Exception {
		request.setCharacterEncoding("UTF-8");
		String pkpsndoc = request.getParameter("pkpsndoc");
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println();
		List<AdPsndocRp2> adpsndocrps = new AdPsndocRp2DaoImpl().getbyEmpPK(pkpsndoc, type);

		if (adpsndocrps.size() == 0) {
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write("false");
		} else {
			AdWzlistDaoImpl adwzlistdaoimpl = new AdWzlistDaoImpl();
			List<AdPsndocRp2> adpsndocrp2s = new ArrayList<AdPsndocRp2>();
			for (AdPsndocRp2 adpsndocrp : adpsndocrps) {
				adpsndocrp.setBonus(adwzlistdaoimpl.getByUUID(adpsndocrp.getBonus()).getWzlistname());
				adpsndocrp2s.add(adpsndocrp);
			}
			Gson gson = new Gson();
			String beanListToJson = gson.toJson(adpsndocrp2s);
			System.out.println(beanListToJson);
			response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(beanListToJson);
		}
	}

	public void adPsndocRPInsert() throws Exception {
		System.out.println(data);

		Gson gson = new Gson();
		if (data != null) {
			String s = "";
			String log = "";
			AdPsndocRpExtServ adpsndocrpExt = gson.fromJson(data, AdPsndocRpExtServ.class);
			AdPsndocRp adpsndocrp = new AdPsndocRpDaoImpl().getbyUUID(adpsndocrpExt.getUuidrp());
			BdJobbasfilDaoImpl jobbasfil = new BdJobbasfilDaoImpl();

			if (!adpsndocrp.getProject().equals(jobbasfil.GetByName(adpsndocrpExt.getProject()).getPkJobbasfil())) {
				log = "project:" + adpsndocrp.getProject() + " --> "
						+ jobbasfil.GetByName(adpsndocrpExt.getProject()).getPkJobbasfil();
				adpsndocrp.setProject(jobbasfil.GetByName(adpsndocrpExt.getProject()).getPkJobbasfil());
			}

			if (!adpsndocrp.getEmpleader().equals(adpsndocrpExt.getEmpleader())) {
				log += " empleader:" + adpsndocrp.getEmpleader() + " --> " + adpsndocrpExt.getEmpleader();
				adpsndocrp.setEmpleader(adpsndocrpExt.getEmpleader());
			}

			if (!adpsndocrp.getStatus().equals(adpsndocrpExt.getStatus())) {
				log += " Status:" + adpsndocrp.getStatus() + " --> " + adpsndocrpExt.getStatus();
				adpsndocrp.setStatus(adpsndocrpExt.getStatus());
			}

			if (ComUtil.isEmptyString(adpsndocrp.getCknote())) {
				adpsndocrp.setCknote("");

			}
			if (!adpsndocrp.getCknote().equals(adpsndocrpExt.getCknote())) {
				log += "Cknote: " + adpsndocrp.getCknote() + " --> " + adpsndocrpExt.getCknote();
				adpsndocrp.setCknote(adpsndocrpExt.getCknote());
			}

			if (ComUtil.isEmptyString(adpsndocrpExt.getUuidrp())) {
				s = new AdPsndocRpDaoImpl().insert(adpsndocrp);
			} else {
				s = new AdPsndocRpDaoImpl().update(adpsndocrp);
			}

			response.setCharacterEncoding("UTF-8");
			if (s == "success") {
				response.getWriter().write("{success:true}");
				AdLog adlog = new AdLog();
				adlog.setAction("AdPsndocRPAction_adPsndocRPInsert");
				adlog.setCknote("");
				adlog.setCreateTime(new Date());
				adlog.setModifycontent(log);
				adlog.setOperater((String) session.get("cuserid"));
				adlog.setTablename("ad_psndoc_rp");
				adlog.setUuid(adpsndocrp.getUuidRp());
				String ral = new AdLogDaoImpl().insert(adlog);
				System.out.println(ral);
			} else {
				System.out.println(s);
				response.getWriter().write("{success:false}");
			}

		}
	}

	public void adPsndocRPInfobyuuid() throws IOException {

		System.out.println(data);
		AdPsndocRp2 adpsn2 = new AdPsndocRp2DaoImpl().getbyUUID(data);
		Gson gson = new Gson();
		String beanListToJson = gson.toJson(adpsn2);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
	}

	public void adPsndocRPUpdate() throws IOException {
		System.out.println(data);
		String log = "";
		BdJobbasfilDaoImpl jobbasfil = new BdJobbasfilDaoImpl();
		AdPsntypelistDaoImpl psntypelist = new AdPsntypelistDaoImpl();
		Gson gson = new Gson();
		AdPsndocRp2 adpsntemp = gson.fromJson(data, AdPsndocRp2.class);
		AdPsndocRp2 adpsn2 = new AdPsndocRp2DaoImpl().getbyUUID(adpsntemp.getUuid());
		if (!adpsn2.getEmpleader().equals(adpsntemp.getEmpleader())) {
			log += " leader:" + psntypelist.getByUUID(adpsn2.getEmpleader()).getPsnname() + " --> "
					+ psntypelist.getByUUID(adpsntemp.getEmpleader()).getPsnname();
			adpsn2.setEmpleader(adpsntemp.getEmpleader());
		}
		if (!adpsn2.getCknote().equals(adpsntemp.getCknote())) {
			log += " Cknote:" + adpsn2.getCknote() + " --> " + adpsntemp.getCknote();
			adpsn2.setCknote(adpsntemp.getCknote());
		}
		if (!adpsn2.getProject().equals(adpsntemp.getProject())) {
			log += " Project:" + jobbasfil.GetByPk(adpsn2.getProject()).getJobname() + " --> "
					+ jobbasfil.GetByPk(adpsntemp.getProject()).getJobname();
			adpsn2.setProject(adpsntemp.getProject());
		}
		if (!adpsn2.getStatus().equals(adpsntemp.getStatus())) {
			log += " Status:" + adpsn2.getStatus() + " --> " + adpsntemp.getStatus();
			adpsn2.setStatus(adpsntemp.getStatus());
		}
		String s = new AdPsndocRp2DaoImpl().update(adpsn2);

		response.setCharacterEncoding("UTF-8");
		if (s == "success") {
			response.getWriter().write("{success:true}");
			AdLog adlog = new AdLog();
			adlog.setAction("adPsndocRPUpdate");
			adlog.setCknote("");
			adlog.setCreateTime(new Date());
			adlog.setModifycontent(log);
			adlog.setOperater((String) session.get("cuserid"));
			adlog.setTablename("ad_psndoc_rp");
			adlog.setUuid(adpsn2.getUuid());
			String ral = new AdLogDaoImpl().insert(adlog);
			System.out.println(ral);
		} else {
			System.out.println(s);
			response.getWriter().write("{success:false}");
		}
	}

	public void adPsndocRPDelete() throws Exception {
		System.out.println(data);
		response.setCharacterEncoding("UTF-8");
		if (data != "" || data != null) {
			AdPsndocRp adpsndocrp = new AdPsndocRpDaoImpl().getbyUUID(data);
			adpsndocrp.setDr(1);
			String s = new AdPsndocRpDaoImpl().update(adpsndocrp);
			if (s == "success") {
				AdLog adlog = new AdLog();
				adlog.setAction("AdPsndocRPAction_adPsndocRPDelete");
				adlog.setCknote("");
				adlog.setCreateTime(new Date());
				adlog.setModifycontent("dr =1");
				adlog.setOperater((String) session.get("cuserid"));
				adlog.setTablename("ad_psndoc_rp");
				adlog.setUuid(adpsndocrp.getUuidRp());
				String ral = new AdLogDaoImpl().insert(adlog);

				response.getWriter().write("{success:true,msg:'成功'}");
			} else {
				System.out.println(s);
				response.getWriter().write("{success:false,msg:'失败'}");
			}
		}
	}

	public void adPsndocRP2Insert() throws Exception {

		InputStream inputstream = this.request.getInputStream();
		String result = IOUtils.toString(inputstream, "UTF-8");
		int i = result.length();
		if (i == 0) {
			request.setCharacterEncoding("UTF-8");
			result = this.request.getParameter("submitData");
			System.out.println(result);
		}
		Gson gson = new Gson();
		RewardAndPunishmentInfo rp = gson.fromJson(result, RewardAndPunishmentInfo.class);
		AdPsndocRp2 adpsndocrp2 = new AdPsndocRp2();

		if (rp.getImage() != null && !rp.getImage().equals("")) {
			String basepath = ServletActionContext.getServletContext().getRealPath("/") + "upload\\";

			// String basepath = "d:\\upload\\xunjian\\";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String imgname = sdf.format(new Date());

			File file = new File(basepath);
			if (!file.exists()) {
				file.mkdir();
			}

			String imgPath = basepath + imgname;
			String imgStr = rp.getImage();
			File fileimg = new File(imgPath);
			if (!fileimg.exists()) {
				imgPath = imgPath + ".jpg";
				imgname = imgname + ".jpg";
			} else {
				imgPath = imgPath + "a.jpg";
				imgname = imgname + "a.jpg";
			}

			boolean flag = new DBUtil().string2Image(imgStr, imgPath);
			adpsndocrp2.setImage(imgname);
		}

		BdPsndoc bdpsndoc = new BdPsndocDaoImpl().GetByPsnPk(rp.getEmployeeid());

		// adpsndocrp.setUuidRp(UUID.randomUUID().toString().replace("-", ""));
		adpsndocrp2.setBonus(rp.getBonus());
		adpsndocrp2.setCknote(rp.getCknote());
		adpsndocrp2.setOperate(rp.getOperator());
		adpsndocrp2.setType(rp.getType());
		if (null != rp.getProject()) {
			adpsndocrp2.setProject(rp.getProject());
		}
		adpsndocrp2.setRight(rp.getStatus2());
		adpsndocrp2.setEmpuserdept(bdpsndoc.getGroupdef6());
		adpsndocrp2.setEmpdept(bdpsndoc.getPkDeptdoc());
		if (null != rp.getEmpleader()) {
			adpsndocrp2.setEmpleader(rp.getEmpleader());
		}

		// SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		adpsndocrp2.setCreateTime(new Date());
		adpsndocrp2.setDr(0);

		adpsndocrp2.setStatus(rp.getStatus());
		adpsndocrp2.setPkPsndoc(rp.getEmployeeid());

		String s = new AdPsndocRp2DaoImpl().insert(adpsndocrp2);

		/*
		 * String[] BonusArray = { "能源", "安全", "4S", "设备", "质量" };
		 * 
		 * if(s!="false") {
		 * if(!Arrays.asList(BonusArray).contains(rp.getBonus())) { String[]
		 * array = rp.getBonus().split(";"); int newscore =
		 * ComUtil.employeescore(array); AdPsndocscore adpsndocscore = new
		 * AdPsndocScoreDaoImpl().getbypsndoc(rp.getEmployeeid(),rp.getType());
		 * if(adpsndocscore !=null) { int oldscore = adpsndocscore.getScore();
		 * newscore = oldscore+newscore; adpsndocscore.setScore(newscore); new
		 * AdPsndocScoreDaoImpl().update(adpsndocscore); }else { AdPsndocscore
		 * newadpsndocscore = new AdPsndocscore();
		 * newadpsndocscore.setPkPsndoc(rp.getEmployeeid());
		 * newadpsndocscore.setScore(newscore);
		 * newadpsndocscore.setScoretype(rp.getType()); new
		 * AdPsndocScoreDaoImpl().insert(newadpsndocscore); } } }
		 */

		// this.response.setHeader("Content-type","text/html;charset=UTF-8");
		OutputStream os = this.response.getOutputStream();
		os.write(s.getBytes("UTF-8"));
	}

	public void getRP2AllInfo() throws IOException, SQLException {
		request.setCharacterEncoding("UTF-8");

		System.out.println(data);
		Gson gson = new Gson();
		AdPsndocRpServ serv = gson.fromJson(data, AdPsndocRpServ.class);
		AdPsndocRp2DaoImpl aspsndocrpdao = new AdPsndocRp2DaoImpl();
		List<Map> adpsndocrps = aspsndocrpdao.getbyParam2(serv);
		System.out.println(adpsndocrps.toString());

		String s = gson.toJson(adpsndocrps);

		// AdPsndocRpExtServ adpsndocrpext = new AdPsndocRpExtServ();

		List<AdPsndocRpExtServ> serv2 = gson.fromJson(s, new TypeToken<List<AdPsndocRpExtServ>>() {
		}.getType());
		BdDeptdocDaoImpl bddeptdocdaoimpl = new BdDeptdocDaoImpl();
		BdPsndocDaoImpl bdpsndocdaoimpl = new BdPsndocDaoImpl();
		List<AdPsndocRpExtServ> adpsndocrpextserv = new ArrayList<AdPsndocRpExtServ>();

		for (AdPsndocRpExtServ adserv : serv2) {
			if (adserv.getEmpuserdept() == null) {
				adserv.setEmpuserdept(adserv.getEmpdept());
			}
			BdPsndoc bdpsndoc = bdpsndocdaoimpl.GetByPsnPk(adserv.getOperate());
			adserv.setOperate(bdpsndoc.getPsnname());
			adserv.setDept(bddeptdocdaoimpl.GetByPk(bdpsndoc.getPkDeptdoc()).getDeptname());
			adpsndocrpextserv.add(adserv);
		}

		String beanListToJson = gson.toJson(adpsndocrpextserv).toLowerCase();
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
	}

	public void getRP2payclose() throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String data = request.getParameter("data");
		String[] strArray = data.split(";");
		AdPsndocRp2DaoImpl adpsndocrp2impl = new AdPsndocRp2DaoImpl();
		String ral1 = "";
		for (int i = 0; i < strArray.length; i++) {
			AdPsndocRp2 aspsndocrp2 = adpsndocrp2impl.getbyUUID(strArray[i]);
			aspsndocrp2.setPaystatus(1);

			AdLog adlog = new AdLog();
			adlog.setAction("AdPsndocRPAction_getRP2payclose");
			adlog.setCknote("");
			adlog.setCreateTime(new Date());
			adlog.setModifycontent("Paystatus =1");
			adlog.setOperater((String) session.get("cuserid"));
			adlog.setTablename("ad_psndoc_rp2");
			adlog.setUuid(strArray[i]);
			ral1 = adpsndocrp2impl.update(aspsndocrp2);
			String ral = new AdLogDaoImpl().insert(adlog);
		}
		if (ral1 == "success") {
			response.getWriter().write("{success:true}");
		} else {
			response.getWriter().write("{success:false}");
		}
	}

	public void getQCReport() throws IOException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String startdate = request.getParameter("startdate").substring(0, 10);
		String enddate = request.getParameter("enddate").substring(0, 10);
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println(startdate + "    " + enddate + type);

		Gson gson = new Gson();
		List objs = new AdPsndocRpDaoImpl().getQCReport(startdate, enddate, type);
		System.out.println(gson.toJson(objs));

		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
	}

	public void getDeptReport() throws IOException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String startdate = request.getParameter("startdate").substring(0, 10);
		String enddate = request.getParameter("enddate").substring(0, 10);
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println(startdate + "    " + enddate + type);

		Gson gson = new Gson();
		List objs = new AdPsndocRpDaoImpl().getDeptReport(startdate, enddate, type);
		System.out.println(gson.toJson(objs));

		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
	}

	public void getProjectReport() throws IOException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String startdate = request.getParameter("startdate").substring(0, 10);
		String enddate = request.getParameter("enddate").substring(0, 10);
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println(startdate + "    " + enddate + type);

		Gson gson = new Gson();
		List objs = new AdPsndocRpDaoImpl().getProjectReport(startdate, enddate, type);
		System.out.println(gson.toJson(objs));

		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
	}

	public void getWztypeReport() throws IOException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String startdate = request.getParameter("startdate").substring(0, 10);
		String enddate = request.getParameter("enddate").substring(0, 10);
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println(startdate + "    " + enddate + type);

		Gson gson = new Gson();
		List objs = new AdPsndocRpDaoImpl().getWztypeReport(startdate, enddate, type);
		System.out.println(gson.toJson(objs));

		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
	}

	public void getEmpleaderReport() throws IOException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String startdate = request.getParameter("startdate").substring(0, 10);
		String enddate = request.getParameter("enddate").substring(0, 10);
		int type = Integer.parseInt(request.getParameter("type"));
		System.out.println(startdate + "    " + enddate + type);

		Gson gson = new Gson();
		List objs = new AdPsndocRpDaoImpl().getEmpleaderReport(startdate, enddate, type);
		System.out.println(gson.toJson(objs));

		String beanListToJson = gson.toJson(objs);
		System.out.println(beanListToJson);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(beanListToJson);
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
		this.request = request;

	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}

}
