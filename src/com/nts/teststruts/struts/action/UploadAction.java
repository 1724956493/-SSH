package com.nts.teststruts.struts.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.nts.teststruts.util.BillCodeUtil;
import com.nts.teststruts.util.ComUtil;
import com.opensymphony.xwork2.ActionSupport;


public class UploadAction extends ActionSupport implements ServletResponseAware,ServletRequestAware{ 

	private File file;
	private String fileFileName; //文件名称
	private String fileContentType; //文件类型
	private  HttpServletResponse  response;
	private  HttpServletRequest request ;
	private boolean success;
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public void responseHtmlText(String text){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF8");
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			return;
		}
	}
	
	public void responseJson(String jsonString){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=UTF8");
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			return;
		}
	}
	
	public void uploadFileUtils(){
		String fileType = request.getParameter("fileType");
		String filePath = request.getParameter("filePath");
		String uploadPath = ServletActionContext.getServletContext().getRealPath(filePath);;
		if(file == null){
			responseHtmlText("{success:false,message:'请选择文件'}");
			return;
		}
		if(fileContentType.indexOf(fileType)<0){
			responseHtmlText("{success:false,message:'请选择正确的文件格式'}");
			return;
		}
		File savefile = new File(new File(uploadPath),BillCodeUtil.getbillcode()+ComUtil.getExtensionName(fileFileName) );
		if (!savefile.getParentFile().exists()){
			savefile.getParentFile().mkdirs();
		}
		try {
			FileUtils.copyFile(file, savefile);
		} catch (IOException e) {
			System.out.println("保存文件失败");
			responseHtmlText("{success:false,message:'保存文件失败'}");
			return;
		}
		System.out.println("保存文件成功");
		responseHtmlText("{success:true,message:'文件上传成功',filename:'"+savefile.getName()+"'}");
	}
	
	@SuppressWarnings("resource")
	public void uploadFileIO(){
		InputStream is = null;
		OutputStream os = null;
		//基于myFile创建一个文件输入流
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			is = null;
			System.out.println("创建文件失败");
			responseHtmlText("{success:false,message:'创建文件失败'}");
			return;
		}
		//设置上传文件目录
		String uploadPath = ServletActionContext.getServletContext().getRealPath("/upload");
		System.out.println(uploadPath);
		//设置目标文件
		File savefile = new File(uploadPath, this.getFileFileName());
		if (!savefile.getParentFile().exists()){
			savefile.getParentFile().mkdirs();
		}
		//创建一个输出流
		try {
			os = new FileOutputStream(savefile);
		} catch (FileNotFoundException e) {
			os = null;
			System.out.println("创建输出流失败");
			responseHtmlText("{success:false,message:'创建输出流失败'}");
			return;
		}
		//设置缓存
		byte[] buffer = new byte[1024];
		int length = 0;
		//读取文件输出到toFile文件中
		try {
			while ((length = is.read(buffer)) > 0) {  
			    os.write(buffer, 0, length);  
			}
		} catch (IOException e) {
			System.out.println("读取文件失败");
			responseHtmlText("{success:false,message:'读取文件失败'}");
			return;
		}
		System.out.println("上传文件名" + fileFileName);  
        System.out.println("上传文件类型" + fileContentType);
        if(is != null){
        	try {
				is.close();
			} catch (IOException e) {
				System.out.println("关闭输入流失败");
			}
        }
        if(os != null){
        	try {
				os.close();
			} catch (IOException e) {
				System.out.println("关闭输出流失败");
			}
        }
        responseHtmlText("{success:true,message:'文件上传成功'}");
	}
	
	
	public void downFile() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String filePath = request.getParameter("filePath"); 
		String fileName = request.getParameter("fileName"); 
		System.out.println(filePath+2 +fileName);		

        new ComUtil().filedown(filePath, fileName, response); 	
	}

	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;		
	}
}  
	
	

