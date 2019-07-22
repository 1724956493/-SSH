dd.config({
	agentId: _config.agentid,
	corpId: _config.corpId,
	timeStamp: _config.timeStamp,
	nonceStr: _config.nonceStr,
	signature: _config.signature,
	jsApiList: ['runtime.info', 'biz.contact.choose',
		'device.notification.confirm', 'device.notification.alert',
		'device.notification.prompt', 'biz.ding.post',
		'biz.util.openLink', 'biz.util.open', 'biz.chat.openSingleChat',
		'biz.chat.pickConversation', 'biz.contact.choose',
		'biz.user.get', 'biz.chat.chooseConversationByCorpId'
	]
});
var getsubmitData = new Object();
var emplId = "";
var issubmitting = false;
var miniAppID = "100375791";
getsubmitData.equipPK = null;
getsubmitData.break_time = null;
getsubmitData.app_time = null;
getsubmitData.app_userPk = null;
getsubmitData.app_userPh = null;
getsubmitData.app_userName = null;
getsubmitData.app_userDingID = null;
getsubmitData.image = null;
getsubmitData.note = null;
getsubmitData.app_toworkername = null
dd.ready(function() {
	FastClick.attach(document.body);
	//设置导航栏右侧
	dd.biz.navigation.setMenu({
		backgroundColor: "#ADD8E6",
		textColor: "#ADD8E611",
		items: [{
			"id": "0", //字符串
			"iconId": "scan", //字符串，图标命名
			"text": "查询"
		}, {
			"id": "1", //字符串
			"iconId": "add", //字符串，图标命名
			"text": "更多"
		}],
		onSuccess: function(data) {
			switch(data.id) {
				case '0':
					mui('#topPopover').popover('hide');
					scan();
					break;
				case '1':
					mui('#topPopover').popover('toggle');
					break;
				default:
					break;
			}
		},
		onFail: function(err) {}
	});

	//监听返回键
	document.addEventListener('backbutton', function() {
		//清空提交数据及页面数据
		clearData();

	});
	dd.biz.navigation.setLeft({
		show: true, //控制按钮显示， true 显示， false 隐藏， 默认true
		control: true, //是否控制点击事件，true 控制，false 不控制， 默认false
		showIcon: true, //是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
		text: '返回', //控制显示文本，空字符串表示显示默认文本
		onSuccess: function(result) {
			//清空提交数据及页面数据
			clearData();
		},
		onFail: function(err) {}
	});

	dd.biz.navigation.setTitle({
		//接收全局变量title
		title: '设备维修申请',
		onSuccess: function(data) {},
		onFail: function(err) {
			log.e(JSON.stringify(err));
		}
	});

	//判断session
	//var getsession_userPhone = <%=session.getAttribute("userPhone")%>;
/*	if(!getsession_userPhone) {
		mui.alert("登陆超时，请重新登陆", "提示", function() {
			dd.ui.nav.close({
				onSuccess: function() {},
				onFail: function() {}
			});
			window.opener = null;
			window.open("about:blank", "_self");
			window.close();
		});
	}*/

	function clearData() {
		//清空提交数据及页面数据
		getsubmitData.equipPK = null;
		getsubmitData.break_time = null;
		getsubmitData.app_time = null;
		getsubmitData.app_userPk = null;
		getsubmitData.app_userPh = null;
		getsubmitData.app_userName = null;
		getsubmitData.app_userDingID = null;
		getsubmitData.image = null;
		getsubmitData.note = null;
		$("#li_send_span").html("分配:");
		$("#note").val("");
		$(".up-img").attr('src', "");
		$(".file").val("");
		$(".up-section").hide();
		$('#mcNm').val("");
		$('#mcCd').val("");
		$('#mcGuige').val("");
		$('#mcXinghao').val("");
		$('#mcXianchangshibiema').val("");
		$('#mcShengQingUser').val("");
		$('#mcShengQingUserPhone').val("");
		$("#mcGuZhangTime").val("");
		$("#mcBaoXiuTime").val("");
		emplId = "";
	}
	//下拉菜单，监听
	$(".mui-table-view-cell").on('click', function() {
		mui('#topPopover').popover('hide');
		switch($(this).attr('id')) {
			case '0':
				window.location.href = "QueryMyApply.jsp?dd_nav_bgcolor=FF5E97F6";
				break;
			case '1':
				window.location.reload();
				break;
			default:
				break;
		}
	});

	function scan() {
		clearData();
		var getDingID = localStorage.getItem("DingID");
		if(getDingID != null) {
			getsubmitData.app_userDingID = getDingID;
		} else {
			dd.biz.user.get({
				onSuccess: function(info) {
					localStorage.setItem("DingID", info.emplId);
					getsubmitData.app_userDingID = info.emplId;
				},
				onFail: function(err) {
					alert(JSON.stringify(err));
				}
			});
		}
		dd.biz.util.scan({
			type: "qrCode", //type为qrCode或者barCode
			onSuccess: function(data) {
				/*  data结构
				   { 'text': String} */
				var getdata = data.text;
				var url = getRealPath() + '/json/pamequip_toJsonweb';
				$.post(url, {
					pkEquip: getdata
				}, function(data, status) {
					if(data.indexOf("false") == 0) {
						mui.alert("没有查找到此设备");
					}
					if(status && data.indexOf("<html>") == -1) {
						var getInfoObj = jQuery.parseJSON(data);
						getsubmitData.equipPK = getdata; //用于存储提交信息
						$('#mcNm').val(getInfoObj.EquipName);
						$('#mcCd').val(getInfoObj.EquipCode);
						$('#mcGuige').val(getInfoObj.EquipModel);
						$('#mcXinghao').val(getInfoObj.EquipSpec);
						$('#mcXianchangshibiema').val(getInfoObj.Equipdepcode);
						getsubmitData.app_userPk = getsession_userJson.pkPsndoc;
						var getClientPhone = localStorage.getItem("ClientPhone");
						getsubmitData.app_userPh = getClientPhone;
						$('#mcShengQingUser').val(getsession_userJson.psnname);
						getsubmitData.app_userName = getsession_userJson.psnname;
						$('#mcShengQingUserPhone').val(getClientPhone);

					} else {
						mui.alert("网络连接出错,请重试");
					}
				});
			},
			onFail: function(err) {}
		})

	}
	$("#mcGuZhangTime").on('click', function() {
		var picker = new mui.DtPicker();
		picker.show(function(rs) {
			$("#mcGuZhangTime").val(rs.text);
			getsubmitData.break_time = rs.text;

			picker.dispose();
		});
	});

	$("#mcBaoXiuTime").on('click', function() {
		var picker = new mui.DtPicker();
		picker.show(function(rs) {
			$("#mcBaoXiuTime").val(rs.text);
			getsubmitData.app_time = rs.text;
			picker.dispose();
		});
	});
	$("#li_send").on('click', function() {
		//发工作通知
		dd.biz.contact.choose({
			startWithDepartmentId: -1,
			multiple: true,
			users: [],
			disabledUsers: [],
			corpId: _config.corpId,
			max: 3,
			limitTips: "超过限定人数",
			isNeedSearch: true,
			title: "选择联系人",
			local: "true",
			onSuccess: function(data) {
				emplId = "";
				var emplName = "";
				$.each(data, function(index, content) {
					emplId += content.emplId + "|";
					emplName += content.name + "，";
				});
				emplName = emplName.substr(0, emplName.length - 1);
				var addSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				$("#li_send_span").html("推送:" + addSpace + emplName);
				emplId = emplId.substr(0, emplId.length - 1);
				getsubmitData.app_toworkername = emplName + "&" + emplId;
			},
			onFail: function(err) {}
		});
		/* 发消息
		dd.biz.chat.pickConversation({
			corpId : _config.corpId, //企业id
			isConfirm : 'true', //是否弹出确认窗口，默认为true
			onSuccess : function(data) {
				dd.biz.user.get({
					onSuccess : function(info) {
						$.post(getRealPath() + '/json/dinguser_sendMessageToDingDing', {
							sender : info.id,
							cid : data.cid,
							accessToken : _config.accessToken,
							content : 'ssss'
						}, function(data, status) {}, 'text');
					},
					onFail : function(err) {
						alert(JSON.stringify(err));
					}
				});
			},
			onFail : function(err) {
				alert(JSON.stringify(err));
			}
		});*/
	});

	$("#submitData").on('click', function() {
		getsubmitData.note = $("#note").val().trim();
		if(getsubmitData.equipPK == null) {
			mui.alert("请先扫描或查询设备数据");
			issubmitting = false;
			return;
		}
		if(getsubmitData.break_time === null) {
			mui.alert("请选择故障时间");
			issubmitting = false;
			return;
		}
		if(getsubmitData.app_time === null) {
			mui.alert("请选择报修时间");
			issubmitting = false;
			return;
		}

		if(emplId.length < 2) {
			mui.alert("请选择推送人员");
			issubmitting = false;
			return;
		}
		if(getsubmitData.note == "") {
			mui.alert("请备注故障状态");
			issubmitting = false;
			return;
		}
		if(issubmitting) {
			mui.toast('正在提交中,请稍后……');
			return;
		} else {
			mui.toast('正在提交中,请稍后……');
			issubmitting = true;
		}
		if(getsubmitData.image == null) {
			getsubmitData.image = "";
		}

		var url = getRealPath() + '/json/dinguser_addApplyRepairData';

		var postData = JSON.stringify(getsubmitData);
		$.post(url, {
			submitData: postData
		}, function(data, status) {
			var isSuccess = JSON.parse(data);
			if(status && isSuccess.success) {
				//在提交的时候推送出去
				$.post(getRealPath() + '/json/dinguser_sendMessageToDingDing', {
					status: "申请",
					touser: emplId,
					accessToken: _config.accessToken,
					agentid: miniAppID, // 微应用ID
					content: getsession_userJson.psnname,
					single_url: getRealPath(),
					apppk: isSuccess.msg
				}, function(data, status) {
					var equip = {
						"name": $('#mcNm').val().trim(),
						"code": $('#mcCd').val().trim()
					}
					$.post(getRealPath() + '/json/dinguser_sendMessageToDingDing', {
						status: "我的申请",
						touser: getsubmitData.app_userDingID,
						accessToken: _config.accessToken,
						agentid: miniAppID, // 微应用ID
						content: JSON.stringify(equip),
						single_url: getRealPath(),
						apppk: isSuccess.msg
					}, function(data, status) {
						//清空提交数据及页面数据
						mui.alert("提交成功！", clearData());
						clearData();
						issubmitting = false;
					});
				});
			} else {
				mui.alert("网络故障,提交失败,请重新提交");
				issubmitting = false;
				return;
			}
		});

	});

});