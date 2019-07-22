Ext.define("core.employee.report.quality.bydept.controller.QualityByDeptController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({

		"QualityByDeptGrid " : {
			beforedestroy : function(_grid){
				_grid.getStore().removeAll();}		
		},				
				
				
		"QualityByDeptGrid button[ref=search]" : {
				click : function(_btn) {	
					var _grid = _btn.up("QualityByDeptGrid");	
					var startdate = Ext.getCmp('qualitybydept_report_startd').getValue();
					var enddate = Ext.getCmp('qualitybydept_report_endd').getValue();
					var reportstore =  _grid.getStore();
					reportstore.getProxy().extraParams = { startdate : startdate , enddate : enddate , type : 0};
					reportstore.load();
				}}		   			
			
		
	})},
	views : ["core.employee.report.quality.bydept.view.QualityByDeptReport","core.employee.report.quality.bydept.view.QualityByDeptGrid",
			"core.employee.report.quality.bydept.view.QualityByDeptChart","core.employee.quality.xunjian.view.QualityGrid","core.util.GridSearchText"],
	stores : ["core.employee.report.quality.bydept.store.QualityByDeptStore","core.employee.quality.xunjian.store.QualityStore"],
	models : ["core.employee.report.quality.bydept.model.QualityByDeptModel"]
});