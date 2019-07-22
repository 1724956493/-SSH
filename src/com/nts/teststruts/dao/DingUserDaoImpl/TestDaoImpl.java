package com.nts.teststruts.dao.DingUserDaoImpl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.baidu.ai.service.AipImageClassifyClient;
//import com.baidu.ai.service.OcrService;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.nts.teststruts.model.YktDept;
import com.nts.teststruts.model.YktUser;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.sqlDBUtil;

import lp.util.GetPostDataFromHTML;
import lp.util.HibernateBySQL;
import lp.util.Json;

public class TestDaoImpl extends HibernateBySQL {
	public Json testMethod(String select, String from, String where) {
		Json retunStatus = new Json();
 
		try {
			/*String select="a.uuid,a.classname,a.checkcontent,a.dailycheck,a.deptcheck01,a.deptcheck02,a.functioncheck,a.ts";
			String from="{ad_equipck_checkcontent a}";
			String where="order by classname";*/
			/*	select = "a.*,rn,maxrows";
				from = "(select a.*,rownum rn from "
						+ "(select a.* ,(select count(*) from  ad_psndoc_rp2_bak) maxrows from {ad_psndoc_rp2_bak a}) a "
						+ "where rownum < 11) a";
				where = " where rn>5"*/
			/*select a.* ,
			 (select count(*) from  ad_psndoc_rp2_bak) maxrows, 
			 (select b.psnname from  bd_psndoc b where b.pk_psndoc=a.pk_psndoc) workname,
			   (select t.deptname from  bd_deptdoc t where t.pk_deptdoc=a.empdept) deptname ,
			   (select t.deptname from  bd_deptdoc t where t.pk_deptdoc=a.empuserdept) usedeptname,
			   (select l.psnname from ad_psntypelist l where l.uuid=a.empleader) empleadername,
			   (select j.jobname from bd_Jobbasfil j where j.pk_jobbasfil=a.project) projectname,
			   (select b.psnname from  bd_psndoc b where b.pk_psndoc=a.operate) operatekname
			    from ad_psndoc_rp2_bak a order by create_time desc*/
			List<Map<String, Object>> data = QueryTablesSample(select, from, where);
			if (data.size() == 0) {
				retunStatus.setMsg("error:系统没有数据！");
				return retunStatus;// 如果返回为空则报错
			}
			retunStatus.setObj(data);
			retunStatus.setSuccess(true);
		} catch (Exception e) {
			retunStatus.setMsg("error:testMethod");
		}
		return retunStatus;
	}

}
