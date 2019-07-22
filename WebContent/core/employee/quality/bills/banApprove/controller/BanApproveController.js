Ext.define("core.employee.quality.bills.banApprove.controller.BanApproveController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		
		var approvebill = function(grid,uuid,approvestatus){
			Ext.Ajax.request({
			      url:'./json/adqualitybill_approve',
			      method:'POST',
			      params:{data:uuid,approvestatus : approvestatus},
			      success:function(response,option){ 
			    	Ext.Msg.alert("提示","审核成功");
			    	grid.getStore().reload();
			      },
			      failure:function(){
				    Ext.Msg.alert("提示","审核失败，请重新提交");
				  }
			})
		};
		
		this.control({
			"BanApproveMainView button[ref=search]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
					    title: '查询窗口',
					    width:400,
					    height:250,
						autoScroll:true,
					    layout: 'fit',
					    modal : true}
					);	
					var form = Ext.create("core.employee.quality.bills.banApprove.view.BanApproveSearchForm");
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
				}},	
				
			 "BanApproveSearchForm button[ref=search]" : {
					click : function(_btn) {
						var banGridStore = Ext.getCmp("MainViewLayout").down("BanApproveGrid").getStore();
						banGridStore.getProxy().extraParams ={data : JSON.stringify(_btn.up("BanApproveSearchForm").getForm().getValues())};
						banGridStore.reload();
						
						_btn.up('window').close();
					}},		
							
			"BanApproveMainView button[ref=review]" : {
					click : function(_item) {
						var grid = _item.up('BanApproveMainView').down('BanApproveGrid');
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
							Ext.Msg.alert('提示','请先选择一行');
						}
						
			}},	
					
			"BanApproveMainView button[ref=approve]" : {
						click : function(_btn) {
								var grid = _btn.up('BanApproveMainView').down('BanApproveGrid');
								var record = grid.getSelectionModel().getSelection();
								if(!record.length >0)
								{
									Ext.Msg.alert('提示','请先选择一行');
								}else{
									Ext.Msg.show({
									     title:'审批状态',
									     msg: '审核通过请按yes，审核不通过请按no，通报不处罚请按cancle',
									     buttons: Ext.Msg.YESNOCANCEL,
									     icon: Ext.Msg.QUESTION,
									     closable : false ,
									     buttonText : {ok: '审核通过', yes: '通报不处罚', no: '审核不通过', cancel: '取消审核'},
									     fn : function(buttonId){
									    	 if (buttonId == 'ok'){									    		 
									    	    approvebill(grid,record[0].get('uuid'),'1');
									    	}else if(buttonId == 'yes'){
									    		approvebill(grid,record[0].get('uuid'),'2');
									    	}else if(buttonId == 'no'){
									    		approvebill(grid,record[0].get('uuid'),'3');
									    	}else{
									    		approvebill(grid,record[0].get('uuid'),'0');
									    	}
									     }
									});
								}
			}},
			
			
			"BanApproveMainView button[ref=upload]" : {
				click : function(_btn) {
					 var grid = _btn.up('BanApproveMainView').down('BanApproveGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }
					 else if(record[0].get('appstatus') == '0' || record[0].get('appstatus') == '3' || record[0].get('appstatus') == ''){
						 Ext.Msg.alert("提示","请先审核当前单据!");
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
				    
	})
	},
	views :  ["core.employee.quality.bills.banApprove.view.BanApproveMainView",
			  "core.employee.quality.bills.banApprove.view.BanApproveGrid",
			  "core.employee.quality.bills.banApprove.view.BanApproveSearchForm",
			  "core.util.ResourceCombo",
			  "core.util.UploadFieldDef"],
	stores : ["core.employee.quality.bills.banApprove.store.BanApproveStore"],
	models : ["core.employee.quality.bills.banApprove.model.BanApproveModel"]
});