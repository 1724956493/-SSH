package com.nts.teststruts.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkertUtil {

	private Template getTemplate(String name){ 
		//通过Freemaker的Configuration读取相应的ftl 
		Configuration cfg = new Configuration(); 
		Template tmp; 
		try { 
		//设定去哪里读取相应的ftl模板文件 
		cfg.setClassForTemplateLoading(this.getClass(), "/ftl"); 
		//在模板文件目录中找到名称为name的文件 
		tmp = cfg.getTemplate(name); 
		return tmp; 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		return null; 
	} 
	
	private void print(String name, Map<String, Object> root){ 
		Template tmp = this.getTemplate(name); 
		try { 
		//通过Template可以将模板文件输出到相应的流 
		tmp.process(root, new PrintWriter(System.out)); 
		} catch (TemplateException e) { 
		e.printStackTrace(); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
	} 
}
