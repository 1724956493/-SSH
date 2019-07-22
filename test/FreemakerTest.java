import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.google.gson.Gson;
import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdRoleMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdqualitybillDaoImpl;
import com.nts.teststruts.dao.impl.BaseQueryDaoImpl;
import com.nts.teststruts.dao.impl.BdJobbasfilDaoImpl;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdRolemenu;
import com.nts.teststruts.service.impl.TreeNode;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.DateUtil;
import com.nts.teststruts.util.FieldChinese;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jxl.write.WriteException;

public class FreemakerTest {
	
	private Configuration cfg = new Configuration();  

	
	@Test
	public void print() throws SQLException, IOException, TemplateException 
	{  
		String uploadPath = "d:\\fktemp" ; //ServletActionContext.getServletContext().getRealPath("/fktemp");
		
		//设置模板获取路径
		cfg.setDirectoryForTemplateLoading(new File(uploadPath));
		// 指定模板如何检索数据模型，这是一个高级的主题了…
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		
		
		//获取模板
		Template temp = cfg.getTemplate("test.ftl");
		temp.setEncoding("UTF-8");  
		
		Map root = new HashMap();
		root.put("user", "Big Joe");
		Map latest = new HashMap();
		root.put("latestProduct", latest);
		latest.put("url", "算不算");
		latest.put("name", "green mouse");
		
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:\\fktemp\\1.txt"), "UTF-8"));
		temp.process(root, out);
		out.flush();
		
		ComUtil.printpdf("d:\\fktemp\\1.txt", "d:\\fktemp\\iText_3.pdf");
	}
	
	@Test
	public void  getWeekOfYear() { 
			 Date begindate = DateUtil.strToDate("2017-06-02");		
			 System.out.println(begindate.toString());
	}

	@Test
	public void billcode() throws IllegalArgumentException, IllegalAccessException, WriteException, IOException{
		
		StringBuilder sb = new StringBuilder();	
		sb.append("select l.billcode,f.jobname,o.deptname deptname1,p.deptname deptname2,l.totalmulct,decode(l.totalmulct,0,'未处罚','已处罚'),c.psnname,d.deptname deptname3,b.mulct from adqualitybill l ");
		sb.append("left join adqualitybill_sub b on l.uuid = b.huuid ");
		sb.append("left join bd_psnbasdoc c on c.pk_psnbasdoc = b.psnname ");
		sb.append("left join bd_deptdoc d on d.pk_deptdoc = b.dept ");
		sb.append("left join bd_deptdoc o on o.pk_deptdoc = l.dept ");
		sb.append("left join bd_deptdoc p on p.pk_deptdoc = l.wbdept ");
		sb.append("left join bd_jobbasfil f on f.pk_jobbasfil = l.project ");
		sb.append("where l.type='ZLGYX' and l.appstatus = '1' order by l.billcode ");
		
		List<Object[]>  aa2    = new BaseQueryDaoImpl().objBySql(sb.toString());
		
//		new ComUtil().excelSavelistobj(aa2,"a1234.xls");
		System.out.println(aa2);
		
	}
	
	@Test
	public void testlistcontain(){
		Object object = new Object();
		 
	}
}
