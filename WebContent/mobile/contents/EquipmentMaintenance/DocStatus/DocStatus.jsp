<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Pragma" content="no-cache">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<title></title>

<link rel="stylesheet" href="../../../css/mui.min.css" />
<link rel="stylesheet" href="../../../css/icons-extra.css" />
<link rel="stylesheet" href="css/upImg.css" />
<link rel="stylesheet" type="text/css"
	href="../../../css/mui.picker.min.css" />
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.12.0/themes/smoothness/jquery-ui.css" />
<script type="text/javascript" src="../../../myjs/my_util.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../../js/fastclick.js"></script>
<script type="text/javascript" src="js/imgUp.js"></script>
<link rel="stylesheet" type="text/css" href="css/DocStatus.css" />
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
<script src="https://cdn.jsdelivr.net/npm/vue-resource"></script>
<script
	src="https://g.alicdn.com/dingding/open-develop/1.5.1/dingtalk.js"></script>
<script src="http://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
<script src="../../../js/mui.min.js"></script>
<script src="../../../js/jquery.params.js"></script>
<script src="../../../js/mui.picker.min.js"></script>
<script src="js/DocStatus.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
table {
	width: 100%;
}

.fistCol {
	text-align: right;
	width: 40%;
}

.borderTd {
	height: 5rem;
	border-right: blue 0.2rem solid;
}
</style>
</head>

<body>

	<div id="box" class="mui-content">

		<table>
			<tr style="height: 2rem;">
				
			</tr>
			<tr v-for="(value,index) in computebaseData">
				<td class="fistCol" :class="{borderTd:!value.ifshow}"
					v-text="value.num"></td>
				<td v-text="value.name"></td>
			</tr>
			<tr style="height: 4rem;">
				
			</tr>
		</table>

	</div>


	<script type="text/javascript">
		mui.init();
		mui('.mui-scroll-wrapper').scroll();
	</script>
</body>

</html>