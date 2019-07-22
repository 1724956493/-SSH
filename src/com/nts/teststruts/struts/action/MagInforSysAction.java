package com.nts.teststruts.struts.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nts.teststruts.dao.MagInforSysDaoImpl.MagInforSysCommDao;

import com.nts.teststruts.dao.MagInforSysDaoImpl.MagInforSysEquipDeptDao;

import com.opensymphony.xwork2.ActionSupport;

public class MagInforSysAction extends ActionSupport
		implements ServletResponseAware, ServletRequestAware, SessionAware {
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map session;

	private MagInforSysCommDao mis = new MagInforSysCommDao();
	private MagInforSysEquipDeptDao misED = new MagInforSysEquipDeptDao();

	public void testMethod() {
		String[] params = new String[] { "select", "from", "where" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.testMethod(list.get("select"), list.get("from"), list.get("where")));
	}

	public void valicationUserPassword() {
		String[] params = new String[] { "user", "password" };
		Map<String, String> list = formateRequestBodyData(params);
		String select = "c.ssnum, c.pk_psnbasdoc,c.mobile,a.cuserid ,a.user_name,a.user_code,a.user_password ,d.pk_psndoc,d.groupdef10, d.pk_deptdoc as pk_deptdoc ,e.deptname,";
		select += "(select wmsys.wm_concat(p.key) keys from ad_mis_permission  p where p.dr=0 and p.pk_pms in (select mrp.pk_pms from ad_mis_role_permission mrp where mrp.dr=0 and mrp.pk_role in (select rp.pk_role from ad_mis_role_person rp where rp.dr=0 and rp.pk_person=c.ssnum ))) as keys";
		String from = "{sm_user a} ,{sm_userandclerk b}, {bd_psnbasdoc c},{bd_psndoc d},{bd_deptdoc e}";
		String where = " where a.cuserid=b.userid and c.pk_psnbasdoc=b.pk_psndoc and d.pk_psnbasdoc=c.pk_psnbasdoc";
		where += "  and  instr(trim(a.user_name),trim(c.psnname))>0  and   d.pk_deptdoc=e.pk_deptdoc  and d.dr !=1 and d.psnclscope in (0,5) and  a.user_code='"
				+ list.get("user") + "'";
		writeJson(mis.valicationUserPassword(select, from, where, list.get("password")));
	}

	public void getEquipCheckClassName() {
		String select = "distinct classname as classname,free02";
		String from = "{ad_equipck_checkcontent a}";
		String where = " where  a.dr=0  order by classname";
		writeJson(misED.getEquipCheckClassName(select, from, where));
	}

	public void getEquipCheckPoint() {
		String[] params = new String[] { "pk_classname", "beginNum", "endNum" };
		Map<String, String> list = formateRequestBodyData(params);

		String select = " a.*,rn,maxrows";
		String from = "(select a.*,rownum rn from ";
		from += "(select a.*,(select count(*) from  ad_equipck_checkcontent a where a.dr=0 and a.pk_classname='"
				+ list.get("pk_classname") + "' ) maxrows ";
		from += "from {ad_equipck_checkcontent  a} where  a.dr=0 and a.pk_classname='" + list.get("pk_classname")
				+ "'  order by ts desc ) a where rownum <" + list.get("endNum") + ") a";
		String where = "where rn>" + list.get("beginNum");
		writeJson(misED.getEquipCheckPoint(select, from, where));
	}

	public void insertJsonArray() {
		String[] params = new String[] { "tableName", "insertJsonArray" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.insertJsonArray(list.get("tableName"), list.get("insertJsonArray")));
	}

	public void insertJson() {
		String[] params = new String[] { "tableName", "insertJson" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.insertJson(list.get("tableName"), list.get("insertJson")));
	}

	public void executeSql() {
		String[] params = new String[] { "sql" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.executeSql(list.get("sql")));
	}

	public void getData() {
		String[] params = new String[] { "select", "from", "where" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.getData(list.get("select"), list.get("from"), list.get("where")));
	}

	public void update() {
		String[] params = new String[] { "tableName", "set", "where" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.update(list.get("tableName"), list.get("set"), list.get("where")));
	}

	public void saveUpdateOrInsert() {
		String[] params = new String[] { "data", "condition", "tableName" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.saveUpdateOrInsert(list.get("tableName"), list.get("data"), list.get("condition")));

	}

	public void saveUpdateOrInsertArray() {
		String[] params = new String[] { "data", "tableName" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(mis.saveUpdateOrInsertArray(list.get("tableName"), list.get("data")));
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

	public Map<String, String> formateRequestBodyData(String arr[]) {

		Map<String, String> returnArr = new HashMap<String, String>();
		JSONObject reqBody = reqeusetBodyData();
		for (int i = 0; i < arr.length; i++) {
			String item = request.getParameter(arr[i].trim());
			if (item == null) {
				if (reqBody != null) {
					Object bodyItem = reqBody.get(arr[i]);
					if (bodyItem != null) {
						item = bodyItem + "";
						returnArr.put(arr[i].trim(), item);
					}
				}
			} else {
				returnArr.put(arr[i].trim(), item);
			}
		}
		return returnArr;
	}

	public JSONObject reqeusetBodyData() {
		BufferedReader br;
		StringBuilder sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject getData = (JSONObject) JSON.parse(sb.toString());
			return getData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

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
	public void setSession(Map session) {
		this.session = session;
	}
}
