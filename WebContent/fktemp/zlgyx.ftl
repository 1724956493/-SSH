<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关于${adquality.billhead}触犯质量高压线的处罚通报</title>
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
	<tr><td class="td3" colspan="3" style="color:#a9a9a9;"><img height="60" width="100"  src='../core/css/imgs/logo/head.png'/>质 量 处 罚 通 报</td><td class="td3" align="right"><img height="100" width="100"  src='${QRimage}' /></td></tr>
	<tr><td class="td3" colspan="4" align="center"><h2>关于${adquality.billhead}触犯质量高压线的处罚通报</h2></td></tr>
	<tr><td class="td3" colspan="4" align="right"><strong>报告编号：</strong>${adquality.billcode}</td></tr>
	<tr align="center"><td><strong>船号：</strong></td><td>${adquality.project}</td><td><strong>项目名称：</strong></td><td>${adquality.projectobj}</td></tr>
	<tr align="center"><td><strong>发生日期：</strong></td><td>${adquality.createdate}</td><td><strong>发生部门：</strong></td><td>${adquality.dept}</td></tr>
	<tr align="center"><td><strong>外协单位：</strong></td><td>${adquality.wbdept}</td><td></td><td></td></tr>
	<tr><td colspan="3"><strong>相应整改意见书（质量调查报告/质量事故通知书）编号：</strong></td><td>${adquality.panding}</td></tr>
	<tr><td colspan="4"><strong>问题描述：</strong><br />${adquality.miaoshu}
					<div align="center" >
					    <#if adquality.image1 ??>  
						<img  id ='img' src="../upload/image/${adquality.image1}">
						</#if>
						<#if adquality.image2 ??>  
							<img   id ='img' src="../upload/image/${adquality.image2}">
						</#if>
					</div>	
	</td></tr>
	<tr><td colspan="4"><strong>责任界定：</strong>${adquality.yiju}	</td></tr>
	<tr><td colspan="4"><strong>处罚建议/决定：</strong><br />
	根据《 质量高压线管理制度 》要求，处罚和考核内容：
           处罚及口指手述教育。触犯高压线，被及时发现并整改，未造成质量事故及经济损失，按上表金额扣罚原则分担如下：直接责任人10%、班组长25%、QA10%、主管10%、主管领导10%、部门长10%、劳务公司经理25%，具体扣罚实施由公司领导审批后执行；处罚如下：	
	<div>
		<table cellspacing="0">
			<tr  align="center"><th>序号</th><th>姓名</th><th>部门</th><th>职责级别</th><th>处罚金额</th></tr>
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
			<tr><td></td><td>合计：</td><td>${adquality.totalmulct}元</td><td colspan="2" >部门责任主管领导签收:</td></tr>
		</table>
	</div>	
	</td></tr>
	<tr><td colspan="4"><strong>整改要求:</strong><br />
					    <#if adquality.fangfa ??> 
				   		 ${adquality.fangfa}</#if>	
	</td></tr>
	<tr height=200px valign="top"><td colspan="2">质量管理部:</td><td colspan="2">公司领导审批/日期：</td></tr>
	<tr valign="top" border="0" style="color:#a9a9a9;"><td colspan="2" class="td3">江 苏 新 时代 造 船 有 限 公 司 <br />New Time Shipbuilding CO., LTD</td>
	                 <td colspan="2" class="td3">质  量  管  理  部 <br />Quality Management Department</td>
	</tr>
</table>
</div>
</body>
</html>