Ext.define("core.employee.report.quality.byempleader.controller.QualityByEmpleaderController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			
		"QualityByEmpleaderGrid " : {
			itemdblclick:function(view,record,item,index,e,eOpts ){	
				var begindate = Ext.util.Format.date(Ext.getCmp('qualitybyempleader_report_startd').getValue(),'Y-m-d');
				var enddate = Ext.util.Format.date(Ext.getCmp('qualitybyempleader_report_endd').getValue(),'Y-m-d');
				var empleader = record.get('empleader');
				
				var str = '{"begindate":'+begindate+',"enddate":'+enddate+',"empleader":'+empleader+',"type":0}';
				
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
/*					var _grid = _form.up("EmprpView").down("EmprpGrid");
					var data =_form.getForm().getValues();
					data.type = 0;
					var _store = _grid.getStore();			
					_store.getProxy().extraParams ={data : JSON.stringify(data)};
					_store.load();	*/		
				
				}},
		

				
		"QualityByEmpleaderGrid button[ref=search]" : {
				click : function(_btn) {	
					var _grid = _btn.up("QualityByEmpleaderGrid");	
					var startdate = Ext.getCmp('qualitybyempleader_report_startd').getValue();
					var enddate = Ext.getCmp('qualitybyempleader_report_endd').getValue();
					var reportstore =  _grid.getStore();
					reportstore.getProxy().extraParams = { startdate : startdate , enddate : enddate , type : 0};
					reportstore.load();
				}}		   			
			
		
	})},
	views : ["core.employee.report.quality.byempleader.view.QualityByEmpleaderReport","core.employee.report.quality.byempleader.view.QualityByEmpleaderGrid",
			"core.employee.report.quality.byempleader.view.QualityByEmpleaderChart","core.employee.quality.xunjian.view.QualityGrid","core.util.GridSearchText"],
	stores : ["core.employee.report.quality.byempleader.store.QualityByEmpleaderStore","core.employee.quality.xunjian.store.QualityStore"],
	models : ["core.employee.report.quality.byempleader.model.QualityByEmpleaderModel"]
});