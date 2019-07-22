package com.nts.teststruts.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nts.teststruts.model.YktUser;
import com.nts.teststruts.service.impl.YktUserXfList;
import com.nts.teststruts.service.impl.YktXfReport;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ComUtil  {
	
    public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return "."+filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    }  
	
    public static boolean isEmptyString(String str)
	    {
	        return str == null || str.trim().length() == 0;
	    }
	
  //html文件生成pdf文件
    public static void printpdf(String temp, String outfile){
    	String command = "D:/Program Files/wkhtmltopdf/bin/wkhtmltopdf.exe " + temp + " " + outfile; ;  
    	try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());			
		}
    }

    //freemaker模板生成html文件
    public static void freemaker(String ftl,String outputfile,Map data){
    	Configuration cfg = new Configuration();
    	String uploadPath =ServletActionContext.getServletContext().getRealPath("/fktemp");
    	String htmlPath =ServletActionContext.getServletContext().getRealPath("/fkhtml");
    	try {
			cfg.setDirectoryForTemplateLoading(new File(uploadPath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			cfg.setDefaultEncoding("UTF-8");
			Template temp = cfg.getTemplate(ftl);
			temp.setEncoding("UTF-8"); 
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlPath+"\\"+outputfile), "UTF-8"));
			temp.process(data, out);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    public static void PrintQRcode(String code , String filepath) throws IOException{
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = null;
		
		try {
			bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, 100, 100,hints);
		} catch (WriterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = new File(filepath);
		MatrixToImageWriter.writeToFile(bitMatrix,"jpg",file);    	
    }
    
	public String excelSave(List<YktXfReport> xfroport ,String excelName){  
		try {  
			   File excelFile = new File(excelName);  
			   if (excelFile.exists()) 
			   		{excelFile.delete(); }
			   // 打开文件
			   WritableWorkbook book = Workbook.createWorkbook(excelFile);
			   // 生成名为“第一页”的工作表，参数0表示这是第一页  
			   WritableSheet sheet = book.createSheet("各部门消费记录报表 ", 0);
			   
			   
			   CellView navCellView = new CellView();  
		       navCellView.setAutosize(true); //设置自动大小
		       navCellView.setSize(18);
		       navCellView.setAutosize(true);
		       sheet.setColumnView(0, navCellView); //设置col显示样式
		       
			   // 合并单元格  
			   sheet.mergeCells(0, 0, 1, 0);
			   sheet.mergeCells(2, 0, 4, 0); 
			   sheet.mergeCells(5, 0, 7, 0); 
			   // 文字样式  
			   jxl.write.WritableFont wfc = new jxl.write.WritableFont(  
			     WritableFont.ARIAL, 15, WritableFont.BOLD, false,  
			     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
			   jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(  
					     wfc);  
			   
			   jxl.write.WritableFont wfc2 = new jxl.write.WritableFont(  
					     WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
					     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
			   jxl.write.WritableCellFormat wcfFC2 = new jxl.write.WritableCellFormat(  
							     wfc2);  
			   
			   jxl.write.WritableFont wfc3 = new jxl.write.WritableFont(  
					     WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
					     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			   jxl.write.WritableCellFormat wcfFC3 = new jxl.write.WritableCellFormat(  
							     wfc3);  
			   
			   // 设置单元格样式  
			//   wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色  
			   wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中  
			   
			   // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)  
			   // 以及单元格内容为     Label label10 = new Label(0, 0, "部门", wcfFC);
			   
			   // 将定义好的单元格添加到工作表中  
			   
			   sheet.addCell(new Label(0, 0, "部门", wcfFC));
			   sheet.addCell(new Label(2, 0, "新世纪", wcfFC));
			   sheet.addCell(new Label(5, 0, "新时代", wcfFC));
			   sheet.addCell(new Label(0, 1, "部门", wcfFC));
			   sheet.addCell(new Label(1, 1, "部门代号", wcfFC));
			   sheet.addCell(new Label(2, 1, "10:30-11:00", wcfFC));
			   sheet.addCell(new Label(3, 1, "11:00-11:05", wcfFC));
			   sheet.addCell(new Label(4, 1, "11:05-11:15", wcfFC));
			   sheet.addCell(new Label(5, 1, "10:30-11:00", wcfFC));
			   sheet.addCell(new Label(6, 1, "11:00-11:05", wcfFC));
			   sheet.addCell(new Label(7, 1, "11:05-11:15", wcfFC));
			   
			   for(int i =0 ;i<xfroport.size();i++){
				   sheet.setColumnView(i, navCellView);
				   sheet.addCell(new Label(0, i+2, xfroport.get(i).getDeptcode(), wcfFC2));
				   sheet.addCell(new Label(1, i+2, xfroport.get(i).getDeptname().trim(), wcfFC2));
				   if(xfroport.get(i).getTime1Total()!=0){
				   sheet.addCell(new jxl.write.Number(2, i+2, xfroport.get(i).getTime1Total(), wcfFC3));
				   }
				   if(xfroport.get(i).getTime2Total()!=0){
				   sheet.addCell(new jxl.write.Number(3, i+2, xfroport.get(i).getTime2Total(), wcfFC3));}
				   if(xfroport.get(i).getTime3Total()!=0){
				   sheet.addCell(new jxl.write.Number(4, i+2, xfroport.get(i).getTime3Total(), wcfFC3));}
				   if(xfroport.get(i).getTime4Total()!=0){
				   sheet.addCell(new jxl.write.Number(5, i+2, xfroport.get(i).getTime4Total(), wcfFC3));}
				   if(xfroport.get(i).getTime5Total()!=0){
				   sheet.addCell(new jxl.write.Number(6, i+2, xfroport.get(i).getTime5Total(), wcfFC3));}
				   if(xfroport.get(i).getTime6Total()!=0){
				   sheet.addCell(new jxl.write.Number(7, i+2, xfroport.get(i).getTime6Total(), wcfFC3));}
			   }
			   
			   
			   /**//* 
			     * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123 
			     */  
		//	   jxl.write.Number number = new jxl.write.Number(3, 3, 555.12541);  
		//	   sheet.addCell(number); 
			   
			// 写入数据并关闭文件  
			   book.write();  
			   book.close();  
			   System.out.println("Excel创建成功"); 		   
			   
		}catch (Exception e) {  
			   System.out.println(e);  
		  }  
		return null;
	}
	
	public String excelSave2(List<YktUserXfList> xfroport ,String excelName,String date){  
		try {  
			   File excelFile = new File(excelName);  

			   Workbook wb = Workbook.getWorkbook(excelFile); // 获得原始文档
			   String ffilename = "d:/"+date+"消费明细表.xls";

			   WritableWorkbook book = Workbook.createWorkbook(new File(ffilename),wb); // 创建一个可读写的副本
			   // 生成名为“第一页”的工作表，参数0表示这是第一页  
			   WritableSheet sheet = book.createSheet("消费明细表 ", 2);
			   
			   
			   CellView navCellView = new CellView();  
		       navCellView.setAutosize(true); //设置自动大小
		       navCellView.setAutosize(true);
	//	       sheet.setColumnView(0, navCellView); //设置col显示样式
 
 
			   // 文字样式  
			   jxl.write.WritableFont wfc = new jxl.write.WritableFont(  
			     WritableFont.ARIAL, 15, WritableFont.BOLD, false,  
			     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
			   jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(  
					     wfc);  
			   
			   jxl.write.WritableFont wfc2 = new jxl.write.WritableFont(  
					     WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
					     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
			   jxl.write.WritableCellFormat wcfFC2 = new jxl.write.WritableCellFormat(  
							     wfc2);  
			   
			   // 设置单元格样式  
			//   wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色  
			   wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中  
			   
			   // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)  
			   // 以及单元格内容为     Label label10 = new Label(0, 0, "部门", wcfFC);
			   
			   // 将定义好的单元格添加到工作表中  
			   
			   sheet.addCell(new Label(0, 0, "一卡通号", wcfFC));
			   sheet.addCell(new Label(1, 0, "姓名", wcfFC));
			   sheet.addCell(new Label(2, 0, "部门编号", wcfFC));
			   sheet.addCell(new Label(3, 0, "部门名称", wcfFC));
			   sheet.addCell(new Label(4, 0, "消费时间", wcfFC));
			   sheet.addCell(new Label(5, 0, "食堂名称", wcfFC));
			   sheet.addCell(new Label(6, 0, "消费机IP", wcfFC));
			   sheet.addCell(new Label(7, 0, "消费机备注", wcfFC));
			   sheet.addCell(new Label(8, 0, "消费机编号", wcfFC));

			   
			   for(int i =0 ;i<xfroport.size();i++){
				   if(xfroport.get(i)!=null){
				   sheet.setColumnView(i, navCellView);
				   sheet.addCell(new Label(0, i+1, xfroport.get(i).getYktusercode(), wcfFC2));
				   sheet.addCell(new Label(1, i+1, xfroport.get(i).getYktusername().trim(), wcfFC2));
				   sheet.addCell(new Label(2, i+1, xfroport.get(i).getYktdeptcode().trim(), wcfFC2));
				   sheet.addCell(new Label(3, i+1, xfroport.get(i).getYktdeptname().trim(), wcfFC2));
				   sheet.addCell(new Label(4, i+1, xfroport.get(i).getYktxftime().trim(), wcfFC2));
				   if(xfroport.get(i).getYktstname()!=null){
				   sheet.addCell(new Label(5, i+1, xfroport.get(i).getYktstname().trim(), wcfFC2));}
				   if(xfroport.get(i).getYktxfjip()!=null){
				   sheet.addCell(new Label(6, i+1, xfroport.get(i).getYktxfjip().trim(), wcfFC2));}
				   if(xfroport.get(i).getYktxfjbz()!=null){
				   sheet.addCell(new Label(7, i+1, xfroport.get(i).getYktxfjbz().trim(), wcfFC2));}
				   if(xfroport.get(i).getYktxfjbh()!=null){
				   sheet.addCell(new Label(8, i+1, xfroport.get(i).getYktxfjbh().trim(), wcfFC2));}
			   }}
			   
			   
			   /**//* 
			     * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123 
			     */  
		//	   jxl.write.Number number = new jxl.write.Number(3, 3, 555.12541);  
		//	   sheet.addCell(number); 
			   
			// 写入数据并关闭文件  
			   book.write();  
			   book.close();  
			   System.out.println("Excel创建成功"); 		   
			   
		}catch (Exception e) {  
			   System.out.println(e);  
		  }  
		return null;
	}

	//类导出到excle通用方法
	public  void excelSave2(List<Object> list,String excelName) throws IllegalArgumentException, IllegalAccessException, WriteException, IOException{
		 Class<?>  classz = list.get(0).getClass();
		 Field[] fields = classz.getDeclaredFields();
		File excelFile = new File(excelName); 
		 WritableWorkbook book;
			book = Workbook.createWorkbook(excelFile);
			WritableSheet sheet = book.createSheet(excelName, 0);
			
			   CellView navCellView = new CellView();  
		       navCellView.setAutosize(true); //设置自动大小
		       navCellView.setSize(18);
		        //设置col显示样式
			
		   jxl.write.WritableFont wfc = new jxl.write.WritableFont(  
				     WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
				     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
				   jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(  
						     wfc);     
				 
					wcfFC.setAlignment(jxl.format.Alignment.CENTRE);
	//				int i =0;
		
	 for(int i =0;i<fields.length;i++){
		 sheet.setColumnView(i, navCellView);
		 sheet.addCell(new Label(i, 0, fields[i].getAnnotation(FieldChinese.class).name() , wcfFC));
	 }
					
	
					
	  for(int i =0;i<list.size();i++){
			  for(int j =0;j<fields.length;j++){
			       if(!fields[j].isAccessible()){
			                    fields[j].setAccessible(true);
			                }
			       if(fields[j].get(list.get(i))!=null){
			  //     System.out.println(fields[j].get(list.get(i)));
			    	   if(fields[j].getType().getSimpleName() != "int")
			    	   {
			    		   sheet.addCell(new Label(j, i+1, (String)fields[j].get(list.get(i)) , wcfFC));
			    	   }else{
			    		   sheet.addCell(new jxl.write.Number(j, i+1,(int) fields[j].get(list.get(i)) , wcfFC));
			    	   }
			       }
			  }		  
		  }

			   book.write();  
			   book.close();  
			   System.out.println("Excel创建成功"); 	
	
		   // 生成名为“第一页”的工作表，参数0表示这是第一页  
	}
	
	
	//类LIST<OBJECT[]>导出到excle通用方法
	public  void excelSavelistobj(List<Object[]> list,String excelName , String[] header) throws IllegalArgumentException, IllegalAccessException, WriteException, IOException{
		Object[] objarray = list.get(0);
		File excelFile = new File(excelName); 
		 WritableWorkbook book;
			book = Workbook.createWorkbook(excelFile);
			WritableSheet sheet = book.createSheet("worksheet1", 0);
			
			   CellView navCellView = new CellView();  
		       navCellView.setAutosize(true); //设置自动大小
		       navCellView.setSize(18);
		        //设置col显示样式
			
		   jxl.write.WritableFont wfc = new jxl.write.WritableFont(  
				     WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
				     UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); 
				   jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(  
						     wfc);     
				 
					wcfFC.setAlignment(jxl.format.Alignment.CENTRE);
	//				int i =0;
		
	 for(int i =0;i<objarray.length;i++){
		 sheet.setColumnView(i, navCellView);
		 sheet.addCell(new Label(i, 0,header[i], wcfFC));
	 }
					
	
					
	  for(int i =0;i<list.size();i++){
			  for(int j =0;j<objarray.length;j++){
			       if(list.get(i)[j]!=null){
			    		   sheet.addCell(new Label(j, i+1, list.get(i)[j].toString() , wcfFC));		    		  
			       }
			  }		  
		  }

			   book.write();  
			   book.close();  
			   System.out.println("Excel创建成功"); 	
	
		   // 生成名为“第一页”的工作表，参数0表示这是第一页  
	}
	
	
	public void filedown(String filePath,String fileName,HttpServletResponse  response) throws Exception{
		if("".equals(filePath)||null==filePath){  
            System.out.println("文件路径异常");  
        }else{
        	File file = new File(filePath,fileName); 
        	if(file.exists()){
        		 response.setHeader("Content-Type", "application/force-download"); 
        		 response.setHeader("Content-Disposition", "attachment; filename=\""+  
                         new String(fileName.getBytes("gb2312"), "ISO8859-1" )+"\"");
        		 ServletOutputStream os = response.getOutputStream();  
                 BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file)); 
                 try {  
                     byte[] content = new byte[1024];    
                     int length;  
                     while ((length = fin.read(content, 0, content.length)) != -1){     
                         os.write(content, 0, length);     
                     }  
                     System.out.println("文件下载成功");  
                 } catch (Exception e) {  
                     System.out.println("文件下载失败");  
                 }finally{  
                     fin.close();     
                     os.flush();     
                     os.close();  
                 } 
                 }  
             }   		
	}
	
	 public static int daysBetween(Date smdate,Date bdate) throws ParseException    
	    {    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	        int age = Integer.parseInt(String.valueOf(between_days/365));
	            
	       return age;           
	    }    
	 
	 public static int employeescore(String[] array)
		 {
				int score = 0;
				HashMap<String, Integer> bonusmap = new HashMap<String, Integer>();
					bonusmap.put("违反质量高压线",10);
					bonusmap.put("超电流电压",2);
					bonusmap.put("机头旋钮表盘不清晰",2);
					bonusmap.put("机头旋钮损坏",2);
					bonusmap.put("焊接参数不张贴",2);
					bonusmap.put("焊机校准标签不贴、不更新",2);
					bonusmap.put("现场不携带焊工证",1);
					bonusmap.put("焊接上面不挂焊工证",1);
					bonusmap.put("不正确使用引熄弧板",2);
					bonusmap.put("其它",0);
				
				for(int i=0;i<array.length;i++)
					{
						score +=bonusmap.get(array[i]).intValue();
					}	
				
				return score;
		 }
	 
	 
}
