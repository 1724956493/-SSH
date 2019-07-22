Ext.define("core.employee.quality.bills.rewardApprove.controller.RewardApproveController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		var type = 'QCJL' ;
		var rewardFormxtype ='RewardForm';
		var rewardForm = "core.employee.quality.bills.reward.view.RewardForm";
		
		this.control({
			"RewardApproveMainView button[ref=search]" : {
				click : function(_btn) {
					var win = Ext.create ('Ext.window.Window',{
					    title: '查询窗口',
					    width:400,
					    height:250,
						autoScroll:true,
					    layout: 'fit',
					    modal : true}
					);	
					var form = Ext.create("core.employee.quality.bills.rewardApprove.view.RewardApproveSearchForm");
					win.add(form);
					win.show();
				}},	
				
			 "RewardApproveSearchForm button[ref=search]" : {
					click : function(_btn) {
						var banGridStore = Ext.getCmp("MainViewLayout").down("RewardApproveGrid").getStore();
						banGridStore.getProxy().extraParams ={data : JSON.stringify(_btn.up("RewardApproveSearchForm").getForm().getValues())};
						banGridStore.reload();
						
						_btn.up('window').close();
					}},		
							
			"RewardApproveMainView button[ref=review]" : {
					click : function(_item) {
						var grid = _item.up('RewardApproveMainView').down('RewardApproveGrid');
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
					
			"RewardApproveMainView button[ref=approve]" : {
						click : function(_btn) {
								var grid = _btn.up('RewardApproveMainView').down('RewardApproveGrid');
								var record = grid.getSelectionModel().getSelection();
								if(!record.length >0)
								{
									Ext.Msg.alert('提示','请先选择一行');
								}else if(record[0].get('appstatus') == '1'){
									Ext.Msg.alert('提示','该单据已经审核，请不要重复审核！');																	
								}else{
									 Ext.Ajax.request({
									      url:'./json/adqualitybill_approve',
									      method:'POST',
									      params:{data:record[0].get('uuid'),approvestatus:'1'},
									      success:function(response,option){ 
									    	Ext.Msg.alert("提示","审核成功");
									    	grid.getStore().reload();
									      },
									      failure:function(){
										    Ext.Msg.alert("提示","审核失败，请重新提交");
										  }
									      })
								}
			}},
			
			
			"RewardApproveMainView button[ref=upload]" : {
				click : function(_btn) {
					 var grid = _btn.up('RewardApproveMainView').down('RewardApproveGrid');
					 var record = grid.getSelectionModel().getSelection();
					 if(record.length == 0)
					 {
						 Ext.Msg.alert("提示","请先选择一条数据!");
					 }
					 else if(record[0].get('appstatus') != '1'){
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
	views :  ["core.employee.quality.bills.rewardApprove.view.RewardApproveMainView",
			  "core.employee.quality.bills.rewardApprove.view.RewardApproveGrid",
			  "core.employee.quality.bills.rewardApprove.view.RewardApproveSearchForm",
		//	  "core.employee.quality.bills.ban.view.ResourceCombo",
			  "core.util.ResourceCombo",
			  "core.util.UploadFieldDef"],
	stores : ["core.employee.quality.bills.rewardApprove.store.RewardApproveStore"],
	models : ["core.employee.quality.bills.rewardApprove.model.RewardApproveModel"]
});