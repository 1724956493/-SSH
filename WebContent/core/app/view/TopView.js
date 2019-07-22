/**北部*/
Ext.define("core.app.view.TopView", {
	extend:"Ext.panel.Panel",
	alias : 'widget.topview',
	id:"topview",
	height : 40,
	bodyStyle : {
		background : '#7598e0',
		padding : '80px'
	},
	layout : "absolute",
	items : [{
				x : 0,
				y : 0,
				width:230,
				//border:0,
				html : "<img src='./core/css/imgs/logo/LOGONTS.jpg'/>"
			},{
				x : 230,
				y : 0,
				width:850,
				bodyStyle : {
					background : '#7598e0',
					border:0,
					padding : '5px'
				}
			// ,	html : "<font color=white size=5>&nbsp;&nbsp;&nbsp;&nbsp;NTS信息管理系统</font>"
			},{
				x : 500,
				y : 10,
				xtype : "displayfield",
				id:"userpk",
				hidden:true
			},{
				x : 800,
				y : 10,
				ref : "logininfo",
				xtype : "displayfield",
				id:"displaylogin",
				value : "<font color=white><b>未登录</b></font>"
			}, {
				x : 1090,
				y : 10,
				xtype : "button",
				ref : "logout",
				text : "注销"
			}, {
				x : 1160,
				y : 10,
				xtype : "button",
				ref : "exit",
				text : "退出系统"
			}]
});
