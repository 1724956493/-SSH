package com.nts.teststruts.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DBUtil {
	
	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;
	public static final ThreadLocal session = new ThreadLocal();
	
	static {
		        try {
		        	Configuration config = new Configuration().configure();
		    		serviceRegistry = new StandardServiceRegistryBuilder()
		    				.applySettings(config.getProperties()).build();
		    		sessionFactory = config
		    				.buildSessionFactory(serviceRegistry);
		         } catch (Throwable ex) {
		             ex.printStackTrace();
		             throw new ExceptionInInitializerError(ex);
		         }
		    }
	
	 public static Session currentSession() throws HibernateException {
		          Session s = (Session) session.get();
		         if (s == null || !s.isOpen()) {
		              s = sessionFactory.openSession();
		              session.set(s);
		          }
		          return s;
		      }
	 
	 public static void closeSession() throws HibernateException {
		          Session s = (Session) session.get();
		          session.set(null);
		          if (s != null)
		              s.close();
		     }
	 
	 public SessionFactory getSessionFactory() {
		          return sessionFactory;
		      }
	 
	 
	 public boolean string2Image(String imgStr, String imgFilePath) { 
		 
		 if (imgStr == null){
			 return false; 			 
		 }
		 try { 
			 // Base64解码  
			 byte[] b = new BASE64Decoder().decodeBuffer(imgStr);  
			 for (int i = 0; i < b.length; ++i) {  
				 if (b[i] < 0) {  
					// 调整异常数据  
					 b[i] += 256;
				 }
			 }
		 OutputStream out = new FileOutputStream(imgFilePath); 
		 out.write(b); 
		 out.flush(); 
		 out.close(); 
		 return true; 
		 } catch (Exception e) { 
			 return false;  
		 }
	 }
	 
	public static String Image2String(String filePath) {

	//		String imgFile = "d://test.jpg";//待处理的图片  
	        InputStream in = null;  
	        byte[] data = null;  
	        //读取图片字节数组  
	        if(new File(filePath+".jpg").exists()){
		        try   
		        {  
		            in = new FileInputStream(filePath+".jpg");          
		            data = new byte[in.available()];  
		            in.read(data);  
		            in.close();  
		        }   
		        catch (IOException e)   
		        {  
		            e.printStackTrace();  
		        }  
	        }else if(new File(filePath+".bmp").exists()){
		        try   
		        {  
		            in = new FileInputStream(filePath+".bmp");          
		            data = new byte[in.available()];  
		            in.read(data);  
		            in.close();  
		        }   
		        catch (IOException e)   
		        {  
		            e.printStackTrace();  
		        } 	        	
	        }
	        //对字节数组Base64编码  
	        if(data!=null)
	        {
		        BASE64Encoder encoder = new BASE64Encoder();  
		        return encoder.encode(data);//返回Base64编码过的字节数组字符串  ;	
	        }
	        else{
	        	return null;
	        }
		}
	
	public String sqlInString(String[] array){
		if(array.length >0){
			String insql = "";
			for(int i =0 ;i<array.length ;i++){
				insql = insql+"'"+array[i]+"'";
				if(i + 1<array.length){
					insql = insql +",";
				}
			}
			insql = "("+insql+")";
			return insql;
		}else{
			return null;
		}
	}
}
