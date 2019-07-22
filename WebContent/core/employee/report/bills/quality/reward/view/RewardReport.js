Ext.define("core.employee.report.bills.quality.reward.view.RewardReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.RewardReport',
			margins: '0 0 0 0',
			title:'奖励报告',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'RewardReportGrid',
				height:300,
			 	region:'center'
			},
			{
			 	xtype: 'RewardReportChart',
			 	height:400,
			 	region:'south',
			 	 split:true
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})