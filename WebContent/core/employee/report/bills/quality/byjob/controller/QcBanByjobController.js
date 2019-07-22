Ext.define("core.employee.report.bills.quality.byjob.controller.QcBanByjobController", {
	extend : "Ext.app.Controller",
	init : function() {
		var self = this;
		this.control({
			
			"QcBanByjobGrid " : {
				beforedestroy : function(_grid){
					_grid.getStore().removeAll();
					}		
			},
					
			"QcBanByjobGrid button[ref=search]" : {
				click : function(_btn) {	
					var startdate = Ext.getCmp('banbillreport_start').getValue();
					var enddate = Ext.getCmp('banbillreport_end').getValue();
					var appstatus = Ext.getCmp('banbillreport_appstatus').getValue().toString();
					
					var _grid = _btn.up("QcBanByjobGrid");	
			//		var startdate = Ext.getCmp('banbillreport_start').getValue();
			//		var enddate = Ext.getCmp('banbillreport_end').getValue();
					var report = Ext.getCmp('banbillreport_type').getValue();
					var reportstore =  _grid.getStore();
					if(report=='按主管领导'){
						reportstore.getProxy().url = './json/adqualitybill_bymanager';  
						_grid.columns[1].setText('主管领导');
					}
					if(report=='按项目'){
						reportstore.getProxy().url = './json/adqualitybill_byproject';  
						_grid.columns[1].setText('项目');
					}
					if(report=='按部门'){
						reportstore.getProxy().url = './json/adqualitybill_bydept';  
						_grid.columns[1].setText('部门');
					}
					reportstore.getProxy().extraParams = { startdate : startdate , enddate : enddate ,appstatus : appstatus,type :'ZLGYX'};
					reportstore.load();
				}},
				
			"QcBanByjobGrid menuitem[ref=exportlist]" : {
				click : function(_btn) {					
					var startdate = Ext.getCmp('banbillreport_start').getValue();
					var enddate = Ext.getCmp('banbillreport_end').getValue();
					var appstatus = Ext.getCmp('banbillreport_appstatus').getValue().toString();
						
					Ext.Ajax.request({
					      url:'./json/adqualitybill_exportlist',
					      method:'POST',
					      params:{startdate:startdate,enddate:enddate,appstatus : appstatus,type :'ZLGYX'},
					      success:function(response,option){ 
					        var obj =Ext.decode(response.responseText);	
					        if(obj.success == true){
					      		window.location.href ='./json/upload_downFile?filePath='+obj.filePath+'&fileName='+obj.fileName;
					      	}else{
					      		Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击！")
					      	}
					      	},
					      failure:function(){
					      	 Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击")
					      }
					      });	
			}},
			
			"QcBanByjobGrid menuitem[ref=exportpsn]" : {
				click : function(_btn) {					
					var startdate = Ext.getCmp('banbillreport_start').getValue();
					var enddate = Ext.getCmp('banbillreport_end').getValue();
					var appstatus = Ext.getCmp('banbillreport_appstatus').getValue().toString();
						
					Ext.Ajax.request({
					      url:'./json/adqualitybill_exportpsn',
					      method:'POST',
					      params:{startdate:startdate,enddate:enddate,appstatus : appstatus},
					      success:function(response,option){ 
					        var obj =Ext.decode(response.responseText);	
					        if(obj.success == true){
					      		window.location.href ='./json/upload_downFile?filePath='+obj.filePath+'&fileName='+obj.fileName;
					      	}else{
					      		Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击！")
					      	}
					      	},
					      failure:function(){
					      	 Ext.Msg.alert("提示","文件生成失败，请过10秒重新点击")
					      }
					      });	
			}},
			
			"QcBanByjobGrid " : {
				celldblclick : function( _gridview, td, cellIndex, record, tr, rowIndex, e, eOpts ) {
					var startdate = Ext.getCmp('banbillreport_start').getValue();
					var enddate = Ext.getCmp('banbillreport_end').getValue();
					var appstatus = Ext.getCmp('banbillreport_appstatus').getValue().toString();
					var report = Ext.getCmp('banbillreport_type').getValue();		
					var fenxi = '';
					var jobpk = '';
										
					if(record.raw[cellIndex] == null){
						Ext.Msg.alert("提示","请选择非空单元格");
						return ;
					}else if(cellIndex == 1){
						jobpk = record.get('jobpk');					
					}
					else{
						fenxi = _gridview.ownerCt.columns[cellIndex].dataIndex;
						jobpk = record.get('jobpk');
					}
					
					var _store = Ext.create('core.employee.report.bills.quality.byjob.store.BanReportStore');
					
					if(report=='按主管领导'){
						_store.getProxy().extraParams ={begindate : startdate,enddate : enddate,appstatus : appstatus,manager : jobpk,fenxi :fenxi,type :'ZLGYX'};
					}
					if(report=='按项目'){
						_store.getProxy().extraParams ={begindate : startdate,enddate : enddate,appstatus : appstatus,project : jobpk,fenxi :fenxi,type :'ZLGYX'};
					}
					if(report=='按部门'){
						_store.getProxy().extraParams ={begindate : startdate,enddate : enddate,appstatus : appstatus,dept : jobpk,fenxi :fenxi,type :'ZLGYX'};
					}					
					
					_store.load();
					
					var _grid = Ext.create('core.employee.report.bills.quality.byjob.view.BanReportGrid',{
						store:_store
					});
					
					var win =  Ext.create('Ext.window.Window', {
						title:"查询信息",
						height: 400,
					    width: 800,
					    layout: 'fit',
					    modal :true
					});
					
					win.add(_grid);
					win.show();					
			}}
			
	})},
	views : ["core.employee.report.bills.quality.byjob.view.QcBanByjobReport","core.employee.report.bills.quality.byjob.view.QcBanByjobGrid",
			"core.employee.report.bills.quality.byjob.view.QcBanByjobChart","core.util.MultiComboBox",
			"core.employee.report.bills.quality.byjob.view.BanReportGrid"],
	stores : ["core.employee.report.bills.quality.byjob.store.QcBanByjobStore",
		"core.employee.report.bills.quality.byjob.store.BanReportStore"],
	models : ["core.employee.report.bills.quality.byjob.model.QcBanByjobModel","core.employee.quality.bills.ban.model.BanModel"]
});