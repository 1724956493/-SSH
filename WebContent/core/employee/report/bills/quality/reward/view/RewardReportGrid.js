Ext.define("core.employee.report.bills.quality.reward.view.RewardReportGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.RewardReportGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
	//		bodyPadding: 5,
			store:"core.employee.report.bills.quality.reward.store.RewardReportStore",
			features: [{
                ftype: 'summary'
            }],
			
            columnLines:true,
            
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'rewardreport_start',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'rewardreport_end',value: new Date()},
				  {	xtype:'combo',	name:'reporttype',name:'查询类型',id:'rewardreport_type',store:['按主管领导','按部门','按项目'],forceSelection:true,value:'按部门'},
				  {xtype: 'multicombobox',fieldLabel: '单据状态:',id:'rewardreport_appstatus',columnWidth: 1,name:'yiju',forceSelection:true,multiSelect:true,
				 		valueField: 'code',displayField: 'name', value : '1',  
				 		store:Ext.create('Ext.data.Store', {
				 			fields: ['name','code'],  
				 			data : [ 
				 				{name:'审核通过',code:'1'},{name:'审核不通过',code:'3'},{name:'未审核',code:'0'}
				 		    ]	
				  }),queryMode: 'local',editable : false,allowBlank :false},
				  {	xtype:'button',	text:'------>查询',	ref:'search'},
				  {text:'相关报表导出',menu: {
						xtype:'menu',
						items:[{text:'导出详细记录到EXCLE',ref:'exportlist'},
							   {text:'导出人员汇总到EXCLE',ref:'exportpsn'}]	
					}}
			     ],
			
			columns:[
				{ xtype: 'rownumberer', align: 'center'},				
				{ text: '', dataIndex: 'jobcode', align: 'center'},
				{ text: '项目一次性通过奖励', dataIndex: 'JLA1', width : 50,align: 'center',summaryType: 'sum'},
				{ text: '月度通过率奖励', dataIndex: 'JLA2', align: 'center',summaryType: 'sum'},			
				{ text: '合计', dataIndex: '', align: 'center',
					renderer : function(v, meta, record){ 
						return record.get("JLA1") + record.get("JLA2"); 
				 	},summaryType: 'sum'  
				},
				{ dataIndex: 'jobpk', align: 'center',hidden :true}
			],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})