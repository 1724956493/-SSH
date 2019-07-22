Ext.define("core.employee.quality.close.view.QualityCloseView", {
			extend:"Ext.form.Panel",
			title:'工艺纪律巡检关闭',
			alias : 'widget.QualityCloseView',
			autoScroll: true,	
			layout:'border',
			items :[
			{xtype:'QualityCloseSearchForm',
			 region:'north'
			},
			{
			 	xtype: 'QualityCloseGrid',
			 	 region:'center'
			}],
						
			initComponent : function() {
				this.callParent(arguments);
			}
})