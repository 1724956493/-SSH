Ext.define("core.employee.quality.xunjian.controller.QualityController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		Ext.Ajax.timeout =300000;
		this.control({
		"QualityView" : {
			close : function( _panel){
				var _grid = _panel.down("QualityGrid");
				var _store = _grid.getStore();
				_store.removeAll();
			}				
			},
						
		"QualitySearchForm button[ref=search]" : {
				click : function(_btn) {
					var _form = _btn.up("QualitySearchForm");
					var _grid = _form.up("QualityView").down("QualityGrid");
					var data =_form.getForm().getValues();
					data.type = 0;
					var _store = _grid.getStore();			
				//	_store.getProxy().extraParams ={data222:encodeURI(JSON.stringify(data))};
					_store.getProxy().extraParams ={data : JSON.stringify(data)};
					_store.load();
				}},
				
		
				
		"QualitySearchForm button[ref=clearsearch]" : {
				click : function(_btn) {
					var _form = _btn.up("QualitySearchForm");
					var _grid = _form.up("QualityView").down("QualityGrid");
					var _store = _grid.getStore();
					var _view = _grid.getView();
					_store.clearGrouping();
					_view.refresh();
					_form.getForm().reset();
			//		Ext.Msg.alert("提醒","已刷新");
				}},
				
		"QualitySearchForm button[ref=export]" : {
				click : function(_btn) {
					var _form = _btn.up("QualitySearchForm");
					var _grid = _form.up("QualityView").down("QualityGrid");
					var data =_form.getForm().getValues();
					data.type = 0;
					
			        Ext.Ajax.request({
					      url:'./json/employee_downAllInfo',
					      method:'POST',
					      params:{data:JSON.stringify(data)},
					      success:function(response,option){ 
					        var obj =Ext.decode(response.responseText);	
					        if(obj.success == true){
					      		window.location.href ='./json/employee_downAllInfo2?data='+obj.filename;
					      	}else{
					      		Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击！")
					      	}
					      	},
					      failure:function(){
					      	 Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击")
					      }
					      });
				}},
				
				
				
	    "QualityGrid " : {
				itemdblclick:function(view,record,item,index,e,eOpts ){		
					var win = Ext.create ('Ext.window.Window',{
					    title: '违章详细信息',					    
					    height: 400,
					    width: 400,
					    layout: 'fit',
					    closable :false,
					    modal : true,
					    items: [
					   			 {xtype : 'QualityForm'}					    
					    ]}
					);					    
					    win.show();	
				
					var _form = win.down("QualityForm");
					var _formBase = _form.getForm();
		//			_form.findField('psnname').setValue();
					_formBase.findField('project').setReadOnly(true);
					_formBase.findField('empleader').setReadOnly(true);
					_formBase.findField('status').setReadOnly(true);
					_formBase.findField('cknote').setReadOnly(true);
					_formBase.reset();
					_formBase.loadRecord(record);
					var record2 = record.copy();
					
					var project = _formBase.findField('project').getStore().findRecord('jobname',record2.get('project').toUpperCase());
					var empleader = _formBase.findField('empleader').getStore().findRecord('psnname',record2.get('empleader'));
					
					_formBase.findField('project').setValue(project);
					_formBase.findField('empleader').setValue(empleader);
					_formBase.findField('uuid').setValue(record2.get('uuidrp'));
							
					Ext.get('QualityForm_save').setVisible(false);
					}},

		"QualityForm button[ref=modify]" : {
				click : function(_btn) {
					var _form = _btn.up("QualityForm");
					var _formBase = _form.getForm();
					var record = _formBase.getValues();
					var dis=Ext.getCmp("displaylogin");
					var diff = (Date.now() - new Date(record['create_time']))/(24 * 60 * 60 * 1000);

					if( dis.getValue().split(':')[1]!=record['operate']){
						Ext.Msg.alert("提示","只允许更改自己上传的记录！");
					}
					else if(diff > 1){
						Ext.Msg.alert('提示','录入时间已经超过一天，不允许修改！');						
					}else{
						Ext.get('EmprpForm_modify').setVisible(false);
						 Ext.get('QualityForm_save').setVisible(true);
						_formBase.findField('project').setReadOnly(false);
						_formBase.findField('empleader').setReadOnly(false);
						_formBase.findField('status').setReadOnly(false);
						_formBase.findField('cknote').setReadOnly(false);
					}
				}},
				
		"QualityForm button[ref=save]" : {
				click : function(_btn) {
					var _form = _btn.up("QualityForm").getForm();
					var mainview=Ext.getCmp("MainViewLayout");
					var _grid = mainview.down("QualityGrid");
					var data = _form.getValues();
					
				_form.submit({
						clientValidation:true,
						url:'./json/adpsndocrp_adPsndocRPUpdate',
						method:'POST',
						params:{data: JSON.stringify(data)},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    //	_form.reset();	
					   // 	Ext.get('QualityForm_modify').setVisible(true);
					    	 Ext.get('QualityForm_save').setVisible(false);
					    	 _grid.getStore().load();
					    	},
					    failure:function(form,action){
					    	Ext.Msg.alert('提示','失败');
					    }
					})
				}},
				
		"QualityForm button[ref=delete]" : {
				click : function(_btn) {
					var _form = _btn.up("QualityForm");
					var _grid = _form.up("QualityView").down("QualityGrid");
					var _store = _grid.getStore();
					var records = _form.getForm().getValues();
					
					Ext.MessageBox.confirm("重要提示",
							"是否要删除吗？",
							function(e){
								if(e == 'yes'){
									Ext.Ajax.request({
										waitMsg : '正在进行处理,请稍后...', 
										url : "./json/adpsndocrp_adPsndocRPDelete",
										params : {
											data : records['uuidrp']
										},// 根据id删除
										method : "POST",
										timeout : 4000,
										success : function(response, opts) {
											var resObj = Ext.decode(response.responseText);
											if (resObj.success) {
												// 不用查询，从grid中去掉对应的记录就OK了
												_store.reload();
												Ext.Msg.alert("提示", resObj.msg);
											} else {
												Ext.Msg.alert("提示", resObj.msg);
											}
										}
									});
								}
							});			
				}},
		
				
		"QualityForm button[ref=close]" : {
				click : function(_btn) {
					var win = _btn.up("window");			
					win.close();				
				}}
				
	
		})
	},
	views : ["core.employee.quality.xunjian.view.QualityView","core.employee.quality.xunjian.view.QualityGrid","core.employee.quality.xunjian.view.QualitySearchForm","core.employee.quality.xunjian.view.QCQACombo",
			 "core.employee.quality.xunjian.view.ProjectCombo","core.employee.quality.xunjian.view.LeaderCombo","core.employee.quality.xunjian.view.QualityForm","core.util.GridSearchText"],
	stores : ["core.employee.quality.xunjian.store.QualityStore","core.employee.quality.xunjian.store.ProjectStore","core.employee.quality.xunjian.store.LeaderStore","core.employee.quality.xunjian.store.QCQAStore"],
	models : ["core.employee.quality.xunjian.model.QualityModel","core.employee.quality.xunjian.model.ProjectModel","core.employee.quality.xunjian.model.LeaderModel"]
});