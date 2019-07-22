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
		</script>
<script
	src="https://g.alicdn.com/dingding/open-develop/1.5.1/dingtalk.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../../js/fastclick.js"></script>
<script type="text/javascript" src="js/imgUp.js"></script>
<link rel="stylesheet" type="text/css" href="css/Repair.css" />

<script src="http://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
<script src="../../../js/mui.min.js"></script>
<script src="../../../js/jquery.params.js"></script>
<script src="../../../js/mui.picker.min.js"></script>
<script src="js/Repair.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>

	<div class="mui-content">

		<div class="mui-input-group">

			<div class="mui-input-row">
				<label>设备名称:</label> <input id="mcNm" type="text" class=" clear"
					readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>设备编号:</label> <input id="mcCd" type="text" class=" clear"
					readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>设备规格:</label> <input id="mcGuige" type="text" class=" clear"
					readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>设备型号:</label> <input id="mcXinghao" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>现场识别码:</label> <input id="mcXianchangshibiema" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>故障时间:</label> <input id="mcGuZhangTime" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>报修时间:</label> <input id="mcBaoXiuTime" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label class="fontblod">申请人:</label> <input id="mcShengQingUser" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>申请人电话:</label> <input id="mcShengQingUserPhone" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>故障说明:</label> <input id="mcAppNote" type="text"
					class=" clear" placeholder="点击查看故障说明" readonly="readonly">
			</div>
			<div id="showAppNote" style="display: none;">
				<textarea id="app_note"
					style="margin-top: .5rem;-webkit-box-flex: 1;display: block;"
					name="" class="clear" readonly="readonly"></textarea>
			</div>
			<div class="mui-input-row">
				<label class="fontblod">分配人:</label> <input id="mcFenPeiUser" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>分配人电话:</label> <input id="mcFenPeiUserPhone" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>分配时间:</label> <input id="mcFenPeiUserTime" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>分配说明:</label> <input id="mcDistributeNote" type="text"
					class=" clear" placeholder="点击查看分配人要求" readonly="readonly">
			</div>
			<div id="showDistributeNote" style="display: none;">
				<textarea id="distribute_note"
					style=" margin-top: .5rem;-webkit-box-flex: 1;display: block;"
					name="" class="clear" readonly="readonly"></textarea>
			</div>
			
			
			
			<div class="mui-input-row isretrun"style="display: none;">
				<label class="fontblod">退回人:</label> <input id="mcReturnUser" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row isretrun"style="display: none;">
				<label>退回人电话:</label> <input id="mcReturnPhone" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row isretrun"style="display: none;">
				<label>退回时间:</label> <input id="mcReturnTime" type="text"
					class=" clear" readonly="readonly">
			</div>
			<div class="mui-input-row isretrun"style="display: none;">
				<label>退回说明:</label> <input id="mcConfirmNote" type="text"
					class=" clear" placeholder="点击查看退回说明" readonly="readonly">
			</div>
			<div id="showConfirmNote" style="display: none;">
				<textarea id="confirm_note"
					style=" margin-top: .5rem;-webkit-box-flex: 1;display: block;"
					name="" class="clear" readonly="readonly"></textarea>
			</div>

			<div class="mui-input-row"></div>
			<div class="mui-input-row">
				<label>维修人:</label> <input id="mcWeiXiuUser" type="text"
					class=" clear" placeholder="" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>维修人电话:</label> <input id="mcWeiXiuUserPhone" type="text"
					class=" clear" placeholder="" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>开始时间:</label> <input id="mcBeginTime" type="text"
					class=" clear" placeholder="点这选取日期" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<label>结束时间:</label> <input id="mcEndTime" type="text"
					class=" clear" placeholder="点这选取日期" readonly="readonly">
			</div>
			<div class="mui-input-row">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell" id="li_send"><span
						id="li_send_span">维修确认：</span>
						<div class="mui-icon mui-icon-plusempty" style="float: right;"></div>
					</li>
				</ul>
			</div>
			<textarea id="note" placeholder="维修备注"
				style=" margin-top: .5rem;-webkit-box-flex: 1;display: block;"
				name="" class="clear"></textarea>
			<div style="width: 100%;height: 50px;"></div>
		</div>

	</div>

	<footer class="mui-bar mui-bar-footer">
		<button id="submitData" class="mui-btn mui-btn-block mui-btn-blue"
			type="button">确定已维修</button>
		<table id="showStatus" width="100%" height="100%"
			style="display:none;">
			<tr>
				<th>单据状态：</th>
			</tr>
		</table>
	</footer>

	<script type="text/javascript">
		mui.init();
		mui('.mui-scroll-wrapper').scroll();
	</script>
</body>

</html>