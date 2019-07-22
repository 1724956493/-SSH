package com.nts.teststruts.struts.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nts.teststruts.dao.DingUserDaoImpl.CommDaoImpl;
import com.nts.teststruts.dao.DingUserDaoImpl.EquipmentManageDaoImpl;
import com.nts.teststruts.dao.DingUserDaoImpl.EquipmentRepairDaoImpl;
import com.nts.teststruts.dao.DingUserDaoImpl.PersonManageDaoImpl;
import com.nts.teststruts.dao.DingUserDaoImpl.TestDaoImpl;
import com.nts.teststruts.dao.impl.BdPsnbasdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.dao.impl.SmUserDaoImpl;
import com.nts.teststruts.service.impl.SmUserServ;
import com.opensymphony.xwork2.ActionSupport;

public class DingUserAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware {

	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map session;
	private BdPsnbasdocDaoImpl psnbasdocdao = new BdPsnbasdocDaoImpl();
	private BdPsndocDaoImpl psndocdao = new BdPsndocDaoImpl();
	private SmUserDaoImpl sudao = new SmUserDaoImpl();
	private SmUserServ smuserserv;
	private TestDaoImpl DingUser = new TestDaoImpl();
	private CommDaoImpl comm = new CommDaoImpl();
	private EquipmentRepairDaoImpl equipRepair = new EquipmentRepairDaoImpl();
	private PersonManageDaoImpl personManage = new PersonManageDaoImpl();
	private EquipmentManageDaoImpl equipManage = new EquipmentManageDaoImpl();
	private AuthHelper authHelper = new AuthHelper();

	public void alibaba_getConfig() {
		Map<String, String> list = formateRequestBodyData(new String[] { "pageUrl" });
		String pageUrl = list.get("pageUrl");
		String nonceStr = "abcdefgh";
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = pageUrl;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		String agentid = null;
		try {
			accessToken = AuthHelper.getAccessToken();
			ticket = AuthHelper.getJsapiTicket(accessToken);
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
			agentid = "";

		} catch (OApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> configValue = new HashMap<String, Object>();
		configValue.put("jsticket", ticket);
		configValue.put("signature", signature);
		configValue.put("nonceStr", nonceStr);
		configValue.put("timeStamp", timeStamp);
		configValue.put("corpId", Env.getCorpId());
		configValue.put("agentid", agentid);
		configValue.put("accessToken", accessToken);
		writeJson(configValue);
	}

	/**
	 * @设备管理
	 * @基本信息查询
	 */
	public void equipManage_getBaseInfor() {
		Map<String, String> list = formateRequestBodyData(new String[] { "equipPK" });
		writeJson(equipManage.EquipManage_GetBaseInfor(list.get("equipPK")));
	}

	/**
	 * @设备管理
	 * @加载设备保养所有信息
	 */
	public void equipManage_equipMaintainMounted() {
		Map<String, String> list = formateRequestBodyData(new String[] { "equipPK", "checkType" });
		writeJson(equipManage.EquipMaintain_Mounted(list.get("equipPK"), list.get("checkType")));
	}

	/**
	 * @设备管理
	 * @设备保养检查提交信息
	 */
	public void equipManage_equipMaintainSubmit() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitJson" });
		writeJson(equipManage.EquipMaintain_submit(list.get("submitJson")));
	}

	public void equipManage_equipMaintainQuery() {
		Map<String, String> list = formateRequestBodyData(new String[] { "equipPK" });
		writeJson(equipManage.EquipMaintain_Query(list.get("equipPK")));
	}

	/**
	 * @人员管理提交
	 * 
	 */
	public void personManage_submit() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitJson" });
		writeJson(personManage.submit(list.get("submitJson")));

	}

	/**
	 * @人员管理渲染前所需要加载的所有数据
	 * 
	 */
	public void personManage_getMountedData() {
		Map<String, String> list = formateRequestBodyData(new String[] { "type" });
		writeJson(personManage.getMountedData(Integer.parseInt(list.get("type"))));
	}

	/**
	 * @人员管理违章整改
	 * 
	 */
	public void personManage_reformUpdate() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitData" });
		writeJson(personManage.reformUpdate(list.get("submitData")));
	}

	/**
	 * @人员管理，根据提交人员pk获取违章数据
	 * 
	 * 
	 */
	public void personManage_getBreakInforByOperator() {
		Map<String, String> list = formateRequestBodyData(
				new String[] { "pkpsndoc", "beginTime", "endTime", "queryStatus", "breakSataus" ,"project"});
		writeJson(personManage.getBreakInforByOperator(list.get("pkpsndoc"), list.get("beginTime"), list.get("endTime"),
				list.get("queryStatus"), list.get("breakSataus"),list.get("project")));
	}

	/**
	 * @人员管理 根据员工pk查询违规数据
	 */
	public void personManage_getBreakInforByPkpsndoc() {
		Map<String, String> list = formateRequestBodyData(new String[] { "pkpsndoc", "pageType" });
		writeJson(personManage.getBreakInforByPkpsndoc(list.get("pkpsndoc"), list.get("pageType")));
	}

	/** @人员管理 根据员工一卡通综合查询 */
	public void presonManage_getPersonIntegratedInfor() {
		Map<String, String> list = formateRequestBodyData(new String[] { "onecode", "beginTime", "endTime" });
		writeJson(
				personManage.getPersonIntegratedInfor(list.get("onecode"), list.get("beginTime"), list.get("endTime")));
	}

	/** @人员管理 根据员工pk查询违规数据 */
	public void personManage_getBreakInforByPkpsndocAll() {
		Map<String, String> list = formateRequestBodyData(new String[] { "pk_psndoc", "beginTime", "endTime" });
		writeJson(personManage.getBreakInforByPkpsndoc(list.get("pk_psndoc"), list.get("beginTime"),
				list.get("endTime")));
	}

	/**
	 * @设备维修-->分配维修申请单
	 */
	// public void distributionApplyRepairData() {
	public void equipRepair_setDISTed() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitData" });
		writeJson(equipRepair.setDISTed(list.get("submitData")));
	}

	/**
	 * @设备维修-->维修 确认 退回
	 */
	// public void confirmApplyRepairData() {
	public void equipRepair_setConfirmed() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitData" });
		writeJson(equipRepair.setConfirmed(list.get("submitData")));
	}

	/**
	 * @设备维修-->维修 申请单中的机器
	 */
	// public void repairApplyRepairData() {
	public void equipRepair_setRepaired() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitData" });
		writeJson(equipRepair.setRepaired(list.get("submitData")));
	}

	/**
	 * @设备维修-->按时间区间查询设备维修申请单与设备表联合查询
	 * @以listJson格式返回指定列数据
	 */
	// public void queryApplyRepairDataByTime() {
	public void equipRepair_getInforByTimeArea() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitData" });
		writeJson(equipRepair.getInforByTimeArea(list.get("submitData")));
	}

	/**
	 * @设备维修--> 按apppk查询设备维修申请,与设备表联合查询
	 * @以listJson格式返回指定列数据
	 */
	// public void queryApplyRepairDataByPK() {
	public void equipRepair_getInforByPk() {
		// String apppk ="487a7385eb1e44b0811724ae89009072";
		Map<String, String> list = formateRequestBodyData(new String[] { "apppk" });
		writeJson(equipRepair.getInforByPk(list.get("apppk")));
	}

	/**
	 * @设备维修--> 增加设备维修申请
	 * @返回生成的UUID
	 */
	// public void addApplyRepairData() {
	public void equipRepair_add() {
		Map<String, String> list = formateRequestBodyData(new String[] { "submitData" });
		writeJson(equipRepair.add(list.get("submitData")));
	}

	/**
	 * @钉钉向人员发送工作消息
	 *
	 */
	public void equipRepair_sendMessageToDingDing() {
		String[] params = new String[] { "status", "touser", "content", "agentid", "accessToken", "single_url",
				"apppk" };
		Map<String, String> list = formateRequestBodyData(params);
		equipRepair.sendMessageToDingDing(list.get("status"), list.get("touser"), list.get("content"),
				list.get("agentid"), list.get("accessToken"), list.get("single_url"), list.get("apppk"));
	}

	/**
	 * @车牌图片节点-> 返回图片中的车牌号
	 *
	 */
	public void comm_getCarInfoByPic() {
		Map<String, String> list = formateRequestBodyData(new String[] { "carPic", "appid", "appkey", "secretkey" });
		writeJson(
				comm.getCarInfoByPic(list.get("carPic"), list.get("appid"), list.get("appkey"), list.get("secretkey")));
	}

	public void comm_getCarInfoById() {
		Map<String, String> list = formateRequestBodyData(new String[] { "carId" });
		writeJson(comm.getCarInfoById(list.get("carId")));
	}

	public void comm_getDingUser() {
		Map<String, String> list = formateRequestBodyData(new String[] { "code", "dingID" });
		writeJson(comm.getDingUser(list.get("code"), list.get("dingID")));
	}

	/**
	 * @通过手机号查bd_psndoc表，联合bd_psnbasdoc查询
	 * @以listJson格式返回指定列数据
	 */
	// public void query_bd_psndocByPhone() {
	public void comm_getPersonInforByPhone() {
		Map<String, String> list = formateRequestBodyData(new String[] { "userPhone" });
		writeJson(comm.getPersonInforByPhone(list.get("userPhone")));
	}

	/**
	 * @通过人员档案里的一卡通查询人员信息
	 */
	public void comm_getPersonInfoByYKT() throws Exception {
		Map<String, String> list = formateRequestBodyData(new String[] { "onecode" });
		writeJson(comm.getPersonInfoByYKT(list.get("onecode")));
	}

	/**
	 * @主管领导
	 */
	public void comm_getLeaders() {
		writeJson(comm.getLeaders());
	}

	/** 获取一卡通数据库上的部门信息 */
	public void comm_getDeptInfor() {
		writeJson(comm.getDeptInfor());
	}

	/** @项目号 */
	public void comm_getProjects() {
		Map<String, String> list = formateRequestBodyData(new String[] { "projectType" });
		// 当前用的是 0001C110000000000DF7
		writeJson(comm.getProjects(list.get("projectType")));
	}

	/** 根据部门名称或人员姓名 获取人员列表 */
	public void comm_getPersonListByNameOrDeptName() {
		Map<String, String> list = formateRequestBodyData(new String[] { "name", "deptname" });
		writeJson(comm.getPersonListByNameOrDeptName(list.get("name"), list.get("deptname")));
	}

	/** 根据员工pk获取 证书信息 */
	public void comm_getCertificate() {
		Map<String, String> list = formateRequestBodyData(new String[] { "pk_psndoc" });
		writeJson(comm.getCertificate(list.get("pk_psndoc")));
	}

	/** 根据人员pk获取奖惩信息 */
	public void comm_getBonous() {
		String[] params = new String[] { "psnbasdoc", "begtime", "endtime" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.getBonous(list.get("psnbasdoc"), list.get("begtime"), list.get("endtime")));
	}

	public void testMethod() throws Exception {
		String[] params = new String[] { "select", "from", "where" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(DingUser.testMethod(list.get("select"), list.get("from"), list.get("where")));
	}

	public void insertJsonArray() {
		String[] params = new String[] { "tableName", "insertJsonArray" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.insertJsonArray(list.get("tableName"), list.get("insertJsonArray")));
	}

	public void insertJson() {
		String[] params = new String[] { "tableName", "insertJson" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.insertJson(list.get("tableName"), list.get("insertJson")));
	}

	public void executeSql() {
		String[] params = new String[] { "sql" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.executeSql(list.get("sql")));
	}

	public void getData() {
		String[] params = new String[] { "select", "from", "where" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.getData(list.get("select"), list.get("from"), list.get("where")));
	}

	public void update() {
		String[] params = new String[] { "tableName", "set", "where" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.update(list.get("tableName"), list.get("set"), list.get("where")));
	}

	public void saveUpdateOrInsert() {
		String[] params = new String[] { "data", "condition", "tableName" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.saveUpdateOrInsert(list.get("tableName"), list.get("data"), list.get("condition")));

	}

	public void saveUpdateOrInsertArray() {
		String[] params = new String[] { "data", "tableName" };
		Map<String, String> list = formateRequestBodyData(params);
		writeJson(comm.saveUpdateOrInsertArray(list.get("tableName"), list.get("data")));
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
}
