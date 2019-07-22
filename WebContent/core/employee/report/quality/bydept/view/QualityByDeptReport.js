Ext.define("core.employee.report.quality.bydept.view.QualityByDeptReport", {
			extend: 'Ext.panel.Panel',
			alias: 'widget.QualityByDeptReport',
			margins: '0 0 0 0',
			title:'巡检_按部门',
			bodyPadding: 5,
		//	height:400,
		//	width:250,		
			layout:'border',
			
		items:[
			{
				xtype:'QualityByDeptGrid',
				height:300,
			 	region:'north'
			},
			{
			 	xtype: 'QualityByDeptChart',
			 	height:400,
			 	region:'center'
			}],	
			
			
  
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})