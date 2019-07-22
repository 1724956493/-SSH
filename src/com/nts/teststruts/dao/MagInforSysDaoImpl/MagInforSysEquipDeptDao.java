package com.nts.teststruts.dao.MagInforSysDaoImpl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nts.teststruts.util.DBUtil;

import lp.util.HibernateBySQL;
import lp.util.Json;

public class MagInforSysEquipDeptDao extends HibernateBySQL {
	// 获取设备点检的设备名称
	public Json getEquipCheckClassName(String select, String from, String where) {
		Json retunStatus = new Json();
		try {
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			if (data.size() == 0) {
				retunStatus.setMsg("error:系统没有数据！");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(data);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			retunStatus.setMsg("error:getEquipCheckClassName");
		}
		return retunStatus;
	}

	// 获取设备点检的点检项
	public Json getEquipCheckPoint(String select, String from, String where) {
		Json retunStatus = new Json();
		try {
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			if (data.size() == 0) {
				retunStatus.setMsg("error:该设备没有点检项！");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(data);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			retunStatus.setMsg("error:getEquipCheckPoint");
		}
		return retunStatus;
	} 
	
}
