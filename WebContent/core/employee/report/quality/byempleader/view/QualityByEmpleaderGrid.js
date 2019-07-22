Ext.define("core.employee.report.quality.byempleader.view.QualityByEmpleaderGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.QualityByEmpleaderGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			store:"core.employee.report.quality.byempleader.store.QualityByEmpleaderStore",
			features: [{
                ftype: 'summary'
            }],
			
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'qualitybyempleader_report_startd',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'qualitybyempleader_report_endd',value: new Date()},
				  {	xtype:'button',	text:'------>查询',	border : '1,1,1,1',	ref:'search'	}
			     ],
			
			columns:[
				{ text: '部门', dataIndex: 'empleader', align: 'center',hidden :true},
				{ text: '部门', dataIndex: 'pardept', align: 'center'},
				{ text: '子部门', dataIndex: 'dept', align: 'center'},
				{ text: '主管领导', dataIndex: 'psnname', align: 'center'},
				{ text: '违章数量', dataIndex: 'count', align: 'center',summaryType: 'sum'}			
			],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})