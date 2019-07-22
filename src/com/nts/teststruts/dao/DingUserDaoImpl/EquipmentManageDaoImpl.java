package com.nts.teststruts.dao.DingUserDaoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lp.util.HibernateBySQL;
import lp.util.Json;

public class EquipmentManageDaoImpl extends HibernateBySQL {
	/** 设备基本信息查询 */
	public Json EquipManage_GetBaseInfor(String equipPK) {
		Json returnStatus = new Json();
		String from = "{pam_equip p}";
		String select = "p.pk_equip,p.equip_code,p.equip_name,p.model,p.spec,p.def3 as localid,p.pk_category,p.start_used_date,";
		// def3 现场识别码Spec型号model规格
		//可用状态
		select +="(select s.status_name from pam_status s where s.pk_status=p.pk_used_status) as usedstatus,";
		// 所属公司
		select += "(select a.unitname from bd_corp a where  a.pk_corp =p.pk_corp) as equipcorp ,";
		// 隶属部门
		select += " (select b.deptname from bd_deptdoc b where  b.pk_deptdoc =p.pk_mandept) as equipdeptname,";
		// 使用部门
		select += "(select c.deptname from bd_deptdoc c where  c.pk_deptdoc =p.pk_usedept) as equipusedeptname,";
		// 所在场地
		select += " (select d.location_name from pam_location d where  d.pk_location =p.pk_location) as equiplocatname";
		String where = "where  p.pk_equip='" + equipPK + "'";
		try {
			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, where);
			if (result.size() > 0) {
				returnStatus.setObj(result);
				returnStatus.setSuccess(true);
			} else {
				returnStatus.setMsg("未查询到任何记录");
			}
		} catch (Exception e) {
			// TODO: handle exception
			returnStatus.setMsg("err:查询数据出错");
		}
		return returnStatus;
	}

	/**
	 * @设备管理
	 * @设备保养检查项查询
	 */
	public Json EquipMaintain_GetCheckContent(String equipName, String checkType) {
		Json returnStatus = new Json();
		String select = "*";
		String from = "{ad_equipck_checkcontent}";
		String where = "where dr= 0 and  pk_classname  in  (select uuid from ad_equipck_classname where dr=0 and classname='" + equipName.trim() + "') and " + checkType + "=1";
		try {
			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, where);
			if (result.size() > 0) {
				returnStatus.setObj(result);
				returnStatus.setSuccess(true);
			} else {
				returnStatus.setMsg("数据库中没有此设备的点检项，请联系相关人员");
			}
		} catch (Exception e) {
			returnStatus.setMsg("err:查询数据出错");
		}
		return returnStatus;
	}

	/** 加载设备保养所有信息 */
	public Json EquipMaintain_Mounted(String equipPK, String checkType) {
		Json returnStatus = new Json();
		try {
			Map<String, Json> join = new HashMap<String, Json>();
			Json baseInfor = EquipManage_GetBaseInfor(equipPK);
			join.put("baseInfor", baseInfor);
			if (baseInfor.isSuccess()) {
				List<Map<String, Object>> m = (List<Map<String, Object>>) baseInfor.getObj();
				join.put("checkContent",
						EquipMaintain_GetCheckContent(m.get(0).get("equip_name").toString(), checkType));
			}
			returnStatus.setObj(join);
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setMsg("err:查询数据出错");
		}
		return returnStatus;
	}

	// 插入设备保养检查记录
	public Json EquipMaintain_submit(String submitJson) {
		Json returnStatus = new Json();
		String tableName = "ad_equipck_d";
		try {
			JSONObject Json = (JSONObject) JSON.parse(submitJson);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ckdatetime = dateformat.format(new Date());
			ckdatetime = "$to_date ('" + ckdatetime + "', 'YYYY-MM-DD HH24:MI:SS' )";
			Json.put("ckdatetime", ckdatetime);
			Json.put("pk_equipckcode", Long.toString(System.currentTimeMillis()));
			Json.put("pk_equipck", "$(select max(pk_equipck)+1 from ad_equipck_d)");
			Map<String, Object> callBackMap = Insert(tableName, Json);
			if ((boolean) callBackMap.get("success")) {
				returnStatus.setSuccess(true);
			} else {
				returnStatus.setMsg("err:插入出错！");
			}
		} catch (Exception e) {
			returnStatus.setMsg("err:插入出错！");
		}

		return returnStatus;
	}

	// 设备保养检查记录查询
	public Json EquipMaintain_Query(String equipPK) {
		Json returnStatus = new Json();

		/**
		 * select a.* , b.checkcontent ,b.uuid ,(select c.psnname from bd_psnbasdoc c where c.pk_psnbasdoc=a.pk_ckuser)as psnname,(select d.deptname from bd_deptdoc d where d.pk_deptdoc=a.pk_ckdept)as deptname from (select *from (select a.* from ad_equipck_d a where pk_equip='1002C11000000005A41E' ORDER BY pk_equipck DESC) where rownum=1 ) a left join ad_equipck_checkcontent b on instr(a.ckstatus,b.uuid)>0
		 * 
		 */
		String select = " a.* , b.checkcontent,(select c.psnname from bd_psnbasdoc c where c.pk_psnbasdoc=a.pk_ckuser)as psnname,(select d.deptname from bd_deptdoc d where d.pk_deptdoc=a.pk_ckdept)as deptname  ";
		String from = "(select *from (select a.* from {ad_equipck_d a} where pk_equip='"+equipPK+"' ORDER BY pk_equipck DESC) where rownum=1 ) a left join {ad_equipck_checkcontent b} on instr(a.ckstatus,b.uuid)>0   ";
		try {
			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, null);

			if (result.size() > 0) {
				List<Object> content = new ArrayList<Object>();
				for (Map<String, Object> e : result) {
					content.add(e.get("checkcontent"));
				}
				Map<String, Object> map = result.get(0);
				map.put("checkcontent", content);
				result.clear();
				result.add(map);
				returnStatus.setObj(result);
				returnStatus.setSuccess(true);
			} else {
				returnStatus.setMsg("此设备没有任何点检记录");
			}
		} catch (Exception e) {
			returnStatus.setMsg("err:查询数据出错");
		}
		return returnStatus;

	}

}
