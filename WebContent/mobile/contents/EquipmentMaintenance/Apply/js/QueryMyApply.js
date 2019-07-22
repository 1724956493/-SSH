var queryJson = {
	"begintime" : "",
	"endtime" : "",
	"userPh" : "",
	"status" : "",
	"method" : "queryByPhone",
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
		show : true, //控制按钮显示， true 显示， false 隐藏， 默认true
		control : true, //是否控制点击事件，true 控制，false 不控制， 默认false
		text : '刷新', //控制显示文本，空字符串表示显示默认文本
		onSuccess : function(result) {
			window.location.reload();
		},
		onFail : function(err) {}
	});
});
window.onload = function() {
	queryJson.userPh = localStorage.getItem("ClientPhone");

	var vm = new Vue({
		el : ".mui-content",
		beforeMount : function() {
//			alert("a");
			var url = getRealPath() + '/json/dinguser_queryApplyRepairDataByTime';
			var postData = JSON.stringify(queryJson);

			this.$http.post(url, {
				submitData : postData
			}, {
				emulateJSON : true
			}).then(function(res) {

				this.docDateJson = res.data
			}, function(res) {});
		},
		updated : function() {
			$(".mui-card").show(); //防止闪现 循环模板
			window.scrollTo(0, document.body.scrollHeight); //跳转到最后
		},
		data : {
			docDateJson : [],
			href : "../DocStatus/DocStatus.jsp?dd_nav_bgcolor=FF5E97F6&apppk="
		}
	});
//	$(".mui-card").show();
}