dd.config({
	agentId : _config.agentid,
	corpId : _config.corpId,
	timeStamp : _config.timeStamp,
	nonceStr : _config.nonceStr,
	signature : _config.signature,
	jsApiList : [ 'runtime.info', 'biz.contact.choose',
		'device.notification.confirm', 'device.notification.alert',
		'device.notification.prompt', 'biz.ding.post',
		'biz.util.openLink', 'biz.util.open', 'biz.chat.openSingleChat',
		'biz.chat.pickConversation', 'biz.contact.choose',
		'biz.user.get', 'biz.chat.chooseConversationByCorpId'
	]
});

var getsubmitData = {
	"apppk" : "",
	"repair_begTime" : "",
	"repair_endTime" : "",
	"repair_userPK" : "",
	"repair_userName" : "",
	"repair_userPh" : "",
	"repair_userDingID" : "",
	"repair_note" : "",
	"repair_toworkerName" : ""
};
var get_userJson = {
	"pkPsndoc" : "",
	"psnname" : ""
};
var emplId = "";
var issubmitting = false;
var miniAppID="100375791";
getsubmitData.apppk = $.query.get("apppk");

dd.ready(function() {

	FastClick.attach(document.body);
	//监听返回键
	document.addEventListener('backbutton', function() {
		//清空提交数据及页面数据
		clearData();

	});

	var getUserJsonUrl = getRealPath() + '/json/dinguser_query_bd_psndocByPhone';
	$.post(getUserJsonUrl, {
		userPhone : localStorage.getItem("ClientPhone")
	}, function(data, status) {
		var json = JSON.parse(data);
		get_userJson.pkPsndoc = json[0].a_pk_psndoc;
		get_userJson.psnname = json[0].a_psnname;
		var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByPK';
		$.post(url, {
			apppk : getsubmitData.apppk
		}, function(data, status) {
			var json = JSON.parse(data);
			$('#mcNm').val(json[0].p_equip_name);
			$('#mcCd').val(json[0].p_equip_code);
			$('#mcGuige').val(json[0].model);
			$('#mcXinghao').val(json[0].p_spec);
			$('#mcXianchangshibiema').val(json[0].p_def3);
			$('#mcShengQingUser').val(json[0].a_app_username);
			$('#mcShengQingUserPhone').val(json[0].a_app_userph);
			$("#mcGuZhangTime").val(json[0].a_break_time);
			$("#mcBaoXiuTime").val(json[0].a_app_time);
			$("#app_note").val(json[0].a_note);
			$("#distribute_note").val(json[0].a_distribution_note);
			$("#mcFenPeiUser").val(json[0].a_distribution_username);
			$("#mcFenPeiUserPhone").val(json[0].a_distribution_userph);
			$("#mcFenPeiUserTime").val(json[0].a_distribution_ts);



			$("#mcWeiXiuUser").val(get_userJson.psnname);
			$("#mcWeiXiuUserPhone").val(localStorage.getItem("ClientPhone"));
			//默认推送确定人为申请人
			var addSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			$("#li_send_span").html("维修确认:" + addSpace + json[0].a_app_username);
			emplId = json[0].a_app_userdingid;
			getsubmitData.repair_toworkerName = json[0].a_app_username + "&" + emplId;
			var toworkername = json[0].r_repair_toworkername;
			if (toworkername.length > 0)
				toworkername = toworkername.substring(0, toworkername.indexOf("&"));


			if (json[0].a_status == "待维修") {
				return;
			}

			if (json[0].a_status == "待确定") {
				var addSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				$("#li_send_span").html("维修确认:" + addSpace + toworkername);
				var btnArray = [ '重新维修', '取消' ];
				mui.confirm('该申请设备已维修,是否重新维修？', '提示', btnArray, function(e) {
					if (e.index == 1) {
						$("#submitData").hide();
						$("#showStatus th").text("单据状态:    " + json[0].a_status);
						$("#showStatus").show();
					}
				});
			} else if (json[0].a_status == "退回") {
				$("#mcReturnUser").val(json[0].c_confirm_username);
				$("#mcReturnPhone").val(json[0].c_confirm_userph);
				$("#mcReturnTime").val(json[0].c_confirm_ts);
				$("#confirm_note").val(json[0].c_confirm_note);
				$(".isretrun").show();
				var addSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				$("#li_send_span").html("维修确认:" + addSpace + toworkername);
				var btnArray = [ '重新维修', '取消' ];
				mui.confirm('该申请维修完成被退回,是否重新维修？', '提示', btnArray, function(e) {
					if (e.index == 1) {
						$("#submitData").hide();
						$("#showStatus th").text("单据状态:    " + json[0].a_status);
						$("#showStatus").show();
					}
				});
			} else {
				$("#submitData").hide();
				$("#showStatus th").text("单据状态:     " + json[0].a_status);
				$("#showStatus").show();
			}
		});
	});

	dd.biz.navigation.setLeft({
		show : true, //控制按钮显示， true 显示， false 隐藏， 默认true
		control : true, //是否控制点击事件，true 控制，false 不控制， 默认false
		showIcon : true, //是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
		text : '返回', //控制显示文本，空字符串表示显示默认文本
		onSuccess : function(result) {
			//清空提交数据及页面数据
			clearData();
		},
		onFail : function(err) {}
	});

	dd.biz.navigation.setTitle({
		//接收全局变量title
		title : '设备维修',
		onSuccess : function(data) {
			var getDingID = localStorage.getItem("DingID");
			if (getDingID != null) {
				getsubmitData.repair_userDingID = getDingID;
			} else {
				dd.biz.user.get({
					onSuccess : function(info) {
						getsubmitData.repair_userDingID = info.emplId;
					},
					onFail : function(err) {
						alert(JSON.stringify(err));
					}
				});
			}
		},
		onFail : function(err) {
			log.e(JSON.stringify(err));
		}
	});

	function clearData() {
		//清空提交数据及页面数据
		$(".clear").val("");
		$("#li_send_span").html("维修确认:");
		getsubmitData = {
			"apppk" : "",
			"repair_begTime" : "",
			"repair_endTime" : "",
			"repair_userPK" : "",
			"repair_userName" : "",
			"repair_userPh" : "",
			"repair_userDingID" : "",
			"repair_note" : "",
			"repair_toworkerName" : ""
		}
	}

	$("#li_send").on('click', function() {
		//发工作通知
		dd.biz.contact.choose({
			startWithDepartmentId : -1,
			multiple : true,
			users : [],
			disabledUsers : [],
			corpId : _config.corpId,
			max : 3,
			limitTips : "超过限定人数",
			isNeedSearch : true,
			title : "选择联系人",
			local : "true",
			onSuccess : function(data) {
				emplId = "";
				var emplName = "";
				$.each(data, function(index, content) {
					emplId += content.emplId + "|";
					emplName += content.name + "，";
				});
				emplName = emplName.substr(0, emplName.length - 1);
				var addSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				$("#li_send_span").html("维修确认:" + addSpace + emplName);
				emplId = emplId.substr(0, emplId.length - 1);
				getsubmitData.repair_toworkerName = emplName + "&" + emplId;

			},
			onFail : function(err) {}
		});

	});

	$("#mcAppNote").on('click', function() {
		$("#showAppNote").toggle();
	});
	$("#mcDistributeNote").on('click', function() {
		$("#showDistributeNote").toggle();
	});
	$("#mcConfirmNote").on('click', function() {
		$("#showConfirmNote").toggle();
	});
	$("#mcBeginTime").on('click', function() {
		var picker = new mui.DtPicker();
		picker.show(function(rs) {
			$("#mcBeginTime").val(rs.text);
			getsubmitData.repair_begTime = rs.text;

			picker.dispose();
		});
	});

	$("#mcEndTime").on('click', function() {
		var picker = new mui.DtPicker();
		picker.show(function(rs) {
			$("#mcEndTime").val(rs.text);
			getsubmitData.repair_endTime = rs.text;
			picker.dispose();
		});
	});
	$("#submitData").on('click', function() {
		getsubmitData.repair_note = $("#note").val().trim();

		getsubmitData.repair_userPK = get_userJson.pkPsndoc;

		getsubmitData.repair_userName = get_userJson.psnname;


		getsubmitData.repair_userPh = localStorage.getItem("ClientPhone");

		if (getsubmitData.repair_toworkerName.length < 2) {
			mui.alert("请选择确定人员");
			issubmitting = false;
			return;
		}
		if (getsubmitData.repair_userPK.length < 2) {
			mui.alert("未能获取到分配人员ID");
			issubmitting = false;
			return;
		}

		if (getsubmitData.repair_begTime.length < 2) {
			mui.alert("请选择开始时间");
			issubmitting = false;
			return;
		}
		if (getsubmitData.repair_endTime.length < 2) {
			mui.alert("请选择结束时间");
			issubmitting = false;
			return;
		}

		if (issubmitting) {
			mui.toast('正在提交中,请稍后……');
			return;
		} else {
			mui.toast('正在提交中,请稍后……');
			issubmitting = true;
		}

		var url = getRealPath() + '/json/dinguser_repairApplyRepairData';

		var postData = JSON.stringify(getsubmitData);
		$.post(url, {
			submitData : postData
		}, function(data, status) {

			if (status && data.indexOf('true') != -1) {

				//在提交的时候推送出去
				$.post(getRealPath() + '/json/dinguser_sendMessageToDingDing', {
					status : "维修",
					touser : emplId,
					accessToken : _config.accessToken,
					agentid : miniAppID, // 微应用ID
					content : get_userJson.psnname,
					single_url : getRealPath(),
					apppk : getsubmitData.apppk
				}, function(data, status) {});
				//清空提交数据及页面数据
				mui.alert("提交成功！", clearData());
				clearData();
				issubmitting = false;

			} else {
				mui.alert("网络故障,提交失败,请重新提交");
				issubmitting = false;
				return;
			}
		});

	});

});