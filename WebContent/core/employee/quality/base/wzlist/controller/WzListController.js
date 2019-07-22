Ext.define("core.employee.quality.base.wzlist.controller.WzListController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({			
		"WzTypeListTree" : {
				itemclick : function(_this,record,item,index,e,eOpts ){
		        	var _grid = _this.up("WzListView").down("WzListGrid");
		        	var _form = _this.up("WzListView").down("WzListForm").getForm();
		        	var textf = _form.getFields().items;
					for(var i=0;i<textf.length;i++){
							textf[i].setDisabled(true);
						}
		        	var _store = _grid.getStore();
		        	_store.getProxy().extraParams ={wztype : record.get('id')};
					_store.load();
				}},
			

		"WzListForm button[ref=add]" : {
				click : function(_btn) {
					var _formpanel = _btn.up("WzListForm");					
					var _form = _formpanel.getForm();
					_form.reset();
					var _tree =_btn.up("WzListView").down("WzTypeListTree");
					var _node = _tree.getSelectionModel().getSelection();
					if(_node.length != 0){
						var aaadd3 = _node[0].get('children');
						
						if(aaadd3.length ==0)
							{
								var textf = _form.getFields().items;
								for(var i=0;i<textf.length;i++){
										textf[i].setDisabled(false);
									}	
								_form.findField('wztype').setValue(_node[0].get('id'));				
								_form.findField('wztype').setReadOnly(true);
							}else{
								Ext.Msg.alert('提示','请选择一个子节点');								
							}
					}else{
						Ext.Msg.alert('提示','请先选择一个节点');
					}
			}},
			
		"WzListForm button[ref=modify]" : {
			click : function(_btn) {
				var _formpanel = _btn.up("WzListForm");					
				var _form = _formpanel.getForm();
				var _grid = _formpanel.up("WzListView").down("WzListGrid");
				var _row = _grid.getSelectionModel().getSelection();
				if(_row.length == 0){
					Ext.Msg.alert("提示","请先选择一条数据!");
				}else{
					_form.findField('uuid').setValue(_row[0].get('uuid'));
					_form.findField('wzlistcode').setValue(_row[0].get('wzlistcode'));
					_form.findField('wzlistname').setValue(_row[0].get('wzlistname'));
					_form.findField('wztype').setValue(_row[0].get('wztype'));
					_form.findField('wzlisttype').setValue(_row[0].get('wzlisttype'));
					_form.findField('wzlistscore').setValue(_row[0].get('wzlistscore'));
					_form.findField('note').setValue(_row[0].get('note'));
					var textf = _form.getFields().items;
					for(var i=0;i<textf.length;i++){
						textf[i].setDisabled(false);
						}				
				}			
			}},	
			
			
		"WzListForm button[ref=save]" : {
				click : function(_btn) {
				var _form = _btn.up("WzListForm").getForm();
				var _grid = _btn.up("WzListView").down("WzListGrid");
				_form.submit({
						clientValidation:true,
						url:'./json/adwztype_saveadWzList',
						method:'POST',
						params:{},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	Ext.Msg.alert('提示','违章明细保存成功');
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
			
		"WzTypeForm button[ref=reset]" : {				
				click : function(_btn) {	
				var _form = _btn.up("WzTypeForm").getForm();				
				_form.reset();
				}}
		})
	},
	views : ["core.employee.quality.base.wzlist.view.WzListForm",
			 "core.employee.quality.base.wzlist.view.WzListGrid",
			 "core.employee.quality.base.wzlist.view.WzListView",
			 "core.employee.quality.base.wzlist.view.WzTypeCombo",
	         "core.employee.quality.base.wzlist.view.WzTypeListTree"],
	stores : ["core.employee.quality.base.wzlist.store.WzListStore",
			  "core.employee.quality.base.wztype.store.WzTypeStore"],
	models : ["core.employee.quality.base.wzlist.model.WzListModel"]
});