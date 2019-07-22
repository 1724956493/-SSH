Ext.define("core.employee.quality.base.wzlist.view.WzListView", {
			extend:"Ext.panel.Panel",
			title:'违章明细设置',
			alias : 'widget.WzListView',
			layout:'border',
			items :[{xtype:'WzTypeListTree',region:'west'},
					{xtype:'panel',region:'center',layout:'border',
						items:[
							{xtype:'WzListGrid',region:'north'},
							{xtype:'WzListForm',region:'center'}
						]
					}],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
})