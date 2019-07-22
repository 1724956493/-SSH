/**系统主程序界面布局类*/
Ext.define("core.authority.view.TestViewLayout", {
			extend : 'Ext.panel.Panel',
			title:'菜单管理',
			layout: 'border',
			closeable:true,
			alias : 'widget.TestViewlayout',
			items : [{
						xtype : 'MenuFormView',
						width :300,
						region:'center',
					},{
						xtype : 'MenuView',
						region:'west',
					}],
			initComponent : function() {
				this.callParent(arguments);
			}
		});