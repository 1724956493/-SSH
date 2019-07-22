Ext.define("core.employee.quality.bills.ban.controller.BanController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		var type = '' ;
		var banFormxtype ='';
		var banForm = '';
		
		var closeWindow = function(_btn){
			var _win = _btn.up("window");
			_win.close();
		};
		
		var banFormSubmit = function(_btn){
			var _form = _btn.up(banFormxtype).getForm();
			_form.findField('billcode').setValue(_form.findField('billcode').getValue().trim());
			var data = _form.getValues();
			if(type=='AH4S'){
				data.yiju = data.yiju.toString();
			};
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
			    	var banstore = Ext.getCmp("MainViewLayout").down("BanGrid").getStore();
			    	banstore.reload();
			    	Ext.Msg.alert('提示',action.result.msg);					    	
			    	},
			    failure:function(form,action){
					Ext.Msg.alert('提示',action.result.msg);
					}
			})			
		};
		
	
		this.control({
			"BanMainView button[ref=add]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
					    title: '报告录入',
					    width:800,
					    height:600,
						autoScroll:true,
					    layout: 'fit',
					    closable :false,
					    modal : true,}					    
					);	
					var form = Ext.create(banForm);
					form.getForm().findField('type').setValue(type);
					win.add(form);
					win.show();
			}},		
			
			"BanMainView button[ref=search]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
					    title: '查询窗口',
					    width:400,
					    height:250,
						autoScroll:true,
					    layout: 'fit',
					    modal : true}
					);	
					var form = Ext.create("core.employee.quality.bills.ban.view.BanSearchForm");
					var comb = form.down("ResourceCombo");
					var comstore = comb.getStore();
					comstore.getProxy().extraParams ={mainview : 'BanMainView'};					
					comstore.load({
							callback: function(records, operation, success) {
								if(comstore.getCount()!=0){
									var value = comstore.getAt(0).get('menucode');
									comb.setValue(value);
								}
						    }					
					});
					
					win.add(form);
					win.show();
				}				
			},
			
			"BanSearchForm button[ref=search]" : {
				click : function(_btn) {
									
					//1、获取combo里面的值，并将controller的变量赋值
					var combo = _btn.up('BanSearchForm').down('combo');
					var record = combo.findRecordByValue(combo.getValue());
					type = record.get('menucode');
					var actionStr = record.get('action').split(",");
					banFormxtype = actionStr[0];
					banForm = actionStr[1];
					
					//2、获取mainview上面的增加按钮并设置为可用
					var banmanview = Ext.getCmp("MainViewLayout").down("BanMainView");
					var addbtn = banmanview.down("toolbar").down("button");					
					addbtn.setDisabled(false);
					addbtn.setText(record.get('menuname')+"新增");
					
					//3、获取bangrid的store传入参数并刷新
					var banGridStore = banmanview.down("BanGrid").getStore();
					banGridStore.getProxy().extraParams ={data : JSON.stringify(_btn.up("BanSearchForm").getForm().getValues())};
					banGridStore.reload();
					
					
					_btn.up('window').close();
				}				
			},
			
			
			
			"ResourceCombo " : {
				expand : function( field, eOpts ){
					
	//				alert(field);
					
				}},
			
				
			"BanForm combo[name=yiju]" : {
					select : function( combo, records, eOpts ){
						combo.up('BanForm').getForm().findField('fenxi').setValue(records[0].get('code'));						
					}},	
				
		    "BanMainView button[ref=modify]" : {
					click : function(_btn) {
						 var grid = _btn.up('BanMainView').down('BanGrid');
						 var record = grid.getSelectionModel().getSelection();
						 if(record.length == 0)
						 {
							 Ext.Msg.alert("提示","请先选择一条数据!");
						 }else if(record[0].get('appstatus') == '1'|| record[0].get('appstatus') == '2' ){
							 Ext.Msg.alert("提示","该单据已经审核，请勿修改!");
						 }else if(Ext.getCmp("displaylogin").getValue().split(':')[1]!=record[0].get('operator')){
							 Ext.Msg.alert("提示","只允许更改自己上传的记录！");
						 }else{							 
						   var win = Ext.create ('Ext.window.Window',{
								    title: '报告录入',
								    width:800,
								    height:600,
									autoScroll:true,
								    layout: 'fit',
								    closable :false,
								    modal : true,
						   });	
						   var banform	= Ext.create (banForm);						   
							 banform.getForm().load({
								 url:'./json/adqualitybill_getbyuuid',
								 params:{data:record[0].get('uuid')},
								 failure: function(form, action) {
								        Ext.Msg.alert("Load failed", action.result.errorMessage);
								    }
							 });
							 win.add(banform);
							 win.show(); 
						 }						 
					}},
					
			"BanMainView button[ref=delete]" : {
						click : function(_btn) {
							 var grid = _btn.up('BanMainView').down('BanGrid');
							 var subgrid = _btn.up('BanMainView').down('SubBanGrid');
							 var record = grid.getSelectionModel().getSelection();
							 if(record.length == 0)
							 {
								 Ext.Msg.alert("提示","请先选择一条数据!");
							 }else if(record[0].get('appstatus') == '1'|| record[0].get('appstatus') == '2'){
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
					
			"BanMainView menuitem[ref=rowadd]" : {
					click : function(_item) {
						 var subgrid  = _item.up('BanMainView').down('SubBanGrid');
						 var maingrid = _item.up('BanMainView').down('BanGrid');
						 var _row = maingrid.getSelectionModel().getSelection();
						 
						 if(_row.length == 0)
						 {
							 Ext.Msg.alert("提示","请先选择一条数据!");
						 }else if(_row[0].get('appstatus') == '1'){
							 Ext.Msg.alert("提示","该单据已经审核，请勿修改!");
						 }
						 else {
						 var win = Ext.create ('Ext.window.Window',{
							    title: '处罚人员录入',
							    width:300,
							    height:250,
								autoScroll:true,
							    layout: 'fit',
							    closable :false,
							    modal : true,					 
						 });	
						 
						 var _form = Ext.create('core.employee.quality.bills.ban.view.SubBanForm');
						 _form.getForm().findField('huuid').setValue(_row[0].get('uuid'));						 
						 win.add(_form);
						 win.show();
/*						var rowedit =   subgrid.getPlugin('subGridRowPlugin');
						rowedit.cancelEdit();
						 var substore = subgrid.getStore();
						 substore.insert(0, Ext.create('core.employee.quality.bills.ban.model.SubBanModel'));
						 rowedit.startEdit(0, 0);*/
			}}			},
			
			"BanMainView menuitem[ref=rowmodify]" : {
				click : function(_item) {
					var maingrid = _item.up('BanMainView').down('BanGrid');
					var mainrecord = maingrid.getSelectionModel().getSelection();
					 var grid = _item.up('BanMainView').down('SubBanGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }else if(mainrecord[0].get('appstatus') == '1'|| record[0].get('appstatus') == '2' ){
						 Ext.Msg.alert("提示","该单据已经审核");
					 }else if(Ext.getCmp("displaylogin").getValue().split(':')[1]!=record[0].get('operator')){
						 Ext.Msg.alert("提示","只允许删除自己上传的记录！");
					 }else{							 
					   var win = Ext.create ('Ext.window.Window',{
							    title: '处罚人员录入',
							    width:400,
							    height:300,
								autoScroll:true,
							    layout: 'fit',
							    closable :false,
							    modal : true,
					   });	
					   var banform	= Ext.create ("core.employee.quality.bills.ban.view.SubBanForm");						   
						 banform.getForm().load({
							 url:'./json/adqualitybill_getbysubuuid',
							 params:{data:record[0].get('uuid')},
							 failure: function(form, action) {
							        Ext.Msg.alert("Load failed", action.result.errorMessage);
							    }
						 });
				//		var employeecombo = banform.down("EmployeeCombo");
				//		var deptcombo = banform.down("DeptCombo");
				//		employeecombo.setDeptparam(deptcombo.getValue().pkDeptdoc);
						 Ext.Msg.alert("Load failed", banform.getForm().getValue());
						 win.add(banform);
						 win.show(); 
					 }			
				}},

			"BanMainView menuitem[ref=rowdelete]" : {
				click : function(_item) {
					var maingrid = _item.up('BanMainView').down('BanGrid');
					var mainrecord = maingrid.getSelectionModel().getSelection();
					 var grid = _item.up('BanMainView').down('SubBanGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }else if(mainrecord[0].get('appstatus') == '1' || record[0].get('appstatus') == '2'){
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
						//	        	subbanstore.getProxy().extraParams ={data : huuid};
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
				
			"BanMainView button[ref=review]" : {
				click : function(_item) {
						var grid = _item.up('BanMainView').down('BanGrid');
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
							        			   setTimeout(window.open('./fkpdf/'+obj.msg+'.pdf'),5000);     
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
							Ext.Msg.alert('提示','请先选择一行');
						}
			}},
			
			
				
				
			"BanGrid " : {
				itemclick:function(view,record,item,index,e,eOpts ){
					   var subgrid = view.up('BanMainView').down('SubBanGrid');
						var _store = subgrid.getStore();
			        	_store.getProxy().extraParams ={data : record.get('uuid')};
						_store.load();
			}},
				
		    "BanForm button[ref=close]" : {
				click : function(_btn) {
					closeWindow(_btn);			
				}},
			
			"BanFormSG button[ref=close]" : {
					click : function(_btn) {
						closeWindow(_btn);			
				}},
				
			"BanFormSG button[ref=save]" : {
					click : function(_btn) {
						banFormSubmit(_btn);		
					}},
					
			"SBBanForm button[ref=close]" : {
					click : function(_btn) {
							closeWindow(_btn);			
					}},
					
			"SBBanForm button[ref=save]" : {
					click : function(_btn) {
							banFormSubmit(_btn);		
						}},
			
			"BanForm button[ref=save]" : {
				click : function(_btn) {
					banFormSubmit(_btn);		
				}},
				
				
			"AHBanForm button[ref=close]" : {
					click : function(_btn) {
						closeWindow(_btn);			
				}},
				
			"AHBanForm button[ref=save]" : {
					click : function(_btn) {
						banFormSubmit(_btn);		
					}},
				
			"AH4SForm button[ref=close]" : {
				click : function(_btn) {
					closeWindow(_btn);			
			}},
				
			"AH4SForm button[ref=save]" : {
				click : function(_btn) {
					banFormSubmit(_btn);		
				}},
			
		   "SubBanForm button[ref=save]" : {
					click : function(_btn) {
						var _form = _btn.up("SubBanForm").getForm();
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
						    	var subbanstore = Ext.getCmp("MainViewLayout").down("SubBanGrid").getStore();
						    	subbanstore.getProxy().extraParams ={data : huuid};
						    	subbanstore.load();
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
					
		"SubBanForm combo[ref=deptcombo]" : {
				select : function( combo, records, eOpts ) {
					var employeecombo = combo.up("SubBanForm").down("EmployeeCombo");
					employeecombo.setDeptparam(records[0].get("pkDeptdoc"));
		}},
		
		
		"SubBanForm button[ref=close]" : {
				click : function(_btn) {
					closeWindow(_btn);
				}}
		})
	},
	views : ["core.employee.quality.bills.ban.view.BanMainView",
			 "core.employee.quality.bills.ban.view.BanForm",
			 "core.employee.quality.bills.ban.view.BanGrid",
			 "core.employee.employeeAdd.view.DeptCombo",			 
			 "core.employee.quality.xunjian.view.ProjectCombo",
			 "core.employee.quality.bills.ban.view.SubBanGrid",
			 "core.employee.quality.bills.ban.view.SubBanForm",
			 "core.util.EmployeeCombo",
			 "core.util.UploadFieldDef",
			 "core.util.MultiComboBox",
			 "core.util.ResourceCombo",
			 "core.util.GridSearchText",
		//	 "core.employee.quality.bills.ban.view.ResourceCombo",
			 "core.employee.quality.bills.ban.view.AHBanForm",
			 "core.employee.quality.bills.ban.view.AH4SForm",
			 "core.employee.quality.bills.ban.view.BanFormSG",
			 "core.employee.quality.bills.ban.view.BanSearchForm",
			 "core.employee.quality.bills.ban.view.SBBanForm"
			 ],
	stores : ["core.employee.employeeAdd.store.DeptStore",
		"core.employee.quality.xunjian.store.ProjectStore",
		"core.employee.quality.bills.ban.store.SubBanStore",
		"core.employee.quality.bills.ban.store.BanStore",
		"core.employee.quality.bills.ban.store.QualityForbidtypeStore",
	//	"core.employee.quality.bills.ban.store.ResourceStore",
		"core.employee.quality.bills.ban.store.AHForbidtypeStore",
		"core.employee.quality.bills.ban.store.SBProjectStore"],
	models : ["core.employee.quality.bills.ban.model.BanModel",
		//	  "core.employee.quality.bills.ban.model.ResourceModel",
			  "core.employee.quality.bills.ban.model.SubBanModel"			  
			  ]
});