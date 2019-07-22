Ext.define("core.employee.report.quality.bydept.view.QualityByDeptGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.QualityByDeptGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			store:"core.employee.report.quality.bydept.store.QualityByDeptStore",
			features: [{
                ftype: 'summary'
            }],
			
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'qualitybydept_report_startd',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'qualitybydept_report_endd',value: new Date()},
				  {	xtype:'button',	text:'------>查询',	border : '1,1,1,1',	ref:'search'	}
			     ],
			
			columns:[
			//	{ text: '部门', dataIndex: 'pk_psndoc', align: 'center',hidden :true},
				{ text: '部门', dataIndex: 'parentdept', align: 'center'},
				{ text: '子部门', dataIndex: 'dept', align: 'center'},
				{ text: '违章类别', dataIndex: 'wzname', align: 'center'},
				{ text: '违章明细', dataIndex: 'wzlistname', align: 'center'},
				{ text: '检查数量', dataIndex: 'count', align: 'center',summaryType: 'sum'}			
			],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})