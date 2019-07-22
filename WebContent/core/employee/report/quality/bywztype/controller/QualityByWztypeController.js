Ext.define("core.employee.report.quality.bywztype.controller.QualityByWztypeController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			
		"QualityByWztypeGrid " : {
			itemdblclick : function(view,record,item,index,e,eOpts ){	
				var begindate = Ext.util.Format.date(Ext.getCmp('qualitybywztype_report_startd').getValue(),'Y-m-d');
				var enddate = Ext.util.Format.date(Ext.getCmp('qualitybywztype_report_endd').getValue(),'Y-m-d');
				var bonus = record.get('uuid');
				
				var str = '{"begindate":'+begindate+',"enddate":'+enddate+',"bonus":'+bonus+',"type":0}';
				
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
			
		"QualityByWztypeGrid button[ref=search]" : {
				click : function(_btn) {	
					var _grid = _btn.up("QualityByWztypeGrid");	
					var startdate = Ext.getCmp('qualitybywztype_report_startd').getValue();
					var enddate = Ext.getCmp('qualitybywztype_report_endd').getValue();
					var reportstore =  _grid.getStore();
					reportstore.getProxy().extraParams = { startdate : startdate , enddate : enddate , type : 0};
					reportstore.load();
				}}		   			
			
		
	})},
	views : ["core.employee.report.quality.bywztype.view.QualityByWztypeReport","core.employee.report.quality.bywztype.view.QualityByWztypeGrid",
			"core.employee.report.quality.bywztype.view.QualityByWztypeChart","core.employee.quality.xunjian.view.QualityGrid","core.util.GridSearchText"],
	stores : ["core.employee.report.quality.bywztype.store.QualityByWztypeStore","core.employee.quality.xunjian.store.QualityStore"],
	models : ["core.employee.report.quality.bywztype.model.QualityByWztypeModel"]
});