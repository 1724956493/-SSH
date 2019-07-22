Ext.onReady(function(){
	Ext.QuickTips.init();
//	Ext.require('core.employee.controller.EmployeeController');
	Ext.Loader.setConfig({
		enabled:true
	});
	Ext.Loader.setPath('Ext.ux', './js/ext-4.2/ux');
	Ext.application({
		name : 'core',  //应用的名字
		appFolder : "core",  //应用的目录
		launch:function(){   //当前页面加载完执行的函数
			Ext.create("Ext.container.Viewport",{
				layout:'fit',
				border:0,
			    items:[{xtype : "LoginWin"},{xtype : "Mainviewlayout",hidden:true}]
			//	items:[{xtype : "Mainviewlayout",hidden:false}]
			})			
		},
		controllers:[
			"core.app.controller.MainController"
		]
	});
	
})