package com.nts.teststruts.dao.DingUserDaoImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.baidu.aip.ocr.AipOcr;
//import com.baidu.ai.service.OcrService;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.DateUtil;

import lp.util.HibernateBySQL;
import lp.util.Json;
import sun.misc.BASE64Decoder;

public class CommDaoImpl extends HibernateBySQL {

	/** 根据前台获取的权限CODE,获取用户电话,根据用户电话获取权限 */
	public Json getDingUser(String code, String dingID) {
		Json retunStatus = new Json();
		String userPhone = "";
		try {
			String accessToken = AuthHelper.getAccessToken();
			CorpUserDetail user = UserHelper.getUser(accessToken,
					UserHelper.getUserInfo(accessToken, code).getUserid());
			userPhone = user.getMobile();
			if (userPhone.contains("15951152853"))
				userPhone = "15896028253";

			// 通过手机号 多表联合 查询出对应用户的权限
			retunStatus = getPowerTreeByPhone(userPhone, dingID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			retunStatus.setMsg("error-getDingUser");
		}
		return retunStatus;
	}

	/** @ 通过手机号获取钉钉权限，以Json树的格式返回人员信息和权限菜单 */
	public Json getPowerTreeByPhone(String userPh, String dingID) {
		Json retunStatus = new Json();
		Map<String, Object> userInfoAndMenu = new HashMap<String, Object>();
		try {
			// 查出人员信息 ad_ykt 一卡通号
			String select = "c.pk_psnbasdoc,c.mobile,a.cuserid ,a.user_name,a.user_code,a.user_password ,d.pk_psndoc,d.groupdef10, d.pk_deptdoc as pk_deptdoc ,e.deptname ,$(select  m.def9 from ad_psndoc  m , ad_psnbasdoc n where m.pk_psnbasdoc = n.pk_psnbasdoc and   n.id = c.id) as ad_ykt";
			String from = " {sm_user a} ,{sm_userandclerk b}, {bd_psnbasdoc c},{bd_psndoc d},{bd_deptdoc e} ";
			String where = "where a.cuserid=b.userid and c.pk_psnbasdoc=b.pk_psndoc and d.pk_psnbasdoc=c.pk_psnbasdoc ";
			where += " and  instr(trim(a.user_name),trim(c.psnname))>0  and   d.pk_deptdoc=e.pk_deptdoc  and d.dr !=1 and d.psnclscope in (0,5) and  c.mobile='"
					+ userPh + "'";
			List<Map<String, Object>> userInfo = QueryTablesSample(select, from, where);

			if (userInfo.size() == 0) {
				retunStatus.setMsg("error:系统没有用户信息！");
				return retunStatus;// 如果返回为空则报错
			}
			// 存入人员信息
			userInfoAndMenu.put("userInfo", userInfo.get(0));
			// 插入或更新钉钉id
			String psnbasdoc = userInfo.get(0).get("pk_psnbasdoc").toString();
			String psndoc = userInfo.get(0).get("pk_psndoc").toString();
			String user_name = userInfo.get(0).get("user_name").toString();
			String tableName = "ad_loadlogs";
			JSONObject value = new JSONObject();
			value.put("pk_psnbasdoc", psnbasdoc);
			value.put("pk_psndoc", psndoc);
			value.put("ding_corpid", Env.getCorpId());
			value.put("ding_id", dingID);
			value.put("user_name", user_name);
			value.put("ts", DateUtil.getStringDate());
			Insert(tableName, value);
			// 查出权限
			select = "distinct( m.uuid_menu) as uuid_menu,m.type,m.parentid,m.menuname,m.menulevel,m.menucode,m.create_time,m.action ";
			from = "  {ad_menu m} ,{ad_rolemenu b} ,{ad_userrole c} ,{sm_user d },{ad_role f}";
			where = " where m.dr=0 and b.dr=0 and c.dr=0  and f.dr=0 and c.uuid_role= f.uuid_role and b.uuid_role= f.uuid_role and m.uuid_menu=b.uuid_menu and b.uuid_role=c.uuid_role and c.cuserid=d.cuserid and  m.type='0'and  d.cuserid='"
					+ userInfo.get(0).get("cuserid") + "' order by m.create_time";
			List<Map<String, Object>> menus = QueryTablesSample(select, from, where);
			if (menus.size() == 0) {
				retunStatus.setMsg("error:用户没有权限");
				retunStatus.setObj(userInfoAndMenu);
				return retunStatus;// 如果返回为空则报错
			}
			// 格式化menus返回menus树
			List<Map<String, Object>> menusTree = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> content : menus) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (content.get("parentid").equals("634988a9e48843418a223249cec3bc44")) {
					map = content;
					List<Map<String, Object>> menusTreeChild = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> content2 : menus) {
						if (content2.get("parentid").equals(map.get("uuid_menu"))) {
							menusTreeChild.add(content2);
						}
					}
					map.put("child", menusTreeChild);
					menusTree.add(content);
				}
			}
			userInfoAndMenu.put("menus", menusTree);
			retunStatus.setObj(userInfoAndMenu);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			retunStatus.setMsg("error:InGetPowerByPhone");
		}

		return retunStatus;
	}

	/** 根据手机号获取人员档案表里的数据 */
	public Json getPersonInforByPhone(String userPhone) {
		Json retunStatus = new Json();
		try {
			String select = " a.pk_psndoc,a.pk_psnbasdoc,a.psnname,a.pk_deptdoc,a.groupdef10,b.id,b.mobile";
			String from = "{bd_psndoc a} ,{bd_psnbasdoc b}";
			String where = "where b.mobile='" + userPhone
					+ "' and a.pk_psnbasdoc= b.pk_psnbasdoc and a.dr !=1 and psnclscope in (0,5) ";

			List<Map<String, Object>> person = QueryTablesSample(select, from, where);
			if (person.size() == 0) {
				retunStatus.setMsg("查无此人");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(person);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			retunStatus.setMsg("error-getPersonInforByPhone");
		}
		return retunStatus;
	}

	/**
	 * @车牌图片-> 返回图片中的车牌号
	 *
	 */ 
	public Json getCarInfoByPic(String carPic,String appid,String appkey,String secretkey) {
		Json retunStatus = new Json(); 
		HashMap<String, String> options = new HashMap<String, String>();
		// options.put("multi_detect", "true");
		AipOcr client = new AipOcr(appid, appkey, secretkey);
		byte[] b;
		try {
			b = new BASE64Decoder().decodeBuffer(carPic);
			String getResult = client.plateLicense(b, options).toString(2);
			String isErro = JSON.parseObject(getResult).getString("error_code");
			if (isErro == null) {
				String result = JSON.parseObject(getResult).getString("words_result");
				String carId = JSON.parseObject(result).getString("number");
				retunStatus.setObj(getCarInfoById(carId));
				retunStatus.setSuccess(true);
				retunStatus.setMsg(carId);
			} else {
				retunStatus.setSuccess(false);
				retunStatus.setMsg("未检测到图片中识别目标，请确保图片中包含车牌");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return retunStatus;
	}

	/**
	 * @车牌-> 更具输入的车牌号查询
	 *
	 */
	public Json getCarInfoById(String carId) {
		// carPic = compressByBase64String(carPic);
		Json retunStatus = new Json();
		String select = "*";
		String from = " {ad_carinfo}";
		String where = " where carid ='" + carId.toUpperCase() + "'";
		String getResult = "";
		try {
			List<Map<String, Object>> result = HibernateBySQL.QueryTablesSample(select, from, where);
			if (result.size() > 0) {
				retunStatus.setObj(result);
				retunStatus.setSuccess(true);
			} else {
				retunStatus.setMsg("未查到此车牌的数据");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			retunStatus.setSuccess(false);
		}
		return retunStatus;
	}

	/**
	 * @ 获取数据库中主管领导，返回Json
	 *
	 */
	public Json getLeaders() {
		Json retunStatus = new Json();
		try {
			String select = "uuid,psnname";
			String from = "{ad_psntypelist}";
			String where = " where  dr =0 and psntype = '主管领导'";
			List<Map<String, Object>> leaders = QueryTablesSample(select, from, where);
			if (leaders.size() == 0) {
				retunStatus.setMsg("没有找到主管领导！");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(leaders);
			retunStatus.setSuccess(true);

		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:getLeaders");
		}
		return retunStatus;
	}

	/**
	 * @保存图片，返回图片名称
	 */
	public String saveImage(String imgStrArray) {
		String saveName = "";
		JSONArray Json = (JSONArray) JSON.parse(imgStrArray);
		for (int i = 0; i < Json.size(); i++) {
			String imgStr = (String) Json.get(i);
			String imgname = "";
			if (imgStr != null && imgStr.length() > 1) {
				String basepath = ServletActionContext.getServletContext().getRealPath("/") + "upload\\";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				imgname += sdf.format(new Date());
				File file = new File(basepath);
				if (!file.exists()) {
					file.mkdir();
				}
				String imgPath = basepath + imgname;
				File fileimg = new File(imgPath);
				if (!fileimg.exists()) {
					imgPath += ".jpg";
					imgname += ".jpg";
				} else {
					imgPath += "a.jpg";
					imgname += "a.jpg";
				}
				boolean flag = new DBUtil().string2Image(imgStr.split(",")[1], imgPath);
				try {
					Thread.currentThread().sleep(2000);// 毫秒

				} catch (Exception e) {
				}
				if (flag)
					saveName += imgname + ",";
			}
		}
		return saveName.length() > 0 ? saveName.substring(0, saveName.length() - 1) : "";
	}

	/**
	 * 从bd_deptdoc中获取部门名称 1002，老区不要
	 */
	public Json getDeptInfor() {
		Json retunStatus = new Json();
		try {
			String select = "*";
			String from = "{bd_deptdoc}";
			String where = " where pk_corp='1002' and deptname  not like '%老区%'";
			List<Map<String, Object>> deptInfor = QueryTablesSample(select, from, where);
			if (deptInfor.size() == 0) {
				retunStatus.setMsg("没有找到部门信息！");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(deptInfor);
			retunStatus.setSuccess(true);

		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:getDeptInfor");
		}
		return retunStatus;
		// 抓一卡通数据库里的部门信息
		/*	Json retunStatus = new Json();
			try {
				Connection conn = sqlDBUtil.getConnection();
				if (conn == null) {
					retunStatus.setMsg("erro:一卡通数据库连接出错");
					return retunStatus;
				}
				String sql = "select bmbh,bmmc as deptname from department order by bmbh";
				PreparedStatement ps;
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				List<Map<String, Object>> results = ResultSetToList(rs);
				if (results.size() == 0) {
					retunStatus.setMsg("erro:数据库中没有任何记录");
					retunStatus.setSuccess(false);
				} else {
					retunStatus.setObj(results);
					retunStatus.setSuccess(true);
				}
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				retunStatus.setMsg("erro:一卡通数据库连接出错");
			}
			return retunStatus;*/
	}

	/** 根据部门名称或人员姓名查询 人员列表 */
	public Json getPersonListByNameOrDeptName(String name, String deptName) {

		Json retunStatus = new Json();
		try {
			String select = " a.def9 as bh ,b.psnname as xm,c.deptname as bm,(select deptname from bd_deptdoc where pk_deptdoc=a.groupdef1)as usedeptname";
			String from = "{ ad_psndoc a},{ad_psnbasdoc b},{bd_deptdoc c}";
			String where = "where a.psnclscope=0 and a.pk_psnbasdoc=b.pk_psnbasdoc and c.pk_deptdoc=a.pk_deptdoc and c.deptname like '%"
					+ deptName + "%' and b.psnname like '%" + name
					+ "%' and c.pk_corp='1002' and c.deptname  not like '%老区%' and a.def9 is not null   order by c.deptname";
			List<Map<String, Object>> deptInfor = QueryTablesSample(select, from, where);
			if (deptInfor.size() == 0) {
				retunStatus.setMsg("未能查找到匹配的员工信息！");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(deptInfor);
			retunStatus.setSuccess(true);

		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:getPersonListByNameOrDeptName");
		}
		return retunStatus;

		/*Json retunStatus = new Json();
		try {
			Connection conn = sqlDBUtil.getConnection();
			if (conn == null) {
				retunStatus.setMsg("erro:一卡通数据库连接出错");
				return retunStatus;
			}
			String sql = "select xm,bh,bm,(SELECT bmmc FROM department WHERE department.bmbh = employee.lb) bmmc from employee where xm like ? and jn!='离职' and bm like ?";
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + deptName + "%");
			ResultSet rs = ps.executeQuery();
			List<Map<String, Object>> results = ResultSetToList(rs);
			if (results.size() == 0) {
				retunStatus.setMsg("erro:数据库中没有任何记录");
				retunStatus.setSuccess(false);
			} else {
				retunStatus.setObj(results);
				retunStatus.setSuccess(true);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			retunStatus.setMsg("erro:一卡通数据库连接出错");
		}
		return retunStatus;*/
	}

	/**
	 * @根据项目类型查询项目号 @数据格式{"msg":"", "obj":[{"jobcode":"0103601","jobname":"1-36500T散货轮","pk_jobbasfil":"0001AA10000000049UIO"}], "success":true}
	 * 
	 */
	public Json getProjects(String projectType) {

		Json retunStatus = new Json();
		try {
			String select = "pk_jobbasfil,jobcode,jobname";
			String from = "{bd_Jobbasfil}";
			String where = " where pk_jobtype ='" + projectType + "' and def1 = '0' order by jobcode";
			List<Map<String, Object>> project = QueryTablesSample(select, from, where);
			if (project.size() == 0) {
				retunStatus.setMsg("没有此类型的项目号");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(project);
			retunStatus.setSuccess(true);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return retunStatus;

	}

	/**
	 * @通过人员档案里的一卡通查询人员信息
	 */
	public Json getPersonInfoByYKT(String onecode) {
		Json retunStatus = new Json();
		try {
			/*String select = "b.mobile,b.pk_psnbasdoc ,a.psnname as name,a.pk_deptdoc as empdept ,a.groupdef6 as empuserdept, a.pk_psndoc as pk_psndoc,a.groupdef10 as ykt ,d.deptname as dept,b.birthdate as age,b.sex as sex,b.id as ic_id,e.deptname as usedept";
			String from = " {bd_psndoc a } left join  {bd_psnbasdoc b } on b.pk_psnbasdoc=a.pk_psnbasdoc left join  "
					+ "{bd_deptdoc d}  on d.pk_deptdoc=a.pk_deptdoc left join "
					+ " {bd_deptdoc e} on a.groupdef6=e.pk_deptdoc";
			String where = " where  a.dr !=1 and d.pk_deptdoc=a.pk_deptdoc and b.pk_psnbasdoc=a.pk_psnbasdoc and a.groupdef10 ='"
					+ onecode + "'";*/
			String select = "b.pk_psnbasdoc, b.psnname as name, b.id as ic_id,  b.sex as sex,  b.birthdate as age,  b.province,";
			select += "  b.city,  b.addr, b.mobile, b.basgroupdef1 as gs_baoxian, a.pk_psndoc, a.pk_deptdoc as empdept ,";
			select += " a.groupdef1 as empuserdept, a.groupdef2 as  work_slot,  a.groupdef3 as  groupname,";
			select += "a.groupdef6 as  position,  a.groupdef4 as  job_attr, a.groupdef10 as  job_status,";
			select += "a.groupdef5 as   baoxian_code, a.groupdef7 as   baoxian_cod_bac,  a.def9 as ykt ,";
			select += " (select deptname from bd_deptdoc where  pk_deptdoc =a.pk_deptdoc )as dept, ";
			select += "(select deptname from bd_deptdoc where  pk_deptdoc =a.groupdef1 ) as usedept ";
			String from = " {ad_psndoc a }    left join  {ad_psnbasdoc b }    on b.pk_psnbasdoc=a.pk_psnbasdoc ";
			String where = " where   a.dr !=1  and  a.psnclscope=0   and a.def9 ='" + onecode + "'";

			List<Map<String, Object>> personInfor = QueryTablesSample(select, from, where);
			if (personInfor.size() == 0) {
				retunStatus.setMsg("NC系统中没有该一卡通号的信息！");
				return retunStatus;// 如果返回为空则报错
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String year = sdf.format(date);
			String age = personInfor.get(0).get("age").toString();
			if (age.length() > 4) {
				age = (Integer.parseInt(year) - Integer.parseInt(age.substring(0, 4))) + "";
			} else {
				age = "生日信息为空，请补全";
			}
			personInfor.get(0).put("age", age);

			String filePath = "E:/upload/hrimage/" + personInfor.get(0).get("ic_id").toString();
			String ImagString = DBUtil.Image2String(filePath);
			if (ImagString != null)
				personInfor.get(0).put("headImg", "data:image/png;base64," + ImagString);
			else
				personInfor.get(0).put("headImg", "images/1.png");
			retunStatus.setObj(personInfor);
			retunStatus.setSuccess(true);

		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:comm_getPersonInfoByYKT");
		}
		return retunStatus;
	}

	// 根据人员pk获取 证书数据
	public Json getCertificate(String pk_psndoc) {
		Json retunStatus = new Json();
		try {
			/*	String select = "t.pk_psndoc_sub as certificateID ,t.begindate ,t.enddate, t.groupdef1 as certificatename,";
				select += "(select y.docname from bd_defdoc y  where y.pk_defdoc=t.groupdef2  ) as certificateType";
				String from = "{hi_psndoc_grpdef4 t}";
				String where = " where t.pk_psndoc ='" + pk_psndoc + "'";
				List<Map<String, Object>> certificate4 = QueryTablesSample(select, from, where);
			
				select = "t.pk_psndoc_sub as certificateID ,t.begindate ,t.enddate, t.groupdef1 as certificatename,";
				select += "(select y.docname from bd_defdoc y  where y.pk_defdoc=t.groupdef3  ) as certificateType";
				from = "{hi_psndoc_grpdef2 t}";
				where = " where t.pk_psndoc ='" + pk_psndoc + "'";*/

			String select = "a.certcode, a.begindate,a.reviewdate,a.enddate,b.certtype,b.certname,a.def2,a.def3,a.def4 ";
			String from = " {ad_hr_cert a} left join  {ad_hr_cert_category b}   on  a.category=b.uuid ";
			String where = "where a.status=0 and a.psndocpk = (select c.pk_psnbasdoc from ad_psndoc c where  c.pk_psndoc='"
					+ pk_psndoc + "') ";
			List<Map<String, Object>> certificate = QueryTablesSample(select, from, where);
			if (certificate.size() == 0) {
				retunStatus.setMsg("未获取到证书");
				return retunStatus;// 如果返回为空则报错
			}
			// certificate2.addAll(certificate4);
			retunStatus.setObj(certificate);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			retunStatus.setMsg("error:comm_getCertificate");
		}
		return retunStatus;

	}

	// 根据人员基本档案PK获取奖惩查询
	public Json getBonous(String pk_psnbasdoc, String beg, String end) {
		Json retunStatus = new Json();
		try {
			String select = "h.billcode,h.billhead,h.project,h.yiju,h.createdate,b.mulct,b.reward,h.type,h.appstatus ,d.jobname";
			String from = " {ADQUALITYBILL_SUB b} left join {ADQUALITYBILL h} on h.uuid = b.huuid   left join {bd_jobbasfil d} on d.pk_jobbasfil=h.project ";
			String where = " where h.appstatus != '0' and b.psnname ='" + pk_psnbasdoc + "'  and h.createdate >='" + beg
					+ "' and h.createdate <='" + end + "' order by h.type";
			List<Map<String, Object>> result = QueryTablesSample(select, from, where);
			retunStatus.setObj(result);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			retunStatus.setMsg("error:comm_getBonous");
		}
		return retunStatus;

	}
	// --------------------------通用方法------------------------------------

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

	// 查询数据
	public Json getData(String select, String from, String where) {
		Json returnStatus = new Json();
		try {
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			returnStatus.setObj(data);
			returnStatus.setSuccess(true);
		} catch (Exception e) {
			returnStatus.setMsg(e.getMessage());
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
