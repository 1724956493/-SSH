Ext.define("core.employee.report.quality.bywztype.view.QualityByWztypeGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.QualityByWztypeGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			store:"core.employee.report.quality.bywztype.store.QualityByWztypeStore",
			
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'qualitybywztype_report_startd',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'qualitybywztype_report_endd',value: new Date()},
				  {	xtype:'button',	text:'------>查询',	border : '1,1,1,1',	ref:'search'	}
			     ],
			
			columns:[
				{ text: '序列号', dataIndex: 'uuid', align: 'center',hidden :true},
				{ text: '违章类别', dataIndex: 'wzname', align: 'center'},
				{ text: '违章明细', dataIndex: 'wzlistname',minwidth: 450},
				{ text: '违章数量', dataIndex: 'count', align: 'center',summaryType: 'sum'}	,
				{ text: '违章明细', dataIndex: 'wzlisttype',align: 'center'}
			],
			
		features: [{
	        groupHeaderTpl: '{name}',           
	        ftype: 'groupingsummary',
	        hideGroupedHeader: true,
            enableGroupingMenu: false,
            startCollapsed :false
	    }],	
		
		initComponent : function() {
				this.callParent(arguments);
			}
			
})