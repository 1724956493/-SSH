/**系统主程序界面布局类*/
Ext.define("core.employee.employeeAdd.view.EmViewLayout", {
			extend : 'Ext.panel.Panel',
			title:'新增人员列表',
			layout: 'border',
			closeable:true,
			id:'emviewlayout',
			alias : 'widget.EmViewLayout',
			layout : 'fit',
				items:[{
					xtype:"EmApplyGrid"					
				},{
					xtype:"EmInfoAddView",
					hidden:true
				}],
					
			initComponent : function() {
				this.callParent(arguments);
			}
		});