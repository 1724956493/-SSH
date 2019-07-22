Ext.define("core.guard.carinfo.controller.CarInfoController",{
	extend : "Ext.app.Controller",
/*	mixins : {
		formUtils : "core.util.FormUtils"
	},*/
	init : function(){
		var self = this;
		this.control({
		"CarInfoGrid " : {
				itemdblclick:function(view,record,item,index,e,eOpts ){
					var _win = view.up('CarInfoView').down('window');
					var _form = _win.down('CarInfoForm');
					_form.down('toolbar').hide();
					_form.loadRecord(record);
					_win.show();				
			}},
			
		"CarInfoGrid button[ref=create]" : {
				click : function(_btn) {
					var _win = _btn.up('CarInfoView').down('window');
					var _form = _win.down('CarInfoForm');
					_form.down('toolbar').show();
					var _formBase = _form.getForm();
					_formBase.reset();
					var textf = _formBase.getFields().items;
					for(var i=0;i<textf.length;i++){
						textf[i].setReadOnly(false);
					};
					_win.show();
				}},
			
		"CarInfoGrid button[ref=modify]" : {
				click : function(_btn) {
					var _grid = _btn.up('CarInfoGrid');
					var record = _grid.getSelectionModel().getLastSelected();
					if(record){
						var _win = _btn.up('CarInfoView').down('window');
						var _form = _win.down('CarInfoForm');
						_form.down('toolbar').show();
						var _formBase = _form.getForm();					
						_formBase.loadRecord(record);
						var textf = _formBase.getFields().items;
						for(var i=0;i<textf.length;i++){
							textf[i].setReadOnly(false);
						};
						_win.show();
					}else{
						Ext.Msg.alert('提示','请先选择一条数据');
					}
				}},
		
				
		"CarInfoGrid button[ref=delete]" : {
				click : function(_btn) {
					var _grid = _btn.up('CarInfoGrid');
					var record = _grid.getSelectionModel().getLastSelected();
					if(record){
						Ext.MessageBox.confirm("重要提示",
							"是否要删除吗？",
							function(e){
								if(e == 'yes'){
									Ext.Ajax.request({
										waitMsg : '正在进行处理,请稍后...', 
										url : "./json/adcarinfo_delete",
										params : {
											data : record.get('uuid')
										},// 根据id删除
										method : "POST",
										timeout : 4000,
										success : function(response, opts) {
											var resObj = Ext.decode(response.responseText);
											if (resObj.success) {
												// 不用查询，从grid中去掉对应的记录就OK了
												_grid.getStore().load();
											} else {
												Ext.Msg.alert("提示", "失败");
											}
										}
									});
								}
							})
					}else{
						Ext.Msg.alert('提示','请先选择一条数据');
					}
				}},	
				
		"CarInfoForm button[ref=save]" : {
				click : function(_btn) {
					var _form = _btn.up("CarInfoForm").getForm();		
					var data = _form.getValues();
					Ext.Ajax.request({
						clientValidation:true,
						url:'./json/adcarinfo_insert',
						method:'POST',
						params:{data: JSON.stringify(data)},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(response, opts){
					    	var resObj = Ext.decode(response.responseText);
							if (resObj.success) {
								_form.reset();
								var textf = _form.getFields().items;
								for(var i=0;i<textf.length;i++){
									textf[i].setReadOnly(true);
								};
								_btn.up('window').hide();
						    	var _grid = _btn.up('CarInfoView').down('CarInfoGrid');
						    	_grid.getStore().reload();			    	
					    	}else {
								Ext.Msg.alert("提示", resObj.errors.error);
					    	}
						}		
					})
				}},	
				
		"CarInfoForm button[ref=reset]" : {
				click : function(_btn) {
					var _form = _btn.up('CarInfoForm');
					_form.getForm().reset();
					}}
		})
	},
		
	views : [
	    "core.guard.carinfo.view.CarInfoView",
	    "core.guard.carinfo.view.CarInfoForm",
	    "core.guard.carinfo.view.CarInfoGrid",
	    "core.util.GridSearchText"
	],
	stores : ["core.guard.carinfo.store.CarInfoStore"],
	models : ["core.guard.carinfo.model.CarInfoModel"]
});