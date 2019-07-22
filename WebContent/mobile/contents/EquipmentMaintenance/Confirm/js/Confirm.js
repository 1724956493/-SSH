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
var miniAppID="100375791";
var getsubmitData = {
	"apppk" : "",
	"confirm_userPK" : "",
	"confirm_userName" : "",
	"confirm_userPh" : "",
	"confirm_userDingID" : "",
	"confirm_note" : "",
	"confirm_status" : "",
	"confirm_return" : ""
};
var get_userJson = {
	"pkPsndoc" : "",
	"psnname" : ""
};
var repair_emplId = "";
var app_emplId = "";
var issubmitting = false;

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
			$("#mcWeiXiuUser").val(json[0].r_repair_username);
			$("#mcWeiXiuUserPhone").val(json[0].r_repair_userph);
			$("#mcBeginTime").val(json[0].r_repair_begtime);
			$("#mcEndTime").val(json[0].r_repair_endtime);
			$("#repair_note").val(json[0].r_repair_note);
			$("#mcConfirmUser").val(get_userJson.psnname);
			$("#mcConfirmPhone").val(localStorage.getItem("ClientPhone"));
			//当退回时 则向维修人发Ding消息repair_emplId app_emplId
			var num = json[0].c_confirm_return;
			if (num == null || num.length == 0)
				getsubmitData.confirm_return = 0;
			else
				getsubmitData.confirm_return = num;


			repair_emplId = json[0].r_repair_userdingid;

			app_emplId = json[0].a_app_userdingid;
			if (json[0].a_status == "待确定") {
				return;
			} else {
				$("#showButton").hide();
				$("#showStatus th").text("单据状态:    " + json[0].a_status);
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
		title : '设备维修确认',
		onSuccess : function(data) {
			var getDingID = localStorage.getItem("DingID");
			if (getDingID != null) {
				getsubmitData.confirm_userDingID = getDingID;
			} else {
				dd.biz.user.get({
					onSuccess : function(info) {
						getsubmitData.confirm_userDingID = info.emplId;
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
			"confirm_userPK" : "",
			"confirm_userName" : "",
			"confirm_userPh" : "",
			"confirm_userDingID" : "",
			"confirm_note" : "",
			"confirm_status" : "",
			"confirm_return" : ""
		}
	}
	$("#mcAppNote").on('click', function() {
		$("#showAppNote").toggle();
	});
	$("#mcDistributeNote").on('click', function() {
		$("#showDistributeNote").toggle();
	});
	$("#mcRepairNote").on('click', function() {
		$("#showRepairNote").toggle();
	});
	$("#submitReturn").on('click', function() {

		getsubmitData.confirm_return = parseInt(getsubmitData.confirm_return) + 1;

		getsubmitData.confirm_status = "退回";
		getsubmitData.confirm_note = $("#note").val().trim();

		getsubmitData.confirm_userPK = get_userJson.pkPsndoc;

		getsubmitData.confirm_userName = get_userJson.psnname;

		getsubmitData.confirm_userPh = localStorage.getItem("ClientPhone");
		if (getsubmitData.confirm_note == null || getsubmitData.confirm_note.length == 0) {
			mui.toast('退回请填写备注说明！');
			return;
		}
		if (issubmitting) {
			mui.toast('正在提交中,请稍后……');
			return;
		} else {
			mui.toast('正在提交中,请稍后……');
			issubmitting = true;
		}

		var url = getRealPath() + '/json/dinguser_confirmApplyRepairData';

		var postData = JSON.stringify(getsubmitData);
		//alert(postData);
		$.post(url, {
			submitData : postData
		}, function(data, status) {

			if (status && data.indexOf('true') != -1) {
				//在提交的时候推送出去
				$.post(getRealPath() + '/json/dinguser_sendMessageToDingDing', {
					status : "退回",
					touser : repair_emplId, //确定 按钮 推送给申请人repair_emplId 
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
	$("#submitConfirm").on('click', function() {
		getsubmitData.confirm_status = "完成";
		getsubmitData.confirm_note = $("#note").val().trim();

		getsubmitData.confirm_userPK = get_userJson.pkPsndoc;

		getsubmitData.confirm_userName = get_userJson.psnname;

		getsubmitData.confirm_userPh = localStorage.getItem("ClientPhone");

		if (issubmitting) {
			mui.toast('正在提交中,请稍后……');
			return;
		} else {
			mui.toast('正在提交中,请稍后……');
			issubmitting = true;
		}

		var url = getRealPath() + '/json/dinguser_confirmApplyRepairData';

		var postData = JSON.stringify(getsubmitData);
		$.post(url, {
			submitData : postData
		}, function(data, status) {

			if (status && data.indexOf('true') != -1) {
				//在提交的时候推送出去
				$.post(getRealPath() + '/json/dinguser_sendMessageToDingDing', {
					status : "完成",
					touser : app_emplId, //确定 按钮 推送给申请人repair_emplId 
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