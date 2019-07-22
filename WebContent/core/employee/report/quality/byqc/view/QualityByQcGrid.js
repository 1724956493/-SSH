Ext.define("core.employee.report.quality.byqc.view.QualityByQcGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.QualityByQcGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			store:"core.employee.report.quality.byqc.store.QualityByQcStore",
			features: [{
                ftype: 'summary'
            }],
			
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'qualitybyqc_report_startd',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'qualitybyqc_report_endd',value: new Date()},
				  {	xtype:'button',	text:'------>查询',	border : '1,1,1,1',	ref:'search'	}
			     ],
			
			columns:[
				{ text: '部门', dataIndex: 'pk_psndoc', align: 'center',hidden :true},
				{ text: '部门', dataIndex: 'parentdept', align: 'center'},
				{ text: '子部门', dataIndex: 'dept', align: 'center'},
				{ text: '检验员姓名', dataIndex: 'psnname', align: 'center'},
				{ text: '检查数量', dataIndex: 'count', align: 'center',summaryType: 'sum'}			
			],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})