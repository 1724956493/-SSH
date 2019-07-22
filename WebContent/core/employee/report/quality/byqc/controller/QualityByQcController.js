Ext.define("core.employee.report.quality.byqc.controller.QualityByQcController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			
		"QualityByQcGrid " : {
			itemdblclick:function(view,record,item,index,e,eOpts ){	
				var begindate = Ext.util.Format.date(Ext.getCmp('qualitybyqc_report_startd').getValue(),'Y-m-d');
				var enddate = Ext.util.Format.date(Ext.getCmp('qualitybyqc_report_endd').getValue(),'Y-m-d');
				var operate = record.get('pk_psndoc');
				
				var str = '{"begindate":'+begindate+',"enddate":'+enddate+',"operate":'+operate+',"type":0}';
				
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
				
		"QualityByQcGrid button[ref=search]" : {
				click : function(_btn) {	
					var _grid = _btn.up("QualityByQcGrid");	
					var startdate = Ext.getCmp('qualitybyqc_report_startd').getValue();
					var enddate = Ext.getCmp('qualitybyqc_report_endd').getValue();
					var reportstore =  _grid.getStore();
					reportstore.getProxy().extraParams = { startdate : startdate , enddate : enddate , type : 0};
					reportstore.load();
				}}		   			
			
		
	})},
	views : ["core.employee.report.quality.byqc.view.QualityByQcReport","core.employee.report.quality.byqc.view.QualityByQcGrid",
			"core.employee.report.quality.byqc.view.QualityByQcChart","core.employee.quality.xunjian.view.QualityGrid","core.util.GridSearchText"],
	stores : ["core.employee.report.quality.byqc.store.QualityByQcStore","core.employee.quality.xunjian.store.QualityStore"],
	models : ["core.employee.report.quality.byqc.model.QualityByQcModel"]
});