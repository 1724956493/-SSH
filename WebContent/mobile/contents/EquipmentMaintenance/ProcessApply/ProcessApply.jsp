<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<title></title>

		<link rel="stylesheet" href="../../../css/mui.min.css" />
		<link rel="stylesheet" href="../../../css/icons-extra.css" />
		<link rel="stylesheet" href="css/upImg.css" />
		<link rel="stylesheet" type="text/css" href="../../../css/mui.picker.min.css" />
		<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.12.0/themes/smoothness/jquery-ui.css" />
		<script type="text/javascript" src="../../../myjs/my_util.js"></script>
		<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
		<script type="text/javascript" src="../../../js/fastclick.js"></script>
		<script type="text/javascript" src="js/imgUp.js"></script>
		<link rel="stylesheet" type="text/css" href="css/ProcessApply.css" />

		<script src="https://g.alicdn.com/dingding/open-develop/1.5.1/dingtalk.js"></script>
		<script src="http://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
		<script src="../../../js/mui.min.js"></script>
		<script src="../../../js/mui.picker.min.js"></script>
		<script src="js/ProcessApply.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div class="mui-content">

			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>开始时间:</label> <input id="beginTime" type="text" class="" placeholder="点这选取日期" readonly="readonly">
				</div>
				<div class="mui-input-row">
					<label>结束时间:</label> <input id="endTime" type="text" class="" placeholder="点这选取日期" readonly="readonly">
				</div>
				<div class="mui-input-row">
					<label class="mui-navigate-right">状态:</label>
					<select name="select" id="select_status">
						<option value="0" selected="true">全部</option>
						<option value="1">待分配</option>
						<option value="2">待维修</option>
						<option value="3">待确定</option>
						<option value="4">完成</option>
						<option value="5">退回</option>
					</select>
				</div>
				<div style="margin-top: 10px;">

					<table width="100%">
						<tr id="showButton">
							<td width="45%" align="center">
								<!--<button id="submitConfirm" class="mui-btn mui-btn-block mui-btn-blue" type="button" style="margin-top: -10px;">确定</button></td>-->
								<button id="query" type="button" class="mui-btn mui-btn-block mui-btn-blue">查询</button>
							</td>
							<td width="10%"></td>
							<td width="45%">
								<button id="scan" type="button" class="mui-btn mui-btn-block mui-btn-red">扫描查询</button>
							</td>
						</tr>
						<tr id="showStatus" style="display: none;">
							<th colspan="3" align="center">单据状态：</th>
						</tr>
					</table>
				</div>
			</div>
			<!--<div class="mui-card">-->
			<ul class="mui-table-view">

			</ul>
			<!--</div>-->
			<div style="width: 100%;height: 50px;"></div>
		</div>

		<footer class="mui-bar mui-bar-footer">

			<table width="100%" height="100%">
				<tr id="showNum" style="display: none;">
					<th colspan="3" align="center">共计：</th>
				</tr>
			</table>
		</footer>
		<script type="text/javascript">
			mui.init();
		</script>
	</body>

</html>