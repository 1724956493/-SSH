<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户登陆</title>
	</head>
	<body bgcolor=“#E8E8FA” >
		<div id="centerdiv"  style= "width:300px;height:100px;margin:200px auto; border:0px solid;text-align:center;">
			<h3>用户登陆</h3>
			<p></p><p></p><p></p>
			<s:form action="user_login" method="post">
			<s:textfield label="用户名" name="smuser.userCode"></s:textfield>
			<s:password label="密      码" name="password"></s:password> 
			<s:submit value="提交"/>
			</s:form>
		</div>
	</body>
</html>