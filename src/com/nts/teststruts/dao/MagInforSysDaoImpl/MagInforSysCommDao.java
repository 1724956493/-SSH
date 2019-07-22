package com.nts.teststruts.dao.MagInforSysDaoImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nts.teststruts.util.Encode;

import lp.util.HibernateBySQL;
import lp.util.Json;

/** @author 李鹏 */
public class MagInforSysCommDao extends HibernateBySQL {
	public Json testMethod(String select, String from, String where) {
		Json returnStatus = new Json();
		try {
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			if (data.size() == 0) {
				returnStatus.setMsg("error:系统没有数据！");
				return returnStatus;// 如果返回为空则报错
			}
			returnStatus.setObj(data);
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setMsg("error:testMethod");
		}
		return returnStatus;
	}

	public Json valicationUserPassword(String select, String from, String where, String password) {
		Json returnStatus = new Json();
		try {
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			if (data.size() == 0) {
				returnStatus.setMsg("errorUser");
				return returnStatus;// 如果返回为空则报错
			}
			Encode encode = new Encode();
			String syspassword = encode.decode(data.get(0).get("user_password") + "").trim();
			if (!syspassword.equals(password)) {
				returnStatus.setMsg("errorPassword");
				return returnStatus;// 如果返回为空则报错
			}
			data.get(0).remove("user_password");
			returnStatus.setObj(data);
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setMsg("error:valicationUserPassword");
		}
		return returnStatus;
	}

	public Json executeSql(String sql) {
		Json returnStatus = new Json();
		try {
			Boolean s = Sql(sql);
			if (s)
				returnStatus.setSuccess(true);
			else {
				returnStatus.setSuccess(false);
				returnStatus.setMsg("数据库操作失败，请检查网络！");
			}
		} catch (Exception e) {
			returnStatus.setMsg("error:sql");
		}
		return returnStatus;
	}

	public Json update(String tableName, String set, String where) {
		Json returnStatus = new Json();
		try {
			Map<String, Object> callBackMap = Update(tableName, set, where);
			if ((boolean) callBackMap.get("success"))
				returnStatus.setSuccess(true);
			else {
				returnStatus.setSuccess(false);
				returnStatus.setMsg(callBackMap.get("msg").toString());
			}
		} catch (Exception e) {
			returnStatus.setSuccess(false);
			returnStatus.setMsg(e.getMessage());
		}
		return returnStatus;
	}

	public Json insertJson(String tableName, String insertJson) {
		Json returnStatus = new Json();
		try {
			JSONObject insertJsonObject = (JSONObject) JSON.parse(insertJson);
			Map<String, Object> callBackMap = Insert(tableName, insertJsonObject);
			if ((boolean) callBackMap.get("success"))
				returnStatus.setSuccess(true);
			else {
				returnStatus.setSuccess(false);
				returnStatus.setMsg(callBackMap.get("msg").toString());
			}
		} catch (Exception e) {
			returnStatus.setSuccess(false);
			returnStatus.setMsg("error:insert");
		}
		return returnStatus;
	}

	public Json insertJsonArray(String tableName, String insertJsonArray) {
		Json returnStatus = new Json();
		try {
			JSONArray InsertContext = JSON.parseArray(insertJsonArray);
			Map<String, Object> callBackMap = Insert(tableName, InsertContext);
			if ((boolean) callBackMap.get("success"))
				returnStatus.setSuccess(true);
			else {
				returnStatus.setSuccess(false);
				returnStatus.setMsg(callBackMap.get("msg").toString());
			}
		} catch (Exception e) {
			returnStatus.setSuccess(false);
			returnStatus.setMsg("error:insert");
		}
		return returnStatus;
	}

	public Json saveUpdateOrInsert(String tableName, String data, String condition) {
		Json returnStatus = new Json();
		try {
			JSONObject dataJson = (JSONObject) JSON.parse(data);
			JSONObject conditionJson = (JSONObject) JSON.parse(condition);
			Map<String, Object> callBackMap = UpdateOrInsert(tableName, dataJson, conditionJson);
			if (!(boolean) callBackMap.get("success")) {
				returnStatus.setSuccess(false);
				returnStatus.setMsg(callBackMap.get("msg").toString());
			} else {
				returnStatus.setSuccess(true);
			}
		} catch (Exception e) {
			returnStatus.setMsg(e.getMessage());
		}
		return returnStatus;
	}

	// 查询数据
	public Json getData(String select, String from, String where) {
		Json returnStatus = new Json();
		try {
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			returnStatus.setObj(data);
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setMsg("error:getData");
		}
		return returnStatus;
	}

	public Json saveUpdateOrInsertArray(String tableName, String data) {
		Json returnStatus = new Json();
		try {
			JSONArray dataJson = (JSONArray) JSON.parse(data);
			Map<String, Object> callBackMap = UpdateOrInsert(tableName, dataJson);

			if (!(boolean) callBackMap.get("success")) {
				returnStatus.setSuccess(false);
				returnStatus.setMsg(callBackMap.get("msg").toString());

			} else {
				returnStatus.setSuccess(true);
			}
		} catch (Exception e) {
			returnStatus.setMsg("error:saveUpdateOrInsert");
		}
		return returnStatus;
	}
}
