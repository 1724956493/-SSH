package com.nts.teststruts.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.nts.teststruts.dao.impl.AdUserroleDaoImpl;
import com.nts.teststruts.dao.impl.QueryBonuseDaoImpl;
import com.nts.teststruts.model.AdUserrole;
import com.nts.teststruts.mypage.QuyerBonues;
import com.opensymphony.xwork2.ActionSupport;

import lp.util.Json;

public class QueryBonuseAction extends ActionSupport
		implements ServletResponseAware, ServletRequestAware, SessionAware {

	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map session;
	QueryBonuseDaoImpl qb = new QueryBonuseDaoImpl();
	AdUserroleDaoImpl adUserRole = new AdUserroleDaoImpl();

	public void addUserRole() {
		Json j = new Json();
		try {
			request.setCharacterEncoding("UTF-8");
			String userDate = this.request.getParameter("userDate").trim();
			String roleID = this.request.getParameter("roleID").trim();
			List<String> demoList = new ArrayList<String>();
			JSONArray userDateJson = JSON.parseArray(userDate);
			for (int i = 0; i < userDateJson.size(); i++) {
				demoList.add(userDateJson.getJSONObject(i).getString("用户名").trim());
			}
			adUserRole.insert(roleID, demoList, false);
			j.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		writeJson(j);
	}

	public void getBonuse() throws Exception {
		request.setCharacterEncoding("UTF-8");

		String result = this.request.getParameter("phone");
		Json j = new Json();
		// "13775718383"
		try {

			List<QuyerBonues> getqb = qb.getDetailbyPhone(result);
			j.setSuccess(true);
			j.setObj(getqb);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// response.getWriter().append(e.getMessage());
			j.setMsg(e.getMessage());
		}
		writeJson(j);
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
