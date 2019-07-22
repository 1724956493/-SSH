

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.lang3.SystemUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.nts.teststruts.dao.impl.AdEquipckDaoImpl;
import com.nts.teststruts.dao.impl.AdEquipreckDaoImpl;
import com.nts.teststruts.dao.impl.AdLogDaoImpl;
import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdPsndocRp2DaoImpl;
import com.nts.teststruts.dao.impl.AdPsndocRpDaoImpl;
import com.nts.teststruts.dao.impl.BdCorpDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.BdPsndocDaoImpl;
import com.nts.teststruts.dao.impl.CSSDaoImpl;
import com.nts.teststruts.dao.impl.EmployeeDaoImpl;
import com.nts.teststruts.dao.impl.IcSapplyHDaoImpl;
import com.nts.teststruts.dao.impl.PamEquipDaoImpl;
import com.nts.teststruts.dao.impl.PamLocationDaoImpl;
import com.nts.teststruts.dao.impl.SmUserDaoImpl;
import com.nts.teststruts.dao.impl.SmUserandclerkDaoImpl;
import com.nts.teststruts.dao.impl.YktDeptDaoImpl;
import com.nts.teststruts.dao.impl.YktUserDaoImpl;
import com.nts.teststruts.dao.impl.YktXfReportDaoImpl;
import com.nts.teststruts.model.AdCarinfo;
import com.nts.teststruts.model.AdEquipck;
import com.nts.teststruts.model.AdEquipreck;
import com.nts.teststruts.model.AdLog;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdPsndocRp;
import com.nts.teststruts.model.AdPsndocRp2;
import com.nts.teststruts.model.BdCorp;
import com.nts.teststruts.model.BdDeptdoc;
import com.nts.teststruts.model.BdPsndoc;
import com.nts.teststruts.model.Employee;
import com.nts.teststruts.model.Equipck;
import com.nts.teststruts.model.PamEquip;
import com.nts.teststruts.model.PamLocation;
import com.nts.teststruts.model.SmUser;
import com.nts.teststruts.model.YktDept;
import com.nts.teststruts.model.YktUser;
import com.nts.teststruts.service.impl.AdPsndocRpExtServ;
import com.nts.teststruts.service.impl.AdPsndocRpServ;
import com.nts.teststruts.service.impl.EmployeeInfo;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.service.impl.PamEquipServ;
import com.nts.teststruts.service.impl.SmUserServ;
import com.nts.teststruts.service.impl.TreeNode;
import com.nts.teststruts.service.impl.YktXfReport;
import com.nts.teststruts.util.ComUtil;
import com.nts.teststruts.util.CssSqlDBUtil;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.Encode;
import com.nts.teststruts.util.IDCard;
import com.nts.teststruts.util.sqlDBUtil;

import jxl.write.WriteException;
import net.sf.json.JSONArray;

public class HibernateORMappingTest {

	
	@Test
	public void testLoad() throws SQLException 
	{  
		//List<Map<String,Integer>> mapinfos = new PamEquipDaoImpl().groupByPk("1002C11000000000008B");
		List maps = new AdEquipckDaoImpl().groupByPk("1002C11000000000008B","2016-05-21","2016-06-21");
		Gson gson = new Gson();
	//	System.out.println(gson.toJson(maps));
		List<MapInfo> maps2 = new ArrayList<MapInfo>();
		 for (int i = 0; i < maps.size(); i++) {
             Object[] object = (Object[]) maps.get(i);
             if(object[2]==null){ object[2] =0;}
           //  System.out.println(object[0].toString()+","+object[1].toString()+","+object[2].toString());	
             MapInfo mapinfo = new   MapInfo();
             mapinfo.setS1(object[0].toString());
             int s2 = ((Number)object[1]).intValue();
             int s3= ((Number)object[2]).intValue();
             mapinfo.setS2(s2 - s3);
             mapinfo.setS3(s3);
             maps2.add(mapinfo);
	}
		 for(Object obj : maps2){
			 
			 Object[] obj2 = (Object[])obj;
		 System.out.println(obj2[0]);
		 }
	}
	
	@Test
	public void testprint() throws Exception
	{			
			String date = "2016-8-17";
			List<YktXfReport> xfroport1 =new  YktXfReportDaoImpl().getxfreport(date+" 10:30:00", date+" 11:00:00", "NCS%");
			List<YktXfReport> xfroport2 =new  YktXfReportDaoImpl().getxfreport(date+" 11:00:00", date+" 11:05:00", "NCS%");
			List<YktXfReport> xfroport3 =new  YktXfReportDaoImpl().getxfreport(date+" 11:05:00", date+" 11:15:00", "NCS%");
			List<YktXfReport> xfroport4 =new  YktXfReportDaoImpl().getxfreport(date+" 10:30:00", date+" 11:00:00", "NTS%");
			List<YktXfReport> xfroport5 =new  YktXfReportDaoImpl().getxfreport(date+" 11:00:00", date+" 11:05:00", "NTS%");
			List<YktXfReport> xfroport6 =new  YktXfReportDaoImpl().getxfreport(date+" 11:05:00", date+" 11:15:00", "NTS%");		
			List<YktDept> yktdepts = new YktDeptDaoImpl().getall();
			Map<String, YktXfReport> map0 = new HashMap<String, YktXfReport>();
			for(YktDept yktdept : yktdepts){
				YktXfReport yktxfreport = new YktXfReport(yktdept);
				map0.put(yktxfreport.getDeptcode(), yktxfreport);
			}
			for(YktXfReport xfroport : xfroport1 ){
				YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
				if(xfroportx!=null){
				xfroportx.setTime1Total(xfroport.getTime1Total());
				xfroportx.setStname("NCS");}
			}
			for(YktXfReport xfroport : xfroport2 ){
				YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
				if(xfroportx!=null){
				xfroportx.setTime2Total(xfroport.getTime1Total());
				xfroportx.setStname("NCS");}
			}
			for(YktXfReport xfroport : xfroport3 ){
				YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
				if(xfroportx!=null){
				xfroportx.setTime3Total(xfroport.getTime1Total());
				xfroportx.setStname("NCS");}
			}
			for(YktXfReport xfroport : xfroport4 ){
				YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
				if(xfroportx!=null){
				xfroportx.setTime4Total(xfroport.getTime1Total());
				xfroportx.setStname("NTS");}
			}
			for(YktXfReport xfroport : xfroport5 ){
				YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
				if(xfroportx!=null){
				xfroportx.setTime5Total(xfroport.getTime1Total());
				xfroportx.setStname("NTS");}
			}
			for(YktXfReport xfroport : xfroport6 ){
				YktXfReport xfroportx = map0.get(xfroport.getDeptcode());
				if(xfroportx!=null){
				xfroportx.setTime6Total(xfroport.getTime1Total());
				xfroportx.setStname("NTS");}
			}
			
			Iterator it = map0.keySet().iterator();
			List<YktXfReport> xfroport0 = new ArrayList<YktXfReport>();
			 while (it.hasNext()) {  
				 String key = it.next().toString(); 
				 xfroport0.add(map0.get(key));
			 }	
				new  ComUtil().excelSave(xfroport0,"d:/1.xls");
			 System.out.println(xfroport0);			
	}
	
	
	@Test
	public void testsaveExcle(){
		Connection conn = sqlDBUtil.getConnection();
		String sql = "select count(*) from yskq where bh ='117010066'";
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getInt(1));			
			}
			rs.close();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}  
	}

	@Test
	public  void testsave2(){
		 Session session = DBUtil.currentSession();
		 Transaction ts = session.beginTransaction();
		 AdPsndocRp c = new AdPsndocRp();
		 c.setType(1);
		 Criteria criteria = session.createCriteria(AdPsndocRp.class);
		 criteria.add(Example.create(c));
		 List<AdPsndocRp> cs = criteria.list();
		 ts.commit();
		 session.close();
			
	}
	
	@Test
	public void testdown() throws IOException  {
		String basepath = "d:\\upload\\xunjian\\";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String imgname = sdf.format(new Date());
				
		File file = new File(basepath);
		if(!file.exists()){
			file.mkdir(); 
		}
		
		
		String imgPath  = basepath + imgname ;
		File fileimg=new File(imgPath); 
		if(!fileimg.exists())
		{
			imgPath = imgPath +".jpg";
			imgname = imgname +".jpg";
		}else
		{
			imgPath = imgPath +"a.jpg";
			imgname = imgname +"a.jpg";
		}
		
		 OutputStream out = new FileOutputStream(imgPath);  
		 out.flush(); 
		 out.close();
	}
	
	@Test
	public void test123() throws SQLException{	
		String data = "8a87819e5953c46501595eef125d007b;8a87819e5953c46501595e3246e2003f;8a87819e5953c46501595e71b76c0058;8a87819e5953c46501595ea3b8c1006f;";
		String[] strArray = data.split(";");
			
		AdPsndocRp2DaoImpl adpsndocrp2impl = new AdPsndocRp2DaoImpl();
		String ral1="";
		for(int i=0;i<strArray.length;i++)
		{			
			AdPsndocRp2 aspsndocrp2 = adpsndocrp2impl.getbyUUID(strArray[i]);
			aspsndocrp2.setPaystatus(1);
			
			
		 	AdLog adlog = new AdLog();
	    	adlog.setAction("AdPsndocRPAction_getRP2payclose");
	    	adlog.setCknote("");
	    	adlog.setCreateTime(new Date());
	    	adlog.setModifycontent("Paystatus =1");
	    	adlog.setOperater("111");
	    	adlog.setTablename("ad_psndoc_rp2");
	    	adlog.setUuid(strArray[i]);
	    	ral1 = adpsndocrp2impl.update(aspsndocrp2);
	    	String ral = 	new AdLogDaoImpl().insert(adlog);
		}
		System.out.println(ral1);
	}
	
	@Test
	public void testpdfdown() throws Exception  {
		String command = "D:/Program Files/wkhtmltopdf/bin/wkhtmltopdf.exe " + "D:/Test/iText2.html" + " " + "D:/Test/iText_2.pdf"; ;  
        Runtime.getRuntime().exec(command);  
    //    TimeUnit.SECONDS.sleep(3);  
	}
	
	@Test
	public void test222down() throws Exception  {
		
		AdPsndocRp2 adpsn2= new AdPsndocRp2DaoImpl().getbyUUID("8a87819e5966e5ab015a69bba58d0509");
		System.out.println(adpsn2.toString());
	}
	
	

		
}
