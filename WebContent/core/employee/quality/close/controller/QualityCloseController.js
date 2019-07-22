Ext.define("core.employee.quality.close.controller.QualityCloseController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		Ext.Ajax.timeout =300000;
		this.control({
		"QualityCloseView" : {
			close : function( _panel){
				var _grid = _panel.down("QualityCloseGrid");
				var _store = _grid.getStore();
				_store.removeAll();
			}				
			},
						
		"QualityCloseSearchForm button[ref=search2]" : {
				click : function(_btn) {
					var _form = _btn.up("QualityCloseSearchForm");
					var _grid = _form.up("QualityCloseView").down("QualityCloseGrid");
					var data =_form.getForm().getValues();
					data.type = 0;
					data.status ="违规" ;
					data.paystatus =1 ;
					var _store = _grid.getStore();			
				//	_store.getProxy().extraParams ={data222:encodeURI(JSON.stringify(data))};
					_store.getProxy().extraParams ={data : JSON.stringify(data)};
					_store.load();
				}},
				
		"QualityCloseSearchForm button[ref=clearsearch]" : {
				click : function(_btn) {
					var _form = _btn.up("QualityCloseSearchForm");
					var _grid = _form.up("QualityCloseView").down("QualityCloseGrid");
					var _store = _grid.getStore();
					var _view = _grid.getView();
					_store.clearGrouping();
					_view.refresh();
					_form.getForm().reset();
			//		Ext.Msg.alert("提醒","已刷新");
				}},
		
		"QualityCloseSearchForm button[ref=payclose]" : {
		       click : function(_btn) {
				  var _form = _btn.up("QualityCloseSearchForm");
				  var _grid = _form.up("QualityCloseView").down("QualityCloseGrid");
				  var _row = _grid.getSelectionModel().getSelection();
				  var a33 =Ext.getCmp("displaylogin").getValue().split(':');
				  
				  if(a33.length<2)
				  {
						Ext.Msg.alert("提示","请先登录");
				   }
				  else if(_row.length<=0)
				  	{
						Ext.Msg.alert("提示","请先选择至少一行数据");
					}else
				 	{
				 		var data ="";
					  	for(var i =0;i<_row.length;i++){				  
					  	 data =  _row[i].get("uuidrp")+";"+data ;
					 	}
					 	Ext.MessageBox.confirm("重要提示","缴费总数量为"+_row.length+"条，请确认数量！",
							function(e){
								if(e == 'yes'){
								  Ext.Ajax.request({
										      url:'./json/adpsndocrp_getRP2payclose',
										      method:'POST',
										      params:{data:data},
										      success:function(response,option){ 
										        var obj =Ext.decode(response.responseText);	
										        if(obj.success == true){
										        	_grid.getStore().reload();
										      		Ext.Msg.alert("提示","提交成功");
										      	}else{
										      		Ext.Msg.alert("提示","提交失败，请重新提交！")
										      	}
										      	},
										      failure:function(){
										      	 Ext.Msg.alert("提示","提交失败，请重新提交一下！")
										      }
										   });	
					
				      }})	}		 	
		}}
		})
	},
	views : ["core.employee.quality.close.view.QualityCloseView","core.employee.quality.close.view.QualityCloseGrid","core.employee.quality.close.view.QualityCloseSearchForm",
			"core.employee.quality.xunjian.view.ProjectCombo","core.employee.quality.xunjian.view.LeaderCombo","core.util.GridSearchText"],
	stores : ["core.employee.quality.xunjian.store.ProjectStore","core.employee.quality.xunjian.store.LeaderStore","core.employee.quality.close.store.QualityCloseStore"],
	models : ["core.employee.quality.close.model.QualityCloseModel"]
});