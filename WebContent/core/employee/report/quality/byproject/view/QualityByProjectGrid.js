Ext.define("core.employee.report.quality.byproject.view.QualityByProjectGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.QualityByProjectGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			store:"core.employee.report.quality.byproject.store.QualityByProjectStore",
			
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'qualitybyproject_report_startd',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'qualitybyproject_report_endd',value: new Date()},
				  {	xtype:'button',	text:'------>查询',	border : '1,1,1,1',	ref:'search'}
			     ],
			
			columns:[
				{ text: '部门', dataIndex: 'project', align: 'center',hidden :true},
				{ text: '项目号', dataIndex: 'jobname', align: 'center'},
				{ text: '检查数量', dataIndex: 'count', align: 'center'}			
			],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})