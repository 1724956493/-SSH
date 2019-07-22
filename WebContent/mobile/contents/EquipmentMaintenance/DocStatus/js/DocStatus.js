
var queryJson = {
	"apppk" : "",
	"userPh" : ""
}
//获取apppk
queryJson.apppk = $.query.get("apppk");

dd.ready(function() {
	dd.biz.navigation.setTitle({
		//接收全局变量title
		title : '单据当前状态',
		onSuccess : function(data) {},
		onFail : function(err) {
			log.e(JSON.stringify(err));
		}
	});
});
window.onload = function() {
	queryJson.userPh = localStorage.getItem("ClientPhone");
	var vm = new Vue({
		el : "#box",
		beforeMount : function() {
			var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByPK';

			$.post(url, {
				apppk : queryJson.apppk
			}, function(data, status) {

				var json1 = JSON.parse(data);
				var json = json1[0];
				this.nowdata.status = json.a_status;
				switch (json.a_status) {
				case "待分配":
					this.nowdata.toworkers = json.a_app_toworkername;
					break;
				case "待维修":
					this.nowdata.toworkers = json.a_distribution_toworkername;
					break;
				case "待确定":
					this.nowdata.toworkers = json.r_repair_toworkername;
					break;
				case "退回":
					this.nowdata.toworkers = json.r_repair_username;
					break;
				}

				this.nowdata.isreturn = json.c_confirm_return;

			}.bind(this));
		},
		data : {
			base : [ "申请", "待分配 ", "分配完成 ", "待维修", "维修完成 ", "待确定 " ],
			forbase : [ "退回", "待维修", "维修完成 ", "待确定 " ],
			endbase : [ "确定" ],
			nowdata : {
				status : "",
				toworkers : "",
				isreturn : ""
			}
		},
		computed : {
			computebaseData : function() {

				var getBaseData = this.base;
				var getforBaseData = this.forbase;
				var getendBaseData = this.endbase;
				var getDemo = this.nowdata;
				var nowStatus = getDemo.status;
				var nowToworkers = getDemo.toworkers.split("|")[0].split("&")[0];
				var arr = [];
				var getThis = this;
				var j = 0;
				if (nowStatus.length == 0) {

					return arr;
				}
				if (getDemo.isreturn.length > 0) {

					for (var i = 0; i < parseInt(getDemo.isreturn) - 1; i++) {

						getBaseData = getBaseData.concat(getforBaseData);
						console.log(getBaseData);
					}
					for (var i = 0; i < getBaseData.length; i++) {
						if (getBaseData[i].indexOf("待") != -1) {
							arr.push({
								num : "",
								name : "",
								ifshow : false
							});
						} else {
							++j;
							arr.push({
								num : j + ".",
								name : getBaseData[i],
								ifshow : true
							});
						}

					}
					for (var i = 0; i < getforBaseData.length; i++) {
						if (getforBaseData[i].indexOf("待") != -1) {
							if (getforBaseData[i].trim() === nowStatus.trim()) {
								arr.push({
									num : "",
									name : nowStatus.trim() + "  (" + nowToworkers + ")",
									ifshow : false
								});
							} else {
								arr.push({
									num : "",
									name : "",
									ifshow : false
								});
							}
						} else {
							++j;
							arr.push({
								num : j + ".",
								name : getforBaseData[i],
								ifshow : true
							});
						}
						if (getforBaseData[i].trim() === nowStatus.trim()) {

							break;
						}
					}

				} else {

					for (var i = 0; i < getBaseData.length; i++) {

						if (getBaseData[i].indexOf("待") != -1) {
							if (getBaseData[i].trim() === nowStatus.trim()) {
								arr.push({
									num : "",
									name : nowStatus.trim() + "  (" + nowToworkers + ")",
									ifshow : false
								});
							} else {
								arr.push({
									num : "",
									name : "",
									ifshow : false
								});
							}
						} else {
							++j;
							arr.push({
								num : j + ".",
								name : getBaseData[i],
								ifshow : true
							});
						}
						if (getBaseData[i].trim() === nowStatus.trim()) {

							break;
						}
					}
				}
				if (nowStatus === "确定")
					arr.push({
						num : j + 1 + ".",
						name : getendBaseData[0],
						ifshow : true
					});
				if (nowStatus === "退回") {

					arr.push({
						num : "",
						name : "待重维修 (" + nowToworkers + ")",
						ifshow : false
					});
				}
				return arr;

			}
		}
	});
}