<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<title></title>

<link rel="stylesheet" href="../../../css/mui.min.css" />
<script type="text/javascript" src="../../../myjs/my_util.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/QueryMyApply.css" />
<script src="https://cdn.jsdelivr.net/npm/vue"></script>

<script src="https://cdn.jsdelivr.net/npm/vue-resource"></script>
<script
	src="https://g.alicdn.com/dingding/open-develop/1.5.1/dingtalk.js"></script>
<script src="../../../js/mui.min.js"></script>
<script src="../../../js/jquery.params.js"></script>
<script src="js/QueryMyApply.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="mui-content">
	
		<div class="mui-card" v-for="(value,index) in docDateJson"
			:key="index" style="display:none">
			<div class="mui-card-header">
				<table>
					<tr>
						<td v-text="'申请日期:'+value.a_ts.substring(0,11)"></td>
					</tr>
				</table>

			</div>
			<a :href="href+value.a_apppk">
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
						<table>
							<tr>
								<td class="secendCol"></td>
								<td class="fistCol">设备名称:</td>
								<td class="secendCol"></td>
								<td v-text="value.p_equip_name"></td>
							</tr>
							<tr>
								<td class="secendCol"></td>
								<td class="fistCol">设备编号:</td>
								<td class="secendCol"></td>
								<td v-text="value.p_equip_code"></td>
							</tr>
							<tr>
								<td class="secendCol"></td>
								<td class="fistCol">设备规格:</td>
								<td class="secendCol"></td>
								<td v-text="value.p_model"></td>
							</tr>

							<tr>
								<td class="secendCol"></td>
								<td class="fistCol">设备型号:</td>
								<td class="secendCol"></td>
								<td v-text="value.p_spec"></td>
							</tr>
							<tr>
								<td class="secendCol"></td>
								<td class="fistCol">现场识别码::</td>
								<td class="secendCol"></td>
								<td v-text="value.p_def3"></td>
							</tr>
						</table>
					</div>
				</div>
			</a>

			<div class="mui-card-footer">
				<table>
					<tr>
						<td align="center" v-text="'状态：'+value.a_status"></td>
					</tr>
				</table>

			</div>

		</div>
	</div>
	<script type="text/javascript">
		mui.init();
		mui('.mui-scroll-wrapper').scroll();
	</script>
</body>

</html>