Ext.define("core.employee.report.quality.byproject.view.QualityByProjectReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.QualityByProjectReport',
			margins: '0 0 0 0',
			title:'巡检_按项目',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'QualityByProjectGrid',
				height:300,
			 	region:'north'
			},
			{
			 	xtype: 'QualityByProjectChart',
			 	height:400,
			 	region:'center'
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})