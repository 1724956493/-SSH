Ext.define("core.employee.report.quality.byqc.view.QualityByQcReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.QualityByQcReport',
			margins: '0 0 0 0',
			title:'巡检_按QC',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'QualityByQcGrid',
				height:300,
			 	region:'north'
			},
			{
			 	xtype: 'QualityByQcChart',
			 	height:400,
			 	region:'center'
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})