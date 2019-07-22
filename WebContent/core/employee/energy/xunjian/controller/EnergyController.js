Ext.define("core.employee.energy.xunjian.controller.EnergyController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		Ext.Ajax.timeout =300000;
		this.control({
		"EnergyView" : {
			close : function( _panel){
				var _grid = _panel.down("EnergyGrid");
				var _store = _grid.getStore();
				_store.removeAll();
			}				
			},
						
		"EnergySearchForm button[ref=search]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergySearchForm");
					var _grid = _form.up("EnergyView").down("EnergyGrid");
					var data =_form.getForm().getValues();
					data.type = 2;
					var _store = _grid.getStore();			
				//	_store.getProxy().extraParams ={data222:encodeURI(JSON.stringify(data))};
					_store.getProxy().extraParams ={data : JSON.stringify(data)};
					_store.load();
				}},
				
		
				
		"EnergySearchForm button[ref=clearsearch]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergySearchForm");
					var _grid = _form.up("EnergyView").down("EnergyGrid");
					var _store = _grid.getStore();
					var _view = _grid.getView();
					_store.clearGrouping();
					_view.refresh();
					_form.getForm().reset();
			//		Ext.Msg.alert("提醒","已刷新");
				}},
				
		"EnergySearchForm button[ref=export]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergySearchForm");
					var _grid = _form.up("EnergyView").down("EnergyGrid");
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
				
				
				
	    "EnergyGrid " : {
				itemdblclick:function(view,record,item,index,e,eOpts ){				
					var _form = view.up("EnergyView").down("EnergyForm");
					var _formBase = _form.getForm();
				//	_form.findField('psnname').setValue();
					_formBase.findField('project').setReadOnly(true);
					_formBase.findField('empleader').setReadOnly(true);
					_formBase.findField('status').setReadOnly(true);
					_formBase.findField('cknote').setReadOnly(true);
					_formBase.reset();
					_formBase.loadRecord(record);
					_formBase.findField('project').setRawValue(record.get('project'));
					_formBase.findField('empleader').setRawValue(record.get('empleader'));
					Ext.get('EnergyForm_save').setVisible(false);
					_form.show();
					}},

		"EnergyForm button[ref=modify]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergyForm");
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
					//	Ext.get('EmprpForm_modify').setVisible(false);
						 Ext.get('EnergyForm_save').setVisible(true);
						_formBase.findField('project').setReadOnly(false);
						_formBase.findField('empleader').setReadOnly(false);
						_formBase.findField('status').setReadOnly(false);
						_formBase.findField('cknote').setReadOnly(false);
					}
				}},
				
		"EnergyForm button[ref=save]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergyForm").getForm();
					var _grid = _btn.up("EnergyView").down("EnergyGrid");
					var data = _form.getValues();
					_form.submit({
						clientValidation:true,
						url:'./json/adpsndocrp_adPsndocRPInsert',
						method:'POST',
						params:{data: JSON.stringify(data)},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	_form.reset();	
					   // 	Ext.get('EmprpForm_modify').setVisible(true);
					    	 Ext.get('EnergyForm_save').setVisible(false);
					    	_grid.getStore().load();
					    	},
					    failure:function(form,action){
					    	Ext.Msg.alert('提示','失败');
					    }
					})		
				}},
				
		"EnergyForm button[ref=delete]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergyForm");
					var _grid = _form.up("EnergyView").down("EnergyGrid");
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
											data : records['uuidRp']
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
		
				
		"EnergyForm button[ref=close]" : {
				click : function(_btn) {
					var _form = _btn.up("EnergyForm");			
					_form.hide();				
				}}
				

		})
	},
	views : ["core.employee.energy.xunjian.view.EnergyView","core.employee.energy.xunjian.view.EnergyGrid","core.employee.energy.xunjian.view.EnergySearchForm",
			 "core.employee.quality.xunjian.view.ProjectCombo","core.employee.quality.xunjian.view.LeaderCombo","core.employee.energy.xunjian.view.EnergyForm","core.util.GridSearchText"],
	stores : ["core.employee.energy.xunjian.store.EnergyStore","core.employee.quality.xunjian.store.ProjectStore","core.employee.quality.xunjian.store.LeaderStore"],
	models : ["core.employee.energy.xunjian.model.EnergyModel"]
});