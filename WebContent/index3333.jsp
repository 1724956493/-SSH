<?xml version="1.0" encoding="GB18030" ?>
<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
 <%@taglib uri="/struts-tags" prefix="s" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
<base href="<%=basePath %>"/>
<title>Insert title here</title>
</head>
<body> 
<s:form action="/alter/alter_add" method="post">
	<s:textfield name ="sapply_h.vbillcode" label="���ݺ�"></s:textfield>
	<s:textfield name ="corp.pk_corp" label="��˾PK"></s:textfield>
	<s:submit></s:submit>
</s:form>	
	
	<s:textfield label="��˾PK"></s:textfield>
ʹ��Domain Model���ղ���<a href="user/user!add?user.name=a&user.age=8">����û�</a>
<s:form action="/user/user_doadd" method="post">
	<s:textfield name ="user.name" label="����"></s:textfield>
	<s:textfield name ="user.age" label="����"></s:textfield>
	<s:submit></s:submit>
</s:form>	
<s:form action="/corp/corp_add" method="post">
	<s:textfield name ="corp.unitname" label="��˾����" ></s:textfield>
	<s:textfield name ="corp.pk_corp" label="��˾PK" ></s:textfield>
	<s:textfield name ="corp.nuitcode" label="��˾����" ></s:textfield>
	<s:submit ></s:submit>
</s:form>	
<s:property  value="#session.user.userName"/>
<s:property  value="#session.user.userCode"/>
<s:debug></s:debug>
</body>
</html>