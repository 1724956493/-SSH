<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>alter</title>
</head>
<body>
	<s:form action="AlterAction">
		<s:textfield name="Unitname" label="Unitname"></s:textfield>
		<s:textfield name="pk_corp" label="pk_corp"></s:textfield>
		<s:textfield name="nuitcode" label="nuitcode"></s:textfield>
		<s:submit value="Submit"></s:submit>
	</s:form>
</body>
</html>