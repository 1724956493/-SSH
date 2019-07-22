<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>安全管理4S考核通知单</title>
<style type="text/css"> 
#layout {width: 800px;
		 height: 1200px;
		 border: 0px solid #000000;
		 padding-top: 30px;
		 margin: 30px auto;
		} 
#img {
    max-height:200px;
	max-width:780px;
}
table {
width: 100%;
height: 100%;
margin :0 auto
}
table tr td{
border:1px solid
}
table tr th{
border:1px solid
}
.td3{ 
border:0px solid
} 
</style>
</head>
<body>
<div id='layout'>
<table cellspacing="0" cellpadding="1">
	<tr><td class="td3" colspan="3" style="color:#a9a9a9;"><img height="60" width="100"  src='../core/css/imgs/logo/head.png'/>环境和职业健康安全管理处罚通报</td><td class="td3" align="right"><img height="100" width="100"  src='${QRimage}' /></td></tr>
	<tr><td class="td3" colspan="4" align="center"><h2>“4S”考核通知单</h2></td></tr>
	<tr><td class="td3" colspan="4" align="right"><strong>报告编号：</strong>${adquality.billcode}</td></tr>
	<tr align="center"><td><strong>检查区域：</strong></td><td>${adquality.projectobj}</td><td><strong>发生日期：</strong></td><td>${adquality.createdate}</td></tr>
	<tr align="center"><td><strong>责任部门：</strong></td><td>${adquality.dept}</td><td><strong>劳务公司：</strong></td><td>
	<#if adquality.wbdept ??> 	
	${adquality.wbdept}</#if></td></tr>
	<tr><td colspan="4"><strong>不合格描述：</strong><br />${adquality.miaoshu}
					<div align="center" >
					    <#if adquality.image1 ??>  
						<img  id ='img' src="../upload/image/${adquality.image1}">
						</#if>
						<#if adquality.image2 ??>  
							<img   id ='img' src="../upload/image/${adquality.image2}">
						</#if>
					</div>	
	</td></tr>
	<tr><td colspan="4"><strong>处罚依据：</strong><br />
		${adquality.yiju}
	<div>
		<table cellspacing="0">
			<tr  align="center"><th>序号</th><th>部门/劳务公司</th><th>姓名</th><th>职责级别</th><th>处罚金额</th></tr>
			<#list adqualitybillsublist as adqualitybillsub>
				<tr  align="center">
				    <td>${adqualitybillsub_index+1}</td>
				    <td>${adqualitybillsub.dept}</td>
				    <td>
				    <#if adqualitybillsub.psnname ??> 
				   		 ${adqualitybillsub.psnname}</#if>
				    </td>				    
				    <td>${adqualitybillsub.joblevel}</td>
				    <td>${adqualitybillsub.mulct}元</td>
				</tr>
			</#list>
			
		</table>
		合计：${adquality.totalmulct}元
	</div>	
	</td></tr>
	<tr><td colspan="4"><strong>纠正措施:</strong><br />
					    <#if adquality.fangfa ??> 
				   		 ${adquality.fangfa}</#if>	
	</td></tr>
	<tr height=200px valign="top"><td colspan="1">安环部:</td><td colspan="1">责任部门主管领导:</td><td colspan="2">公司领导审批/日期：</td></tr>
	<tr valign="top" border="0" style="color:#a9a9a9;"><td colspan="2" class="td3">江 苏 新 时代 造 船 有 限 公 司 <br />New Time Shipbuilding CO., LTD</td>
	                 <td colspan="2" class="td3">安环部 <br />HSE</td>
	</tr>
</table>
</div>
</body>
</html>