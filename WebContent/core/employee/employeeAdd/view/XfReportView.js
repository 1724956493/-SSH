/**系统主程序界面布局类*/
Ext.define("core.employee.employeeAdd.view.XfReportView", {
			extend : 'Ext.panel.Panel',
			title:'人员消费报表',
			layout: 'border',
			closeable:true,
			alias : 'widget.XfReportView',
	//		layout : 'fit',
			tbar:[{
				xtype:'datefield',
				fieldLabel: '请选择查看消费日期',
				labelWidth: 120,
				value: Ext.Date.add(new Date(), Ext.Date.DAY, -1),
				format: 'Y-m-d',
				maxValue: new Date(),
       			name: 'from_date'				
				},{
				xtype:'button',
			    text:'导出消费记录表',
			    width: 120,
			    ref:"import"
			}],
					
			initComponent : function() {
				this.callParent(arguments);
			}
		});