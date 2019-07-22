Ext.define("core.employee.report.bills.quality.byjob.view.QcBanByjobReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.QcBanByjobReport',
			margins: '0 0 0 0',
			title:'高压线_按工程',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'QcBanByjobGrid',
				height:300,
			 	region:'center'
			},
			{
			 	xtype: 'QcBanByjobChart',
			 	height:400,
			 	region:'south',
			 	 split:true
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})