Ext.define("core.employee.report.quality.byempleader.view.QualityByEmpleaderReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.QualityByEmpleaderReport',
			margins: '0 0 0 0',
			title:'巡检_按主管领导',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'QualityByEmpleaderGrid',
				height:300,
			 	region:'north'
			},
			{
			 	xtype: 'QualityByEmpleaderChart',
			 	height:400,
			 	region:'center'
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})