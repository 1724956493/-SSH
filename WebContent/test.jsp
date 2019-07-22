<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width" />
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/dingding/open-develop/1.5.1/dingtalk.js"></script>
<script>
$(document).ready(function(){
  $("#btn1").click(function(){
	  /* dd.biz.util.scan({
			type : String, //type为qrCode或者barCode
			onSuccess : function(data) { 
				alert('123456')
				
			},
			onFail : function(err) {}
		})  */	
		
	  dd.device.notification.alert('1234565');
  })
})
</script>
</head>
<body>
<div data-role="page">

  <div data-role="header">
    <h1>车辆查询系统</h1>
  </div>

  <div data-role="content">
  <div data-role="fieldcontain">
 		请输入车牌号码:<input type="text" id="carid">
 		<a data-role="button" data-icon="search" id="btn1">查询</a>
 		<div id="test"></div>
  </div>
  </div>

  <div data-role="footer">
    <h1>新时代造船有限公司</h1>
  </div>

</div>
</body>
</html>