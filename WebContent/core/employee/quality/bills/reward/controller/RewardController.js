Ext.define("core.employee.quality.bills.reward.controller.RewardController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this ;
		var type = 'QCJL' ;
		var rewardFormxtype ='RewardForm';
		var rewardForm = "core.employee.quality.bills.reward.view.RewardForm";
		
		var closeWindow = function(_btn){
			var _win = _btn.up("window");
			_win.close();
		};
		
		var rewardFormSubmit = function(_btn){
			var _form = _btn.up(rewardFormxtype).getForm();
			_form.findField('billcode').setValue(_form.findField('billcode').getValue().trim());
			var data = _form.getValues();
			_form.submit({
				clientValidation:true,
				params:{data:JSON.stringify(data)},
				url:'./json/adqualitybill_insert',
				method:'POST',
				waitTitle : '请等待' ,
			    waitMsg: '正在提交中',
			    success:function(form,action){
			    	_form.reset();
			    	_btn.up("window").close();
			    	var RewardStore = Ext.getCmp("MainViewLayout").down("RewardGrid").getStore();
			    	RewardStore.load();
			    	Ext.Msg.alert('提示',action.result.msg);					    	
			    	},
			    failure:function(form,action){
					Ext.Msg.alert('提示',action.result.msg);
					}
			})			
		};
		
		this.control({
			"RewardMainView button[ref=add]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
						title: '奖励报告录入',
					    width:800,
					    height:500,
						autoScroll:true,
					    layout: 'fit',
					    closable :false,
					    modal : true}
					);	
					var form = Ext.create(rewardForm);
					form.getForm().findField('type').setValue(type);
					win.add(form);
					win.show();
			}},		
			
			"RewardMainView button[ref=search]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
					    title: '查询窗口',
					    width:400,
					    height:250,
						autoScroll:true,
					    layout: 'fit',
					    modal : true}
					);	
					var form = Ext.create("core.employee.quality.bills.reward.view.RewardSearchForm");
					win.add(form);
					win.show();
				}				
			},
			
			"RewardSearchForm button[ref=search]" : {
				click : function(_btn) {
									
					//1、获取combo里面的值，并将controller的变量赋值
					
					
					//2、获取mainview上面的增加按钮并设置为可用
					var rewardManview = Ext.getCmp("MainViewLayout").down("RewardMainView");
					var addbtn = rewardManview.down("toolbar").down("button");					
					addbtn.setDisabled(false);
			//		addbtn.setText(record.get('menuname')+"新增");
					
					//3、获取bangrid的store传入参数并刷新
					var rewardGridStore = rewardManview.down("RewardGrid").getStore();
					rewardGridStore.getProxy().extraParams ={data : JSON.stringify(_btn.up("RewardSearchForm").getForm().getValues())};
					rewardGridStore.reload();
					
					
					_btn.up('window').close();
				}				
			},
				
		    "RewardMainView button[ref=modify]" : {
					click : function(_btn) {
						 var grid = _btn.up('RewardMainView').down('RewardGrid');
						 var record = grid.getSelectionModel().getSelection();
						 if(record.length == 0)
						 {
							 Ext.Msg.alert("提示","请先选择一条数据!");
						 }else if(record[0].get('appstatus') == '1'){
							 Ext.Msg.alert("提示","该单据已经审核，请勿修改!");
						 }else if(Ext.getCmp("displaylogin").getValue().split(':')[1]!=record[0].get('operator')){
							 Ext.Msg.alert("提示","只允许更改自己上传的记录！");
						 }else{							 
						   var win = Ext.create ('Ext.window.Window',{
								    title: '奖励报告录入',
								    width:800,
								    height:500,
									autoScroll:true,
								    layout: 'fit',
								    closable :false,
								    modal : true,
						   });	
						   var RewardForm	= Ext.create (rewardForm);						   
							 RewardForm.getForm().load({
								 url:'./json/adqualitybill_getbyuuid',
								 params:{data:record[0].get('uuid')},
								 failure: function(form, action) {
								        Ext.Msg.alert("Load failed", action.result.errorMessage);
								    }
							 });
							 win.add(RewardForm);
							 win.show(); 
						 }						 
					}},
					
			"RewardMainView button[ref=delete]" : {
						click : function(_btn) {
							 var grid = _btn.up('RewardMainView').down('RewardGrid');
							 var subgrid = _btn.up('RewardMainView').down('SubRewardGrid');
							 var record = grid.getSelectionModel().getSelection();
							 if(record.length == 0)
							 {
								 Ext.Msg.alert("提示","请先选择一条数据!");
							 }else if(record[0].get('appstatus') == '1'){
								 Ext.Msg.alert("提示","该单据已经审核，请勿删除!");
							 }else if(Ext.getCmp("displaylogin").getValue().split(':')[1]!=record[0].get('operator')){
								 Ext.Msg.alert("提示","只允许删除自己上传的记录！");
							 }else{	
									Ext.MessageBox.confirm("重要提示",
											"是否要删除吗？",
											function(e){
												if(e == 'yes'){
													 Ext.Ajax.request({
													      url:'./json/adqualitybill_delete',
													      method:'POST',
													      params:{data:record[0].get('uuid')},
													      success:function(response,option){
													    	  var obj =Ext.decode(response.responseText);	
														        if(obj.success == true){
														        	Ext.Msg.alert("提示","删除成功");
														        	grid.getStore().load();	
														        	subgrid.getStore().removeAll();
														        }else{
														        	Ext.Msg.alert("提示","删除失败");
														        }
													      },
														  failure:function(){
															  Ext.Msg.alert("提示","行删除失败")
															  }
													      }) 
								      }})					
					}}},
					
			
					
			"RewardMainView menuitem[ref=rowadd]" : {
					click : function(_item) {
						 var subgrid  = _item.up('RewardMainView').down('SubRewardGrid');
						 var maingrid = _item.up('RewardMainView').down('RewardGrid');
						 var _row = maingrid.getSelectionModel().getSelection();
						 
						 if(_row.length == 0)
						 {
							 Ext.Msg.alert("提示","请先选择一条数据!");
						 }else if(_row[0].get('appstatus') == '1'){
							 Ext.Msg.alert("提示","该单据已经审核，请勿修改!");
						 }
						 else {
						 var win = Ext.create ('Ext.window.Window',{
							    title: '奖励人员录入',
							    width:300,
							    height:250,
								autoScroll:true,
							    layout: 'fit',
							    closable :false,
							    modal : true,					 
						 });	
						 
						 var _form = Ext.create('core.employee.quality.bills.reward.view.SubRewardForm');
						 _form.getForm().findField('huuid').setValue(_row[0].get('uuid'));						 
						 win.add(_form);
						 win.show();
/*						var rowedit =   subgrid.getPlugin('subGridRowPlugin');
						rowedit.cancelEdit();
						 var substore = subgrid.getStore();
						 substore.insert(0, Ext.create('core.employee.quality.bills.reward.model.SubRewardModel'));
						 rowedit.startEdit(0, 0);*/
			}}
					
			
			},
			
			"RewardMainView menuitem[ref=rowmodify]" : {
				click : function(_item) {
					var maingrid = _item.up('RewardMainView').down('RewardGrid');
					var mainrecord = maingrid.getSelectionModel().getSelection();
					 var grid = _item.up('RewardMainView').down('SubRewardGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }else if(mainrecord[0].get('appstatus') == '1'){
						 Ext.Msg.alert("提示","该单据已经审核");
					 }else if(Ext.getCmp("displaylogin").getValue().split(':')[1]!=record[0].get('operator')){
						 Ext.Msg.alert("提示","只允许删除自己上传的记录！");
					 }else{							 
					   var win = Ext.create ('Ext.window.Window',{
							    title: '奖励人员录入',
							    width:400,
							    height:300,
								autoScroll:true,
							    layout: 'fit',
							    closable :false,
							    modal : true,
					   });	
					   var RewardForm	= Ext.create (rewardForm);						   
						 RewardForm.getForm().load({
							 url:'./json/adqualitybill_getbysubuuid',
							 params:{data:record[0].get('uuid')},
							 failure: function(form, action) {
							        Ext.Msg.alert("Load failed", action.result.errorMessage);
							    }
						 });
				//		var employeecombo = RewardForm.down("EmployeeCombo");
				//		var deptcombo = RewardForm.down("DeptCombo");
				//		employeecombo.setDeptparam(deptcombo.getValue().pkDeptdoc);
						 Ext.Msg.alert("Load failed", RewardForm.getForm().getValue());
						 win.add(RewardForm);
						 win.show(); 
					 }			
				}},

			"RewardMainView menuitem[ref=rowdelete]" : {
				click : function(_item) {
					var maingrid = _item.up('RewardMainView').down('RewardGrid');
					var mainrecord = maingrid.getSelectionModel().getSelection();
					 var grid = _item.up('RewardMainView').down('SubRewardGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }else if(mainrecord[0].get('appstatus') == '1'){
						 Ext.Msg.alert("提示","该单据已经审核");
					 }else if(Ext.getCmp("displaylogin").getValue().split(':')[1]!=mainrecord[0].get('operator')){
						 Ext.Msg.alert("提示","只允许删除自己上传的记录！");
					 }else{	
						 Ext.Ajax.request({
						      url:'./json/adqualitybill_subdelete',
						      method:'POST',
						      params:{data:record[0].get('uuid')},
						      success:function(response,option){
						    	  var obj =Ext.decode(response.responseText);	
							        if(obj.success == true){
							        	Ext.Msg.alert("提示","删除成功");
						//	        	var store = 
						//	        	subRewardStore.getProxy().extraParams ={data : huuid};
							        		grid.getStore().load();							        	
							        }else{
							        	Ext.Msg.alert("提示","删除失败");
							        }
						      },
							  failure:function(){
								  Ext.Msg.alert("提示","删除失败")
								  }
						      })
					 }
					
				}},
				
			"RewardMainView button[ref=review]" : {
				click : function(_item) {
						var grid = _item.up('RewardMainView').down('RewardGrid');
						var record = grid.getSelectionModel().getSelection();
						if(record.length >0)
						{
							 Ext.Ajax.request({
							      url:'./json/adqualitybill_reviewHtml',
							      method:'POST',
							      params:{data:record[0].get('uuid')},
							      success:function(response,option){ 
							        var obj =Ext.decode(response.responseText);	
							        if(obj.success == true){
							        	var win = Ext.create ('Ext.window.Window',{
										    title: '报告生成预览',
										    width:1024,
										    height:600,
											autoScroll:true,
										    layout: 'fit',
										    modal : true,					 
									    });
							           var panel = Ext.create ('Ext.panel.Panel',{
							        	   tbar:[{
							        		   xtype: 'button', text: '输出PDF',listeners: { "click": function () {
							        			   window.open('./fkpdf/'+obj.msg+'.pdf');       
							        		    }}}							        		   
							        	   ],
							        	   html:' <iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="./fkhtml/'+obj.msg+'.html"> </iframe>'
									    });
							           win.add(panel);
							           win.show();
							      	}else{
							      		Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击！")
							      	}
							      	},
							      failure:function(){
							      	 Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击")
							      }
							  });
						}else{
							Ext.Msg.alert('警告','请先选择一行');
						}
			}},
			
			"RewardMainView button[ref=upload]" : {
				click : function(_btn) {
					 var grid = _btn.up('RewardMainView').down('RewardGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }
					 else if(record[0].get('appstatus') != '1'){
						 Ext.Msg.alert("提示","请先审核当前单据!");
					 }else if( Ext.getCmp("displaylogin").getValue().split(':')[1] != record[0].get('operator')){
						 Ext.Msg.alert("提示","只允许更改自己上传的记录！");
					 }else{
						 var win = Ext.create ('Ext.window.Window',{
							    title: 'PDF报告上传',
							    width:300,
							    height:100,
								autoScroll:true,
							    modal : true,	
							    
							    bbar:[{xtype: 'button', text: '保存',ref:'save',handler: function() {
							    	    var me = this;
							    		var txt = me.up('window').down('UploadFieldDef').getValue();
							    		Ext.Ajax.request({
							    			  url:'./json/adqualitybill_upscanfile',
										      method:'POST',
										      params:{data:record[0].get('uuid'),scanfilename:txt},
										      success:function(response,option){ 
										    	  Ext.Msg.alert("提示","文件保存成功");
										    	  me.up('window').close();
										      },
										      failure:function(){
											      Ext.Msg.alert("提示","文件名保存失败，请重新上传");
											  }							    			
							    		})						    	
						        	}},
							    	{xtype: 'button', text: '关闭',ref:'close',handler: function() {
							            this.up('window').close();
							        }}]
					 });
					var uploadcom = Ext.create('core.util.UploadFieldDef',{fieldLabel: 'PDF文档:',name: 'pdf1',margin:'5 40 0 20',contexType:'application/pdf',filepath :'/upload/pdf'});
					win.add(uploadcom);	
					win.show();
					 }
				}},
				
				
			"RewardGrid " : {
				itemclick:function(view,record,item,index,e,eOpts ){
					   var subgrid = view.up('RewardMainView').down('SubRewardGrid');
						var _store = subgrid.getStore();
			        	_store.getProxy().extraParams ={data : record.get('uuid')};
						_store.load();
					}},
					
					
			"RewardForm combo[name=yiju]" : {
						select : function( combo, records, eOpts ){
							combo.up('RewardForm').getForm().findField('fenxi').setValue(records[0].get('code'));						
						}},	
				
		    "RewardForm button[ref=close]" : {
				click : function(_btn) {
					closeWindow(_btn);	
				}},
			
			
			"RewardForm button[ref=save]" : {
				click : function(_btn) {
					rewardFormSubmit(_btn);				
				}},
				
				
		"SubRewardForm button[ref=save]" : {
					click : function(_btn) {
						var _form = _btn.up("SubRewardForm").getForm();
						var data = _form.getValues();
						var huuid = data.huuid;
						_form.submit({
							clientValidation:true,
							url:'./json/adqualitybill_subinsert',
							params:{data:JSON.stringify(data)},
							method:'POST',
							waitTitle : '请等待' ,
						    waitMsg: '正在提交中',
						    success:function(form,action){
						    	var subRewardStore = Ext.getCmp("MainViewLayout").down("SubRewardGrid").getStore();
						    	subRewardStore.getProxy().extraParams ={data : huuid};
						    	subRewardStore.load();
						    	_form.reset();
                                _form.findField('huuid').setValue(huuid);
						   // 	_btn.up("window").close();
						    	Ext.Msg.alert('提示', '保存成功');					    	
						    	},
						    failure:function(form,action){
								Ext.Msg.alert('提示','保存失败');
								}
						})
					}},
					
		"SubRewardForm combo[ref=deptcombo]" : {
				select : function( combo, records, eOpts ) {
					var employeecombo = combo.up("SubRewardForm").down("EmployeeCombo");
					employeecombo.setDeptparam(records[0].get("pkDeptdoc"));
		}},
		
		
		"SubRewardForm button[ref=close]" : {
				click : function(_btn) {
					closeWindow(_btn);
				}}
		})
	},
	views : ["core.employee.quality.bills.reward.view.RewardMainView",
			 "core.employee.quality.bills.reward.view.RewardForm",
			 "core.employee.quality.bills.reward.view.RewardGrid",
			 "core.employee.employeeAdd.view.DeptCombo",
			 "core.employee.quality.bills.reward.view.RewardSearchForm",
			 "core.util.EmployeeCombo",
			 "core.util.GridSearchText",
			 "core.employee.quality.xunjian.view.ProjectCombo",
			 "core.employee.quality.bills.reward.view.SubRewardGrid",
			 "core.employee.quality.bills.reward.view.SubRewardForm",
			 "core.util.UploadFieldDef"],
	stores : ["core.employee.employeeAdd.store.DeptStore","core.employee.quality.xunjian.store.ProjectStore","core.employee.quality.bills.reward.store.SubRewardStore","core.employee.quality.bills.reward.store.RewardStore","core.employee.quality.bills.reward.store.QualityRewardTypeStore"],
	models : ["core.employee.quality.bills.reward.model.RewardModel","core.employee.quality.bills.reward.model.SubRewardModel"]
});