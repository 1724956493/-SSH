/**系统主程序界面布局类*/
Ext.define("core.authority.view.RoleViewLayout", {
			extend : 'Ext.panel.Panel',
			title:'角色管理',
			layout: 'border',
			closeable:true,
			alias : 'widget.RoleViewLayout',
			items : [{
						xtype : 'RoleView',
						region:'west',
					//	selType:"rowmodel",
						selModel:{mode: "singel"},
						hideHeaders:true
					},{
						xtype : 'RoleFormView',
						region:'center',
					},{
						xtype : 'CheckedMenuView',
						region:'east',
					}],
			initComponent : function() {
				this.callParent(arguments);
			}
		});