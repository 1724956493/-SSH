Ext.define("core.employee.quality.base.psntype.controller.PsntypeController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			"PsntypeForm button[ref=add]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
					    title: '人员查询窗口',					    
					    height: 400,
					    width: 400,
					    layout: 'fit',
					    closable :false,
					    modal : true,
					    items: [
					   			 {xtype : 'EmpSearchGrid'}					    
					    ]}
					);					    
					    win.show();	
				}},
				
		   "EmpSearchGrid button[ref=search]" : {
				click : function(_btn) {
				   var tbar = _btn.up("toolbar");
				   var record = tbar.down("textfield").getValue();
				   var _grid = _btn.up("EmpSearchGrid");				
				   var _store = _grid.getStore();
		           _store.getProxy().extraParams ={psnname : record};
					_store.load();				
				}},
				
		 "EmpSearchGrid " : {
				itemdblclick:function(view,record,item,index,e,eOpts ){
					var _form = Ext.getCmp("PsntypeForm").getForm();
					_form.loadRecord(record);
					var textf = _form.getFields().items;
					for(var i=0;i<textf.length;i++){
						textf[i].setDisabled(false);
						};
					_form.findField('psnname').setReadOnly(true);
					view.up("window").close();					
				}},
				
		 "PsntypeForm button[ref=save]" : {
				click : function(_btn) {
					var _form = _btn.up("PsntypeForm").getForm();
					var _grid = _btn.up("PsntypeView").down("PsntypeGrid");
					_form.submit({
						clientValidation:true,
						url:'./json/adpsntype_savepsntype',
						method:'POST',
						params:{},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	Ext.Msg.alert('提示','人员保存成功');
					    	_grid.getStore().reload();
					    	_form.reset();
					    	var textf = _form.getFields().items;
							for(var i=0;i<textf.length;i++){
								textf[i].setDisabled(true);
							}					    	
					    	},
					    failure:function(form,action){
						    var resProductObj = Ext.decode(action.response.responseText);
						    Ext.Msg.alert('提示','保存失败！，原因'+resProductObj.errors.error);
							    }
					})					
				}},
				
				
		  "PsntypeForm button[ref=close]" : {
				click : function(_btn) {
					var _form = _btn.up("PsntypeForm").getForm();
					var _grid = _btn.up("PsntypeView").down("PsntypeGrid");
					_form.submit({
						clientValidation:true,
						url:'./json/adpsntype_savepsntype',
						method:'POST',
						params:{},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	Ext.Msg.alert('提示','人员保存成功');
					    	_grid.getStore().reload();
					    	_form.reset();
					    	var textf = _form.getFields().items;
							for(var i=0;i<textf.length;i++){
								textf[i].setDisabled(true);
							}					    	
					    	},
					    failure:function(form,action){
						    var resProductObj = Ext.decode(action.response.responseText);
						    Ext.Msg.alert('提示','保存失败！，原因'+resProductObj.errors.error);
							    }
					})					
				}},

		 "PsntypeForm button[ref=modify]" : {
				click : function(_btn) {
				var _formpanel = _btn.up("PsntypeForm");					
				var _form = _formpanel.getForm();
				var _grid = _formpanel.up("PsntypeView").down("PsntypeGrid");
				var _row = _grid.getSelectionModel().getSelection();
				if(_row.length == 0){
					Ext.Msg.alert("提示","请先选择一条数据!");
				}else{
				
				
				
				}			
				}}
		})
		

	},
	views : ["core.employee.quality.base.psntype.view.PsntypeView",
			 "core.employee.quality.base.psntype.view.PsntypeGrid",
			 "core.employee.quality.base.psntype.view.PsntypeForm",
			 "core.employee.quality.xunjian.view.ProjectCombo",
			 "core.employee.quality.base.psntype.view.EmpSearchGrid",
			 "core.employee.employeeAdd.view.DeptCombo"],
	stores : ["core.employee.quality.base.psntype.store.PsntypeStore",
			  "core.employee.quality.xunjian.store.ProjectStore",
			  "core.employee.employeeAdd.store.DeptStore",
			  "core.employee.quality.base.psntype.store.EmpSearchStore"],
	models : ["core.employee.quality.base.psntype.model.PsntypeModel",
			  "core.employee.quality.base.psntype.model.EmpSearchModel"]
});