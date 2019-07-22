dd.ready(function() {

	var queryJson = {
		"begintime": "",
		"endtime": "",
		"status": "",
		"method": "query",
		"equip_pk": ""
	};

	dd.biz.navigation.setTitle({
		//接收全局变量title
		title: ' 处理设备维修申请',
		onSuccess: function(data) {},
		onFail: function(err) {
			log.e(JSON.stringify(err));
		}
	});
	$("#beginTime").val("");
	$("#beginTime").on('click', function() {
		var picker = new mui.DtPicker({
			"type": "date"
		});
		picker.show(function(rs) {
			$("#beginTime").val(rs.text);
			queryJson.begintime = rs.text;
			picker.dispose();
		});
	});
	$("#endTime").val("");
	$("#endTime").on('click', function() {
		var picker = new mui.DtPicker({
			"type": "date"
		});
		picker.show(function(rs) {
			$("#endTime").val(rs.text);
			queryJson.endtime = rs.text;
			picker.dispose();
		});
	});
	//动态添加HTML 按钮，监听方法如下
	$('.mui-table-view').on('click', '.process', function() {

		var url = $(this).data("apppkurl");
		window.location.href = url;
	});

	$("#query").on('click', function() {
		queryJson.method = "query";
		$(".li_mulct").remove();
		if(queryJson.endtime == "" || queryJson.begintime == "") {
			mui.alert("请选择开始时间和结束时间")
		}

		queryJson.status = $("#select_status").find("option:selected").text();

		var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByTime';

		var postData = JSON.stringify(queryJson);
		$.post(url, {
			submitData: postData
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
				var url = "";
				if(content.a_status == "待分配") {
					url = "../Distribution/Distribution.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
					string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
					string_html += " font-weight:bold;'> ";
					string_html += "分配 "
					string_html += "</button></td></tr>";
				}
				if(content.a_status == "待维修") {
					url = "../Repair/Repair.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
					string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
					string_html += " font-weight:bold;'> ";
					string_html += "维修 "
					string_html += "</button></td></tr>";
				}
				if(content.a_status == "待确定") {
					url = "../Confirm/Confirm.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
					string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
					string_html += " font-weight:bold;'> ";
					string_html += "确定 "
					string_html += "</button></td></tr>";
				}
				if(content.a_status == "退回") {
					url = "../Repair/Repair.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
					string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
					string_html += " font-weight:bold;'> ";
					string_html += "重新维修 "
					string_html += "</button></td></tr>";
				}
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
			type: "qrCode", //type为qrCode或者barCode
			onSuccess: function(data) {
				queryJson.equip_pk = data.text;

				var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByTime';

				var postData = JSON.stringify(queryJson);
				$.post(url, {
					submitData: postData
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
						var url = "";
						if(content.a_status == "待分配") {
							url = "../Distribution/Distribution.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
							string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
							string_html += " font-weight:bold;'> ";
							string_html += "分配 "
							string_html += "</button></td></tr>";
						}
						if(content.a_status == "待维修") {
							url = "../Repair/Repair.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
							string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
							string_html += " font-weight:bold;'> ";
							string_html += "维修 "
							string_html += "</button></td></tr>";
						}
						if(content.a_status == "待确定") {
							url = "../Confirm/Confirm.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
							string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
							string_html += " font-weight:bold;'> ";
							string_html += "确定 "
							string_html += "</button></td></tr>";
						}
						if(content.a_status == "退回") {
							url = "../Repair/Repair.jsp?dd_nav_bgcolor=FF5E97F6&apppk=" + content.a_apppk;
							string_html += "<tr><td colspan='2' align='center' ><button  class='process' data-apppkurl='" + url + "' style='width: 100%;font-size: larg; ";
							string_html += " font-weight:bold;'> ";
							string_html += "重新维修 "
							string_html += "</button></td></tr>";
						}
						string_html += "</table></div></li>";
						++num;
						$(".mui-table-view").append(string_html.replace("undefined", ""));
					});
					$("#showNum").show();
					$("#showNum th").text("共计：" + num + "条记录");
				});

			},
			onFail: function(err) {}
		});

	});

});