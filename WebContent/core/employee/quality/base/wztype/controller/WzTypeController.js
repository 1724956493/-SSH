Ext.define("core.employee.quality.base.wztype.controller.WzTypeController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			"WzTypeTree" : {
				itemclick : function(_this,record,item,index,e,eOpts ){
					var _formpanel = _this.up("WzTypeView").down("WzTypeForm");					
					var _form = _formpanel.getForm();
					_form.findField('uuid').setValue(record.get('uuid'));
					_form.findField('wzcode').setValue(record.get('code'));
					_form.findField('wzparent').setValue(record.get('parent'));
					_form.findField('wzname').setValue(record.get('text'));
					_form.findField('note').setValue(record.get('description'));
					var textf = _form.getFields().items;
							for(var i=0;i<textf.length;i++){
								textf[i].setDisabled(true);
							}
				}},
			
			"WzTypeTree button[ref=add]" : {
				click : function(_btn) {
					var _formpanel = _btn.up("WzTypeView").down("WzTypeForm");					
					var _form = _formpanel.getForm();
					_form.reset();
					var _tree =_btn.up("WzTypeTree");
					var _node = _tree.getSelectionModel().getSelection();
					var id = _node[0].get('id');
					if(_node[0].get('leaf')==true){
						id = _node[0].get('parent');
					}
					_form.findField('wzparent').setValue(id);
					_form.findField('wzcode').setValue(_node[0].get('code'))
					var textf = _form.getFields().items;
					for(var i=0;i<textf.length;i++){
						textf[i].setDisabled(false);
					};
					_form.findField('wzparent').setReadOnly(true);
			}},
			

			
			"WzTypeTree button[ref=modify]" : {
				click : function(_btn) {
					var _formpanel = _btn.up("WzTypeView").down("WzTypeForm");					
					var _form = _formpanel.getForm();
					var _tree =_btn.up("WzTypeTree");					
					var textf = _form.getFields().items;
					for(var i=1;i<textf.length;i++){
						textf[i].setDisabled(false);
					};
					_form.findField('wzparent').setReadOnly(true);
			}},
			
			"WzTypeTree button[ref=delete]" : {
				click : function(_btn) {
					
					var _formpanel = _btn.up("WzTypeView").down("WzTypeForm");
					var _tree =_btn.up("WzTypeTree");
					var _form = _formpanel.getForm();
					Ext.MessageBox.confirm("重要提示","是否要删除吗？",
							function(e){
								if(e == 'yes'){
					_form.submit({
						clientValidation:true,
						url:'./json/user_deletemenu',
						method:'POST',
						params:{},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					     	
                        //url后台返回的数据{success:true,msg:'成功'}	+action.result.msg
					    	Ext.Msg.alert('提示','菜单删除成功');	
					    	_tree.getStore().reload();
					    	},
					    failure:function(form,action){
					    	var resProductObj = Ext.decode(action.response.responseText);
						    Ext.Msg.alert('提示','删除失败！，原因'+resProductObj.errors.error);
					    }
					})}})
			}},
			
			
			"WzTypeForm button[ref=save]" : {				
				click : function(_btn) {	
					var _form = _btn.up("WzTypeForm").getForm();
					var _tree = _btn.up("WzTypeView").down("WzTypeTree");
					_form.submit({
						clientValidation:true,
						url:'./json/adwztype_saveadWztype',
						method:'POST',
						params:{type:0},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	Ext.Msg.alert('提示','菜单保存成功');
					    	_tree.getStore().reload();
					    	_form.reset();
					    	var textf = _form.getFields().items;
							for(var i=0;i<textf.length;i++){
								textf[i].setDisabled(true);
							};
							
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
	views : ["core.employee.quality.base.wztype.view.WzTypeForm",
	"core.employee.quality.base.wztype.view.WzTypeGrid",
	"core.employee.quality.base.wztype.view.WzTypeView",
	"core.employee.quality.base.wztype.view.WzTypeTree"],
	stores : ["core.employee.quality.base.wztype.store.WzTypeStore"],
	models : ["core.employee.quality.base.wztype.model.WzTypeModel"]
});