Ext.define("core.employee.quality.base.psntype.view.PsntypeView", {
			extend:"Ext.panel.Panel",
			title:'人员岗位设置',
			alias : 'widget.PsntypeView',
			autoScroll: true,	
			layout :'border',
			items :[
	       	{xtype: 'PsntypeGrid',region:'center'},
			{
				xtype: 'PsntypeForm',
		//		collapsible: true,
				height : 200,
				region: 'south'
			}],
						
			initComponent : function() {
				this.callParent(arguments);
			}			
})