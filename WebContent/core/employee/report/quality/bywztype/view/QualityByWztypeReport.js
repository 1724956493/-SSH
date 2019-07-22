Ext.define("core.employee.report.quality.bywztype.view.QualityByWztypeReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.QualityByWztypeReport',
			margins: '0 0 0 0',
			title:'巡检_按违章类别',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'QualityByWztypeGrid',
				height:300,
			 	region:'north'
			},
			{
			 	xtype: 'QualityByWztypeChart',
			 	height:400,
			 	region:'center'
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})