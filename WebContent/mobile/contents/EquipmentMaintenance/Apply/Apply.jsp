<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>
<html>

<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<link rel="stylesheet" href="../../../css/mui.min.css" />
<link rel="stylesheet" href="../../../css/icons-extra.css" />
<link rel="stylesheet" href="css/upImg.css" />
<link rel="stylesheet" type="text/css"
	href="../../../css/mui.picker.min.css" />
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.12.0/themes/smoothness/jquery-ui.css" />
<script type="text/javascript" src="../../../myjs/my_util.js"></script>
<script type="text/javascript">
			var _config = <%=com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request)%>;
			var getsession_userPhone = <%=session.getAttribute("userPhone")%>;
			var getsession_userJson = <%=session.getAttribute("userJson")%>;
</script>
<script
	src="https://g.alicdn.com/dingding/open-develop/1.5.1/dingtalk.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../../js/fastclick.js"></script>
<script type="text/javascript" src="js/imgUp.js"></script>
<link rel="stylesheet" type="text/css" href="css/Apply.css" />

<script src="http://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
<script src="../../../js/mui.min.js"></script>
<script src="../../../js/mui.picker.min.js"></script>
<script src="js/Apply.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>

	<div class="mui-content">

		<div class="mui-input-group">
			
			<div class="mui-input-row">
				<label>设备名称:</label> <input id="mcNm" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>设备编号:</label> <input id="mcCd" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>设备规格:</label> <input id="mcGuige" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>设备型号:</label> <input id="mcXinghao" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>现场识别码:</label> <input id="mcXianchangshibiema" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>故障时间:</label> <input id="mcGuZhangTime" type="text"
					class="" placeholder="点这选取日期" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>报修时间:</label> <input id="mcBaoXiuTime" type="text"
					class="" placeholder="点这选取日期" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>申请人:</label> <input id="mcShengQingUser" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>申请人电话:</label> <input id="mcShengQingUserPhone" type="text"
					class="" placeholder="扫描获取数据" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell" id="li_img">上传照片：
						<div class="mui-icon mui-icon-search" style="float: right;"></div>
						<input type="file" name="file" id="file" class="file" value=""
						accept="image/jpg,image/jpeg,image/png,image/bmp"
						multiple="multiple">
					</li>
				</ul>
			</div>
			<div class="z_photo upimg-div clear">
				<div class="up-section fl loading">
					<span class="up-span"></span> <img src="images/del.png"
						class="close-upimg"> <img src="" class="up-img up-opcity">
					<p class="img-namep"></p>
					<input id="taglocation" name="taglocation" value="" type="hidden">
					<input id="tags" name="tags" value="" type="hidden">
				</div>
			</div>
			<div class="mui-input-row">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell" id="li_send"><span id="li_send_span">推送：</span>
						<div class="mui-icon mui-icon-plusempty" style="float: right;"></div>
					</li>
				</ul>
			</div>
			<textarea id="note"
				style=" margin-top: .5rem;-webkit-box-flex: 1;display: block;"
				name=""  placeholder="故障说明(必填)："></textarea>
			<div style="width: 100%;height: 50px;"></div>
		</div>
	</div>

	<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<div class="mui-popover-arrow"></div>
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell" id="0"><a href="#"> <span
							class="mui-icon mui-icon-search">&nbsp;&nbsp;查询我的申请</span></li>
					<li class="mui-table-view-cell " id="1"><a href="# "> <span
							class="mui-icon mui-icon-search">&nbsp;&nbsp;刷新</span></li>
				</ul>
			</div>
		</div>
	</div>
	<footer class="mui-bar mui-bar-footer">
		<button id="submitData" class="mui-btn mui-btn-block mui-btn-blue"
			type="button">提交申请</button>
	</footer>
	
	<script type="text/javascript">
		mui.init();
		mui('.mui-scroll-wrapper').scroll();
	</script>
</body>

</html>