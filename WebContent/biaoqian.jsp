<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<s:if test="'foo' in {'foo','bar'}">
	包含</s:if>
	<s:else>不包含</s:else>
<br>
		<s:if test="'foot' not in {'foo','bar'}">
	不包含</s:if>	<s:else>包含</s:else>

访问系统环境变量<s:property value="@java.lang.System@getenv('JAVA_HOME')"/>	
圆周率的值<s:property value="@java.lang.Math@PI"/>	

<br>
<table border="1" width="200">
<s:generator separator="," val="{'疯狂java讲义','轻量级企业应用实战','疯狂IOS讲义','java书籍'}">
<s:iterator  id="name" status="st">
	<tr <s:if test="#st.odd">style="background-color:#88cc88"</s:if>>
		<td><s:property value="#st.index"/>
		<s:property value="name"/>
		</td>
	</tr>
</s:iterator>
</s:generator>

</table>
</body>
</html>