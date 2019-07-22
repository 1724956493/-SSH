Ext.define("core.employee.report.bills.quality.byjob.view.QcBanByjobGrid", {
			extend: 'Ext.grid.Panel',
			alias: 'widget.QcBanByjobGrid',
			margins: '0 0 0 0',
			//title:'人员新增',
	//		bodyPadding: 5,
			store:"core.employee.report.bills.quality.byjob.store.QcBanByjobStore",
			features: [{
                ftype: 'summary'
            }],
			
            columnLines:true,
            
			tbar:[{	xtype:'datefield',fieldLabel: '开始日期',maxValue: new Date(),format: 'Y-m-d',name:'startdate',id:'banbillreport_start',	value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)},
				  { xtype:'datefield',fieldLabel: '结束日期',format: 'Y-m-d',name:'enddate',	id:'banbillreport_end',value: new Date()},
				  {	xtype:'combo',	name:'reporttype',name:'查询类型',id:'banbillreport_type',store:['按主管领导','按部门','按项目'],forceSelection:true,value:'按部门'},
				  {xtype: 'multicombobox',fieldLabel: '单据状态:',id:'banbillreport_appstatus',columnWidth: 1,name:'yiju',forceSelection:true,multiSelect:true,
				 		valueField: 'code',displayField: 'name', value : '1',  
				 		store:Ext.create('Ext.data.Store', {
				 			fields: ['name','code'],  
				 			data : [ 
				 				{name:'审核通过',code:'1'},{name:'通报不处罚',code:'2'},{name:'审核不通过',code:'3'},{name:'未审核',code:'0'}
				 		    ]	
				  }),queryMode: 'local',editable : false,allowBlank :false},
				  {	xtype:'button',	text:'------>查询',	ref:'search'},
				  {text:'相关报表导出',menu: {
						xtype:'menu',
						items:[{text:'导出详细记录到EXCLE',ref:'exportlist'},
						//	   {text:'子表修改',ref:'rowmodify'},
							   {text:'导出人员汇总到EXCLE',ref:'exportpsn'}]	
					}}
			     ],
			
			columns:[
				{ xtype: 'rownumberer', align: 'center'},				
				{ text: '', dataIndex: 'jobcode', align: 'center'},
				{ text: '质量弄虚作假（报告与实际不符或造假行为）', dataIndex: 'A101', width : 50,align: 'center',summaryType: 'sum'},
				{ text: '外检项目三次及三次以上报检', dataIndex: 'A102', align: 'center',summaryType: 'sum'},
				{ text: '除部门不可抗力外的因素导致外检拒检', dataIndex: 'A103', align: 'center',summaryType: 'sum'},
				{ text: '不合格品流转下道', dataIndex: 'A104', align: 'center',summaryType: 'sum'},
				{ text: '同类质量事故公司内重复发生', dataIndex: 'A105', align: 'center',summaryType: 'sum'},
				{ text: '员工未经过培训就上岗 ', dataIndex: 'A106', align: 'center',summaryType: 'sum'},
				{ text: '材料用错/错用', dataIndex: 'A107', align: 'center',summaryType: 'sum'},
				{ text: '船东书面质量投诉', dataIndex: 'A108', align: 'center',summaryType: 'sum'},
				{ text: '未按图纸施工', dataIndex: 'A109', align: 'center',summaryType: 'sum'},
				{ text: '焊道夹杂不清', dataIndex: 'A110', align: 'center',summaryType: 'sum'},
				{ text: '双相工艺违章', dataIndex: 'A111', align: 'center',summaryType: 'sum'},
				{ text: '项目未准备好', dataIndex: 'A112', align: 'center',summaryType: 'sum'},
				{ text: '未按要求预热', dataIndex: 'A113', align: 'center',summaryType: 'sum'},				
				{ text: '合计', dataIndex: '', align: 'center',
					renderer : function(v, meta, record){ 
						return record.get("A101") + record.get("A102")+ record.get("A103")+ record.get("A104")+ record.get("A105")+ record.get("A106")+ record.get("A107")+ record.get("A108")+ record.get("A109")+ record.get("A110")+ record.get("A111")+ record.get("A112"); 
				 	},summaryType: 'sum'  
				},
				{ dataIndex: 'jobpk', align: 'center',hidden :true}
			],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})