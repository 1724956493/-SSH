Ext.define("core.employee.report.quality.byproject.controller.QualityByProjectController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			
		"QualityByProjectGrid " : {
			itemdblclick:function(view,record,item,index,e,eOpts ){	
				var begindate = Ext.util.Format.date(Ext.getCmp('qualitybyproject_report_startd').getValue(),'Y-m-d');
				var enddate = Ext.util.Format.date(Ext.getCmp('qualitybyproject_report_endd').getValue(),'Y-m-d');
				var project = record.get('project');
				
				var str = '{"begindate":'+begindate+',"enddate":'+enddate+',"project":'+project+',"type":0}';
				
				var _store = Ext.create('core.employee.quality.xunjian.store.QualityStore');
				_store.getProxy().extraParams ={data : str};
				_store.load();
				
				
				var _grid = Ext.create('core.employee.quality.xunjian.view.QualityGrid',{
					store:_store					
				});
				
				var win =  Ext.create('Ext.window.Window', {
					title:"查询信息",
					height: 400,
				    width: 800,
				    layout: 'fit',
				    modal :true
				});
				
				win.add(_grid);
				win.show();				
				}},
			

		"QualityByProjectGrid button[ref=search]" : {
				click : function(_btn) {	
					var _grid = _btn.up("QualityByProjectGrid");	
					var startdate = Ext.getCmp('qualitybyproject_report_startd').getValue();
					var enddate = Ext.getCmp('qualitybyproject_report_endd').getValue();
					var reportstore =  _grid.getStore();
					reportstore.getProxy().extraParams = { startdate : startdate , enddate : enddate , type : 0};
					reportstore.load();
				}}		   			
			
		
	})},
	views : ["core.employee.report.quality.byproject.view.QualityByProjectReport","core.employee.report.quality.byproject.view.QualityByProjectGrid",
			"core.employee.report.quality.byproject.view.QualityByProjectChart","core.employee.quality.xunjian.view.QualityGrid","core.util.GridSearchText"],
	stores : ["core.employee.report.quality.byproject.store.QualityByProjectStore","core.employee.quality.xunjian.store.QualityStore"],
	models : ["core.employee.report.quality.byproject.model.QualityByProjectModel"]
});