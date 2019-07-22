
var queryJson = {
	"begintime" : "",
	"endtime" : "",
	"userPh" : "",
	"status" : "",
	"method" : "query",
	"equip_pk" : ""
}
dd.ready(function() {
	dd.biz.navigation.setTitle({
		//接收全局变量title
		title : '查询我的申请单',
		onSuccess : function(data) {},
		onFail : function(err) {
			log.e(JSON.stringify(err));
		}
	});
	dd.biz.navigation.setRight({
		show : false, //控制按钮显示， true 显示， false 隐藏， 默认true
		control : true, //是否控制点击事件，true 控制，false 不控制， 默认false
		text : '刷新', //控制显示文本，空字符串表示显示默认文本
		onSuccess : function(result) {
			window.location.reload();
		},
		onFail : function(err) {}
	});
	queryJson.userPh = localStorage.getItem("ClientPhone");

	$("#beginTime").on('click', function() {
		var picker = new mui.DtPicker({
			"type" : "date"
		});
		picker.show(function(rs) {
			$("#beginTime").val(rs.text);
			queryJson.begintime = rs.text;
			picker.dispose();
		});
	});

	$("#endTime").on('click', function() {
		var picker = new mui.DtPicker({
			"type" : "date"
		});
		picker.show(function(rs) {
			$("#endTime").val(rs.text);
			queryJson.endtime = rs.text;
			picker.dispose();
		});
	});
	$("#query").on('click', function() {
		queryJson.method = "query";
		$(".li_mulct").remove();
		if (queryJson.endtime == "" || queryJson.begintime == "") {
			mui.alert("请选择开始时间和结束时间")
		}

		queryJson.status = $("#select_status").find("option:selected").text();

		var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByTime';

		var postData = JSON.stringify(queryJson);
		$.post(url, {
			submitData : postData
		}, function(data, status) {
			var jsonData = JSON.parse(data);
			var num = 0;
			$.each(jsonData, function(index, content) {
				var string_html = "<li class='li_mulct mui-table-view-cell mui-collapse' ><a class='mui-navigate-right' style='width: 100%;''>申请日期： ";
				string_html += content.a_ts + "</a><div class='mui-collapse-content'>";
				string_html += "<table style='width: 100%;'border= 1>";
				string_html += "<tr><td width='40%'>设备名称:</td><td >" + content.p_equip_name + "</td></tr>";
				string_html += "<tr><td >设备编号:</td><td >" + content.p_equip_code + "</td></tr>";
				string_html += "<tr><td >设备规格:</td><td >" + content.p_model + "</td></tr>";
				string_html += "<tr><td >设备型号:</td><td >" + content.p_spec + "</td></tr>";
				string_html += "<tr><td >现场识别码:</td><td >" + content.p_def3 + "</td></tr>";
				string_html += "<tr><td >申请状态:</td><td >" + content.a_status + "</td></tr>";
				string_html += "</table></div></li>";
				++num;
				$(".mui-table-view").append(string_html.replace("undefined", ""));
			});
			$("#showNum").show();
			$("#showNum th").text("共计：" + num + "条记录");
		});
	});

	$("#scan").on('click', function() {
		queryJson.method = "scan";
		$(".li_mulct").remove();

		queryJson.status = $("#select_status").find("option:selected").text();

		dd.biz.util.scan({
			type : "qrCode", //type为qrCode或者barCode
			onSuccess : function(data) {
				queryJson.equip_pk = data.text;

				var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByTime';

				var postData = JSON.stringify(queryJson);
				$.post(url, {
					submitData : postData
				}, function(data, status) {
					var jsonData = JSON.parse(data);
					var num = 0;
					$.each(jsonData, function(index, content) {
						var string_html = "<li class='li_mulct mui-table-view-cell mui-collapse' ><a class='mui-navigate-right' style='width: 100%;''>申请日期： ";
						string_html += content.a_ts + "</a><div class='mui-collapse-content'>";
						string_html += "<table style='width: 100%;'border= 1>";
						string_html += "<tr><td width='40%'>设备名称:</td><td >" + content.p_equip_name + "</td></tr>";
						string_html += "<tr><td >设备编号:</td><td >" + content.p_equip_code + "</td></tr>";
						string_html += "<tr><td >设备规格:</td><td >" + content.p_model + "</td></tr>";
						string_html += "<tr><td >设备型号:</td><td >" + content.p_spec + "</td></tr>";
						string_html += "<tr><td >现场识别码:</td><td >" + content.p_def3 + "</td></tr>";
						string_html += "<tr><td >申请状态:</td><td >" + content.a_status + "</td></tr>";

						string_html += "</table></div></li>";
						++num;

						$(".mui-table-view").append(string_html.replace("undefined", ""));
					});
					$("#showNum").show();
					$("#showNum th").text("共计：" + num + "条记录");
				});

			},
			onFail : function(err) {}
		});

	});
});