Ext.define("core.employee.quality.base.wztype.view.WzTypeView", {
			extend:"Ext.panel.Panel",
			title:'违章类别',
			alias : 'widget.WzTypeView',
			autoScroll: true,	
			layout:'border',
			items :[
			{
				xtype:'WzTypeTree',
			 	region:'west'
			},
			{
			 	xtype: 'WzTypeForm',
			 	region:'center'
			}],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
			})