package com.nts.teststruts.dao.DingUserDaoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lp.util.HibernateBySQL;
import lp.util.Json;

public class PersonManageDaoImpl extends HibernateBySQL {
	CommDaoImpl commMethods = new CommDaoImpl();

	/**
	 * @ 通过页面类型,控制返回不同部门的违章明细，以Json形式树展现
	 *
	 */
	public Json getRuleDetailsTree(int typeStatus) {
		// 类型0代表质量部 2代表设备部能源巡检 1安环部巡检
		String[] type = { "8a87819e578fcbab015792283d57000c", "8a87819e579d9de20157aea8bd7d02c5",
				"8a87819e579d9de20157aeb5014302c9" };
		Json retunStatus = new Json();
		try {
			String select = "l.uuid,l.wzlistname ,t.wzname";
			String from = "{ad_wztype t} ,{ad_wzlist l}";
			String where = "where l.wztype =t.uuid and t.dr=0 and l.dr=0 and t.wzparent='" + type[typeStatus]
					+ "' order by t.create_time";
			List<Map<String, Object>> ruleDetail = QueryTablesSample(select, from, where);
			List<Map<String, Object>> ruleDetail2 = ruleDetail;
			if (ruleDetail.size() == 0) {
				retunStatus.setMsg("没有找到违规类别！");
				return retunStatus;// 如果返回为空则报错
			}

			// 格式化结果 并树形JSON
			List<Map<String, Object>> ruleDetailTree = new ArrayList<Map<String, Object>>();

			Set<String> wzName = new HashSet<String>();
			for (Map<String, Object> content : ruleDetail) {
				wzName.add((String) content.get("wzname"));
			}
			for (String value : wzName) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("wzname", value);
				List<Map<String, Object>> menusTreeChild = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> content : ruleDetail) {
					if (content.get("wzname").equals(value)) {
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("uuid", content.get("uuid"));
						map2.put("wzlistname", content.get("wzlistname"));
						menusTreeChild.add(map2);
					}
				}
				map.put("child", menusTreeChild);
				ruleDetailTree.add(map);
			}
			retunStatus.setObj(ruleDetailTree);
			retunStatus.setSuccess(true);

		} catch (Exception e) {
			retunStatus.setMsg("error:PersonManager_getRuleDetailsTree");
		}
		return retunStatus;
	}

	/** @返回人员管理所有需要在渲染前加载的数据 */
	public Json getMountedData(int typeStatus) {
		Json retunStatus = new Json();
		try {
			Map<String, Json> join = new HashMap<String, Json>();
			join.put("ruleTree", getRuleDetailsTree(typeStatus));
			join.put("leaders", commMethods.getLeaders());
			if (typeStatus == 0) {
				// projectType当前用的是 0001C110000000000DF7
				String projectType = "0001C110000000000DF7";
				join.put("projects", commMethods.getProjects(projectType));
			}
			retunStatus.setObj(join);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:PersonManager_getMountedData");
		}
		return retunStatus;
	}

	/**
	 * @设备能源巡检节点提交的数据
	 */
	public Json submit(String submitJson) {
		Json retunStatus = new Json();
		String tableName = "ad_psndoc_rp2";

		try {
			JSONObject Json = (JSONObject) JSON.parse(submitJson);
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowdate = dateformat.format(new Date());
			nowdate = "$to_date ('" + nowdate + "', 'YYYY-MM-DD HH24:MI:SS' )";
			Json.put("create_time", nowdate);
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String image = commMethods.saveImage(Json.getString("base64Image"));
			if (image.length() > 0) {
				Json.put("image", image);
			}
			Json.put("uuid", uuid);
			Map<String, Object> callBackMap = Insert(tableName, Json);
			if ((boolean) callBackMap.get("success")) {
				retunStatus.setSuccess(true);
			} else {
				retunStatus.setMsg("err:插入出错！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("err:插入出错！");
		}
		return retunStatus;
	}

	// 违章整改更新
	public Json reformUpdate(String submitJson) {
		Json retunStatus = new Json();
		String tableName = "ad_psndoc_rp2";
		try {
			JSONObject SetCondition = (JSONObject) JSON.parse(submitJson);
			String uuid = SetCondition.getString("uuid");
			SetCondition.remove("uuid");
			String reformimage = commMethods.saveImage(SetCondition.getString("reformimage"));
			if (reformimage.length() > 0) {
				SetCondition.put("reformimage", reformimage);
			}
			String where = "where uuid='" + uuid + "'";

			Map<String, Object> callBackMap = Update(tableName, SetCondition, where);
			if ((boolean) callBackMap.get("success")) {
				retunStatus.setSuccess(true);
			} else {
				retunStatus.setMsg("err:插入出错！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("err:插入出错！");
		}
		return retunStatus;
	}

	// 以上传人员pk或所在部门pk（对应主管领导pk）查询违章数据
	public Json getBreakInforByOperator(String pkpsndoc, String beginTime, String endTime, String queryStatus,
			String breakSataus, String project) {

		Json retunStatus = new Json();
		String select = "t.*,(select wzlistname from ad_wzlist where uuid=t.bonus) as wzlistname";
		String from = "{ad_psndoc_rp2 t}";
		String where = "";
		int status = Integer.parseInt(breakSataus);

		if (queryStatus.equals("person")) {
			where = "where    t.operate='" + pkpsndoc + "' ";
		} else {
			where = "where   t.empleader in (select uuid from ad_psntypelist  p where  dr =0 and psntype = '主管领导'  and p.parentdeptpk='"
					+ pkpsndoc + "') ";
		}
		where += " and t.status ='违规' and to_char(t.create_time,'yyyy-mm-dd') >= '" + beginTime
				+ "' and  to_char(t.create_time,'yyyy-mm-dd')<= '" + endTime + "'";
		if (status != -1) {
			where += " and type= " + status;
		}
		if(project!=null){
			where += " and project= '" + project+"'";
		}
		where += " order by create_time desc";

		try {

			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, where);
			if (result.size() > 0) {
				retunStatus.setObj(result);
				retunStatus.setSuccess(true);
			} else {
				retunStatus.setMsg("未查询到任何记录");
			}
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("err:查询数据出错");
		}
		return retunStatus;

	}

	// 以人员pk查询违章数据
	public Json getBreakInforByPkpsndoc(String pkpsndoc, String pageType) {

		Json retunStatus = new Json();
		String select = "t.*,(select wzlistname from ad_wzlist where uuid=t.bonus) as wzlistname";
		String from = "{ad_psndoc_rp2 t}";
		String where = "where t.status ='违规' and t.pk_psndoc='" + pkpsndoc + "' ";
		where += " and t.type='" + pageType + "' ";
		where += " order by create_time desc";
		try {

			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, where);
			if (result.size() > 0) {
				retunStatus.setObj(result);
				retunStatus.setSuccess(true);
			} else {
				retunStatus.setMsg("未查询到任何记录");
			}
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("err:查询数据出错");
		}
		return retunStatus;
	}

	public Json getBreakInforByPkpsndoc(String pkpsndoc, String beg, String end) {

		Json retunStatus = new Json();
		String select = "t.*,(select wzlistname from ad_wzlist where uuid=t.bonus) as wzlistname";
		String from = "{ad_psndoc_rp2 t}";
		String where = "where t.status ='违规' and t.pk_psndoc='" + pkpsndoc + "' ";
		where += "   and  to_char(t.create_time ,'yyyy-mm-dd')>='" + beg
				+ "'  and   to_char(t.create_time ,'yyyy-mm-dd')<='" + end + "'  order by create_time desc";
		try {

			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, where);
			if (result.size() > 0) {
				retunStatus.setObj(result);
				retunStatus.setSuccess(true);
			} else {
				retunStatus.setMsg("未查询到任何记录");
			}
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("err:查询数据出错");
		}
		return retunStatus;
	}

	/** 人员信息综合查询 */
	public Json getPersonIntegratedInfor(String onecode, String beg, String end) {
		Json retunStatus = new Json();
		try {
			Map<String, Json> join = new HashMap<String, Json>();
			Json PersonBaseInfor = commMethods.getPersonInfoByYKT(onecode);
			join.put("PersonBaseInfor", PersonBaseInfor);
			if (PersonBaseInfor.isSuccess()) {
				List<Map<String, Object>> PersonBaseInforList = (List<Map<String, Object>>) PersonBaseInfor.getObj();
				String pk_psndoc = PersonBaseInforList.get(0).get("pk_psndoc").toString();
				join.put("Certificate", commMethods.getCertificate(pk_psndoc));
				join.put("BreakInfor", getBreakInforByPkpsndoc(pk_psndoc, beg, end));
			}
			retunStatus.setObj(join);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:PersonManager_getPersonIntegratedInfor");
		}
		return retunStatus;
	}
}
