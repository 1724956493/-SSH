/**主控制器*/
Ext.define("core.equipment.controller.EquipmentController",{
	extend : "Ext.app.Controller",
/*	mixins : {
		formUtils : "core.util.FormUtils"
	},*/
	init : function(){
		var self = this;
		this.control({
			"DeptTreeView" : {
				itemclick : function(_view, record, item, index, e, eOpts) {
					var _panel = _view.up("EquipmentLayout").down("EquipmentCheckReport");
					var startdate = Ext.getCmp('equip_report_startd').getValue();
					var enddate = Ext.getCmp('equip_report_endd').getValue();
			//		Ext.Msg.alert(Ext.util.Format.date(startdate,'Y-m-d')+":"+Ext.util.Format.date(enddate,'Y-m-d'));
					var reportstore =  Ext.getStore("core.equipment.store.EquipReportStore");
					reportstore.getProxy().extraParams = {deptpk : record.get('id') , startdate : startdate , enddate : enddate};
					reportstore.load();					
				}},
				
			"EquipmentCheckReport button[ref=find]" : {
				click : function(_btn) {	
					var _treepanel = _btn.up("EquipmentLayout").down("DeptTreeView");
					var record = _treepanel.getSelectionModel().getSelection();
			//		Ext.Msg.alert("123",record[0].get('id'));
					if(record.length!=0)
						{
					var _panel = _btn.up("EquipmentCheckReport");	
					var startdate = Ext.getCmp('equip_report_startd').getValue();
					var enddate = Ext.getCmp('equip_report_endd').getValue();
					var reportstore =  Ext.getStore("core.equipment.store.EquipReportStore");
					reportstore.getProxy().extraParams = {deptpk : record[0].get('id') , startdate : startdate , enddate : enddate};
					reportstore.load();}
					else{
						Ext.Msg.alert("警告","请至少先选择一个部门")
					}
				}}		   
		})
	},
		
	views : [
	    "core.equipment.view.EquipmentLayout",
	    "core.equipment.view.EquipmentCheckGrid",
	    "core.equipment.view.EquipmentGridView",
	    "core.equipment.view.DeptTreeView",
	    "core.equipment.view.EquipmentCheckReport"
	],
	stores : ["core.equipment.store.EquipmentStore","core.equipment.store.DeptTreeStore","core.equipment.store.EquipReportStore"],
	models : ["core.equipment.model.EquipmentModel","core.equipment.model.EquipmentReportModel"]
});