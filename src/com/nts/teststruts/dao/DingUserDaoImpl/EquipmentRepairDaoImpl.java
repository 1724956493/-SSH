package com.nts.teststruts.dao.DingUserDaoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lp.util.GetPostDataFromHTML;
import lp.util.HibernateBySQL;
import lp.util.Json;

public class EquipmentRepairDaoImpl extends HibernateBySQL {
	Map<String, Object> callBackMap = null;

	/**
	 * @设备维修-->set维修分配
	 */
	public Json setDISTed(String submitData) {
		Json returnStatus = new Json();
		try {
			String tableName = "ad_equip_apply_repair";
			JSONObject Json = (JSONObject) JSON.parse(submitData);
			String where = "where  apppk='" + Json.getString("apppk") + "'";
			Json.remove("apppk");
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Json.put("distribution_ts", dateformat.format(new Date()));
			Json.put("status", "待维修");
			callBackMap = Update(tableName, Json, where);
			if (!(boolean) callBackMap.get("success")) {
				return returnStatus;
			}
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			returnStatus.setSuccess(false);
		}
		return returnStatus;
	}

	/**
	 * @设备维修-->set维修确认/退回
	 */
	public Json setConfirmed(String submitData) {
		Json returnStatus = new Json();
		try {
			JSONObject Json = (JSONObject) JSON.parse(submitData);
			// 更新ad_equip_apply_repair 状态
			String tableName = "ad_equip_apply_repair";
			JSONObject Json2 = new JSONObject();
			String where = "where  apppk='" + Json.getString("apppk") + "'";
			if (Json.get("confirm_status").toString().contains("退回")) {
				Json2.put("status", "退回");
			} else {
				Json2.put("status", "完成");
			}
			Json.remove("confirm_status");
			callBackMap = Update(tableName, Json2, where);
			if (!(boolean) callBackMap.get("success")) {
				return returnStatus;
			}

			// 插入或更新ad_equip_confirm数据
			String tableName2 = "ad_equip_repair_confirm";

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Json.put("confirm_ts", "$to_date ('" + dateformat.format(new Date()) + "', 'YYYY-MM-DD HH24:MI:SS' )");
			JSONObject Condition = new JSONObject();
			Condition.put("apppk", Json.get("apppk"));
			callBackMap = UpdateOrInsert(tableName2, Json, Condition);
			if (!(boolean) callBackMap.get("success")) {
				return returnStatus;
			}
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setSuccess(false);
		}
		return returnStatus;
	}

	/**
	 * @设备维修-->set维修已完成
	 */
	public Json setRepaired(String submitData) {

		Json returnStatus = new Json();
		try {
			JSONObject Json = (JSONObject) JSON.parse(submitData);
			// 更新ad_equip_apply_repair 状态
			String tableName = "ad_equip_apply_repair";
			JSONObject Json2 = new JSONObject();
			Json2.put("status", "待确定");
			String where = "where  apppk='" + Json.getString("apppk") + "'";
			callBackMap = Update(tableName, Json2, where);
			if (!(boolean) callBackMap.get("success")) {
				return returnStatus;
			}
			// 插入或更新ad_equip_repair数据
			tableName = "ad_equip_repair";
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Json.put("repair_ts", "$to_date ('" + dateformat.format(new Date()) + "', 'YYYY-MM-DD HH24:MI:SS' )");
			JSONObject Condition = new JSONObject();
			Condition.put("apppk", Json.get("apppk"));
			callBackMap = UpdateOrInsert(tableName, Json, Condition);
			if (!(boolean) callBackMap.get("success")) {
				return returnStatus;
			}
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setSuccess(false);
		}
		return returnStatus;
	}

	/**
	 * @设备维修-->按时间区间查询设备维修申请单
	 * @与设备表联合查询
	 * @以listJson格式返回指定列数据
	 */
	public List<Map<String, Object>> getInforByTimeArea(String submitData) {
		String from = "{ad_equip_apply_repair a} ,{pam_equip p}";
		JSONObject Json = (JSONObject) JSON.parse(submitData);
		String where = " where a.equippk=p.pk_equip ";
		if (Json.getString("method").equals("query")) {
			if (!Json.getString("status").contains("全部"))
				where += " and a.status= '" + Json.getString("status") + "' ";
			if (Json.getString("userPh") != null)
				where += " and a.app_userph='" + Json.getString("userPh") + "' ";
			where += " and a.ts between to_date('" + Json.getString("begintime") + "', 'YYYY-MM-DD HH24:MI:SS')";
			where += " and to_date('" + Json.getString("endtime") + "', 'YYYY-MM-DD HH24:MI:SS')";
			where += " order by a.ts";
		}
		if (Json.getString("method").equals("scan")) {
			if (!Json.getString("status").contains("全部"))
				where += " and a.status= '" + Json.getString("status") + "' ";

			if (Json.getString("userPh") != null)
				where += " and a.app_userph='" + Json.getString("userPh") + "' ";

			if (Json.getString("endtime").length() > 0 && Json.getString("begintime").length() > 0) {
				where += " and a.ts between to_date('" + Json.getString("begintime") + "', 'YYYY-MM-DD HH24:MI:SS')";
				where += " and to_date('" + Json.getString("endtime") + "', 'YYYY-MM-DD HH24:MI:SS')";
			}
			where += " and a.equippk='" + Json.getString("equip_pk") + "' ";
			where += " order by a.ts";
		}
		if (Json.getString("method").equals("queryByPhone")) {
			where += " and a.app_userph='" + Json.getString("userPh") + "' ";
			where += " order by a.ts";
		}
		String select = "a.apppk,a.status,a.ts,p.equip_name,p.equip_code,p.model,p.spec,p.def3";
		return QueryTablesSample(select, from, where);
	}

	/**
	 * @设备维修--> 按apppk查询设备维修申请,与设备表联合查询
	 * @以listJson格式返回指定列数据
	 */
	public List<Map<String, Object>> getInforByPk(String apppk) {
		// 查询出 表 a , r ,c 三张表所有字段，p个别字段
		// 设备名称，代码 规格model，型号spec，现场识别码def3
		String select = "a.*,r.*,c.*,p.equip_name,p.equip_code,p.model,p.spec,p.def3";
		String from = " (( {ad_equip_apply_repair a} left join {ad_equip_repair r} on a.apppk=r.apppk) left join {pam_equip p} on a.equippk=p.pk_equip) left join {ad_equip_repair_confirm c} on a.apppk=c.apppk";
		String where = "where a.apppk='" + apppk + "'";
		return QueryTablesSample(select, from, where);

	}

	/**
	 * @设备维修--> 增加设备维修申请
	 * @返回生成的UUID
	 */
	public Json add(String submitData) {
		String tableName = "ad_equip_apply_repair";
		Json returnStatus = new Json();
		try {
			JSONObject Json = (JSONObject) JSON.parse(submitData);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Json.put("ts", "$to_date ('" + dateformat.format(new Date()) + "', 'YYYY-MM-DD HH24:MI:SS' )");
			Json.put("status", "待分配");
			String uuid = UUID.randomUUID().toString().replace("-", "");
			Json.put("apppk", uuid);
			System.out.println(Json);
			System.out.println(Json.get("equipPK"));
			callBackMap = Insert(tableName, Json); 
			returnStatus.setSuccess((boolean) callBackMap.get("success"));
			returnStatus.setMsg(uuid);
		} catch (Exception e) {
			// TODO: handle exception

		}

		return returnStatus;
	}

	/**
	 * @ 通过钉钉向人员发送工作消息
	 *
	 */
	public void sendMessageToDingDing(String status, String touser, String content, String agentid, String accessToken,
			String single_url, String apppk) {
		String url = "https://oapi.dingtalk.com/message/send?access_token=" + accessToken;
		Map<String, Object> JsonFist = new HashMap<String, Object>();
		Map<String, Object> JsonSecend = new HashMap<String, Object>();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String markdown = "";
		if (status.equals("我的申请")) {
			String name = JSON.parseObject(content).getString("name");
			String code = JSON.parseObject(content).getString("code");
			markdown = "# 我的设备维修申请  \n ##### 设备名称:" + name + " \n  ##### 设备编码 ：" + code + "\n ##### 申请时间:"
					+ dateformat.format(new Date());
			JsonSecend.put("title", "设备维修申请");
			JsonSecend.put("single_url",
					single_url
							+ "/mobile/contents/EquipmentMaintenance/DocStatus/DocStatus.jsp?dd_nav_bgcolor=FF5E97F6&apppk="
							+ apppk);
		}

		if (status.equals("申请")) {
			markdown = "# 设备维修申请  \n ##### 申请人:" + content + " \n ##### 申请时间:" + dateformat.format(new Date());
			JsonSecend.put("title", "设备维修申请");
			JsonSecend.put("single_url",
					single_url
							+ "/mobile/contents/EquipmentMaintenance/Distribution/Distribution.jsp?dd_nav_bgcolor=FF5E97F6&apppk="
							+ apppk);
		}

		if (status.contains("分配")) {
			markdown = "# 设备维修已分配 ,请维修！\n ##### 分配人:" + content + " \n ##### 分配时间:" + dateformat.format(new Date());
			JsonSecend.put("title", "设备维修分配");
			JsonSecend.put("single_url", single_url
					+ "/mobile/contents/EquipmentMaintenance/Repair/Repair.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + apppk);
		}
		if (status.contains("维修")) {
			markdown = "# 设备已维修 ,请确认！ \n ##### 维修人:" + content + " \n ##### 维修时间:" + dateformat.format(new Date());
			JsonSecend.put("title", "设备维修确定");
			JsonSecend.put("single_url",
					single_url
							+ "/mobile/contents/EquipmentMaintenance/Confirm/Confirm.jsp?dd_nav_bgcolor=FF5E97F6&apppk="
							+ apppk);
		}
		if (status.contains("完成")) {
			markdown = "# 设备维修完成！ \n  #####  确定人:" + content + " \n ##### 确定时间:" + dateformat.format(new Date());
			JsonSecend.put("title", "设备维修完成");
			JsonSecend.put("single_url",
					single_url
							+ "/mobile/contents/EquipmentMaintenance/Confirm/Confirm.jsp?dd_nav_bgcolor=FF5E97F6&apppk="
							+ apppk);
		}
		if (status.contains("退回")) {
			markdown = "# 设备维修退回，请重新维修！ \n ##### 退回人:" + content + " \n ##### 退回时间:" + dateformat.format(new Date());
			JsonSecend.put("title", "设备维修退回");
			JsonSecend.put("single_url", single_url
					+ "/mobile/contents/EquipmentMaintenance/Repair/Repair.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + apppk);
		}
		JsonSecend.put("markdown", markdown);
		JsonSecend.put("single_title", "查看详细信息");
		JsonFist.put("touser", touser);
		JsonFist.put("toparty", "");
		JsonFist.put("agentid", agentid);// 微应用ID
		JsonFist.put("action_card", JsonSecend);
		JsonFist.put("msgtype", "action_card");
		String json = JSON.toJSONString(JsonFist);
		GetPostDataFromHTML.postData2(url, json);
	}

}
