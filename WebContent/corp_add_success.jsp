<?xml version="1.0" encoding="GB18030" ?>
<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
    <%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030" />
<title>Insert title here</title>
</head>
<body>
	<s:form action="/corp/corp_print" method="post">
	<s:select label="请选择公司" labelposition="top" name="corp.pkCorp" list="corps" listKey="pkCorp" listValue="unitname" />
	<s:submit name="提交"/>
	</s:form>
	
	<s:debug></s:debug>
	<s:textfield value=""></s:textfield>
</body>
</html>