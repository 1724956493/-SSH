package com.nts.teststruts.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;

public class UploadUtil {

/*	public void upLoadFile(File source, File target) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new  BufferedInputStream(new FileInputStream(source), FILE_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(target),FILE_SIZE);
			byte[] image = new byte[FILE_SIZE];
			while (in.read(image) > 0) {
				out.write(image);
					}
			} 
		catch (IOException ex) {
			ex.printStackTrace();
			} 
		finally {
			try {
			in.close();
			out.close();
			} catch (IOException ex) {	
			}
		}}

	public String photoUpload() {
		String filename = null;
		try {
			filename = photoName.substring(12, photoName.length());
			String filePath = ServletActionContext.getServletContext().getRealPath("upload");
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdir();
			}
			File targetFile = new File(filePath + "\\" + filename);
			System.out.println("文件上传到的路径为："+targetFile);
			upLoadFile(file, targetFile);
			response.getWriter().write("{success:true}");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		}
	*/
}
