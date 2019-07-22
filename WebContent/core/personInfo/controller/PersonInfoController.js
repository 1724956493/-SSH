/**主控制器*/
Ext.define("core.app.controller.PersonInfoController",{
	extend : "Ext.app.Controller",
/*	mixins : {
		formUtils : "core.util.FormUtils"
	},*/
	init : function(){
		var self = this;
		this.control({
			"RoleView button[ref=add]" : {
				click : function(_btn) {
					var form = _btn.up("RoleViewLayout").down("RoleFormView");
					form.getForm().reset();
			}},
			
			
			"RoleView" : {
				itemclick : function(_grid, record, item, index, e, eOpts) {
					var form = _grid.up("RoleViewLayout").down("RoleFormView");
					var grid= form.up("RoleViewLayout").down("RoleView");
					var record = grid.getSelectionModel().getSelection();
					form.loadRecord(record[0]);				
				}},
			
			"RoleFormView button[ref=reset]" : {
				click : function(_btn) {
					var form =_btn.up("RoleFormView").getForm();
					form.reset();
				}},
				
			"RoleFormView button[ref=save2]" : {
					click : function(_btn) {						
						var _form =_btn.up("RoleFormView").getForm();
						var abcformrecord = _form.getValues();
						
						/*		var arequestConfig ={
								url:'./insert/user_saverole',
								jsonData : JSON.stringify(abcformrecord),
							//	form:_form,
							//	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
								callback: function(options,success,response){
								var msg=["请求是否成功：",success,"\n","服务器返回值：",response.responseText];
						           Ext.Msg.alert('1',msg);
								}
						};						
						Ext.Ajax.request(arequestConfig); */
				_form.submit({
							clientValidation:true,
							url:'./insert/user_saverole2',
							method:'POST',
							params:{
								newStatus: 'delivered'
							},
							waitTitle : '请等待' ,
						    waitMsg: '正在提交中',
						    success:function(form,action){
						        //	var resProductObj = Ext.decode(action.response.responseText);
                                //url后台返回的数据{success:true,msg:'成功'}	+action.result.msg
						    	Ext.Msg.alert('提示','保存');
						    	},
						    failure:function(form,action){
									Ext.Msg.alert('提示','保存失败！');
								    }
						});
					}
			},
		})
	},
		
	views : [
	    "core.app.view.TestViewLayout",
	    "core.app.view.UserView",
	    "core.app.view.RoleView",
	    "core.app.view.RoleFormView",
	    "core.app.view.RoleViewLayout"
	],
	stores : ["core.app.store.UserStore","core.app.store.RoleStore"],
	models : ["core.app.model.UserModel","core.app.model.RoleModel"]
});