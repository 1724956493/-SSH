/**主控制器*/
Ext.define("core.employee.employeeAdd.controller.EmployeeController",{
	extend : "Ext.app.Controller",
/*	mixins : {
		formUtils : "core.util.FormUtils"
	},*/
	init : function(){
		var self = this;
		this.control({	
			"EmApplyGrid" : {			
				afterrender : function(_this,eOpts ){
					_this.view.refresh();
				}},
		
			"EmApplyGrid button[ref=add]" : {
				click : function(_btn) {
					var win = new core.employee.employeeAdd.view.IdAddWin();
					var grid = _btn.up("EmViewLayout").down("EmApplyGrid");
					var form = grid.up("EmViewLayout").down("EmInfoAddView");
					form.show();
					grid.hide();
					win.show();
				}},
			
			"EmApplyGrid button[ref=delete]" : {
				click : function(_btn) {
					var grid = _btn.up("EmViewLayout").down("EmApplyGrid");
					var store = grid.getStore();
					var records = grid.getSelectionModel().getLastSelected();
					if (!records) {
						Ext.Msg.alert("提示", "请选择需要删除的数据!");
						return;
					}
					
					Ext.MessageBox.confirm("重要提示",
							"是否要删除吗？",
							function(e){
								if(e == 'yes'){
									Ext.Ajax.request({
										waitMsg : '正在进行处理,请稍后...', 
										url : "./json/employee_employeeInfoDelete",
										params : {
											data : records.get('employeeuuid')
										},// 根据id删除
										method : "POST",
										timeout : 4000,
										success : function(response, opts) {
											var resObj = Ext.decode(response.responseText);
											if (resObj.success) {
												// 不用查询，从grid中去掉对应的记录就OK了
												store.load();
												Ext.Msg.alert("提示", resObj.msg);
											} else {
												Ext.Msg.alert("提示", resObj.msg);
											}
										}
									});
								}
							});
					
				}},
			
			"EmApplyGrid button[ref=close]" : {
				click : function(_btn) {
					var tabpanel = _btn.up("EmViewLayout");
					tabpanel.close();
				}},
			
			"EmApplyGrid button[ref=modify]" : {
					click : function(_btn) {
						var grid = _btn.up("EmViewLayout").down("EmApplyGrid");
						var form = grid.up("EmViewLayout").down("EmInfoAddView");
						var records = grid.getSelectionModel().getLastSelected();
						if(records.get('status')!=0){
							Ext.Msg.alert('提示','该用户已经提交审核，不允许修改');
						}else{
							form.getForm().loadRecord(records);	 
							form.show();
							grid.hide();
						}						
					}},
				
			"EmApplyGrid " : {
				itemdblclick:function(view,record,item,index,e,eOpts ){
					var grid = view.up("EmApplyGrid");
					grid.hide();
					var form = grid.up("EmViewLayout").down("EmInfoAddView");
					form.getForm().loadRecord(record);
					Ext.getCmp("EmInfoAddView_save").setVisible(false); 
					form.show();
					}},
				
			"EmInfoAddView button[ref=save]" : {
				click : function(_btn) {
					var _form = _btn.up("EmInfoAddView").getForm();
					var data = _form.getValues();
					_form.submit({
						clientValidation:true,
						url:'./json/employee_employeeInfoInsert',
						method:'POST',
						params:{data: JSON.stringify(data)},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	_form.reset();	
					    	if(data.employeeuuid == "")
					    	{
						    	var win = new core.employee.employeeAdd.view.IdAddWin();
						    	win.show();	
					    	}else {
					    		_btn.up("EmInfoAddView").hide();
					    		var _grid = _btn.up("EmViewLayout").down("EmApplyGrid");
					    		_grid.getStore().reload();
					    		_grid.show();
					    	}				    	
					    	},
					    failure:function(form,action){
					    	Ext.Msg.alert('提示','失败');
					    }
					})
					}},
					
			"EmInfoAddView button[ref=reset]" : {
				click : function(_btn) {
					var form = _btn.up("EmInfoAddView").getForm();
					form.reset();
					}},
					
			"EmInfoAddView button[ref=close]" : {
				click : function(_btn) {
					var grid = _btn.up("EmViewLayout").down("EmApplyGrid");
					var form = grid.up("EmViewLayout").down("EmInfoAddView");
					Ext.getCmp("EmInfoAddView_save").setVisible(true);
					form.getForm().reset();
					form.hide();
					grid.getStore().reload();
					grid.show();					
					}},
			
			"IdAddWin button[ref=check]" : {
					click : function(_btn) {						
						var _formpanelwin =Ext.getCmp("IdAddForm");
						var _form =_formpanelwin.getForm()
						var _win = _btn.up("IdAddWin");
						_form.submit({
							clientValidation:true,
							url:'./json/user_saverole3',
							method:'POST',
						//	params:{newStatus: 'delivered'},
							waitTitle : '请等待' ,
						    waitMsg: '正在提交中',
						    success:function(form,action){
						    	var result = Ext.decode(action.response.responseText);
						    	var _formpanel = Ext.getCmp("emviewlayout").down("EmInfoAddView");
						    	var _form2 =_formpanel.getForm();						    	
						    	
						    	_form2.findField('psnname').setValue(result.employee.psnname);
						    	_form2.findField('id').setValue(result.employee.id);
						    	_form2.findField('birthdate').setValue(result.employee.birthdate);
						    	_form2.findField('province').setValue(result.employee.province);
						    	_form2.findField('dept').setValue(result.employee.pk_deptdoc);
						    	_win.close();
						 //   	Ext.Msg.alert('提示',result.employee.birthdate);
						    	},
						    failure:function(form,action){
						         	var result = Ext.decode(action.response.responseText);
									Ext.Msg.alert("提示",result.errors.id)
						     //    	_form.findField(result).setValue(result.errors.id);
								    }
						});
					}},	
			
			"IdAddWin button[ref=return]" : {
				click : function(_btn) {
					var _win = _btn.up("IdAddWin");					
					var grid = Ext.getCmp("emviewlayout").down("EmApplyGrid");
					var form = grid.up("EmViewLayout").down("EmInfoAddView");
					_win.close();
					grid.show();
					form.hide();					
				}				
			},
			
			"XfReportView button[ref=import]" : {
				click : function(_btn) {
					var tbar = _btn.up("toolbar");
					var record = Ext.Date.format(tbar.down("datefield").getValue(), 'Y-m-d');
					
					Ext.Ajax.request({
					      url:'./json/yktserver_xfReportdown',
					      method:'POST',
					      params:{data:record},
					      success:function(response,option){ 
					        var obj =Ext.decode(response.responseText);	
					        if(obj.success == true){
					      		window.location.href ='./json/yktserver_xfReportdown2?data='+record;
					      	}else{
					      		Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击！")
					      	}
					      	},
					      failure:function(){
					      	 Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击")
					      }
					      });			
			}}				
		})
	},
		
	views : [
	    "core.employee.employeeAdd.view.EmViewLayout",
	    "core.employee.employeeAdd.view.EmApplyGrid",
	    "core.employee.employeeAdd.view.EmInfoAddView",
	    "core.employee.employeeAdd.view.IdAddWin",
	    "core.employee.employeeAdd.view.DeptCombo",
	    "core.employee.employeeAdd.view.JobCombo",
	    "core.employee.employeeAdd.view.EmSbGrid",
	    "core.employee.employeeAdd.view.XfReportView"
	],
	stores : ["core.employee.employeeAdd.store.EmployeeStore","core.employee.employeeAdd.store.DeptStore","core.employee.employeeAdd.store.JobStore"],
	models : ["core.employee.employeeAdd.model.EmployeeModel","core.employee.employeeAdd.model.DeptModel","core.employee.employeeAdd.model.JobModel"]
});