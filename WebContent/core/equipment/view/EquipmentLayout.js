/**系统主程序界面布局类*/
Ext.define("core.equipment.view.EquipmentLayout", {
			extend : 'Ext.panel.Panel',
			title:'设备点检管理',
			layout: 'border',
			alias : 'widget.EquipmentLayout',
			items : [{
						xtype : 'DeptTreeView',
						region:'west'
					},{
						xtype : 'EquipmentCheckReport',
						region:'center'
					}],
			initComponent : function() {
				this.callParent(arguments);
			}
		});