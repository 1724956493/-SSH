Ext.define("core.employee.quality.bills.banApprove.view.BanApproveGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.BanApproveGrid',
			autoScroll :true,
			minheight:250,
			store:"core.employee.quality.bills.banApprove.store.BanApproveStore",


			
			columns: [            
			   	  {xtype: 'rownumberer',minWidth: 10},
			   	  {hidden: true, dataIndex: 'uuid'},
	              {text: '单据号', dataIndex: 'billcode', align: 'center'},
	              {text: '事故标题', dataIndex: 'billhead', align: 'center'},
	              {text: '项目号', dataIndex: 'project', align: 'center'},
	              {text: '项目对象', dataIndex: 'projectobj', align: 'center'},
	              {text: '主责部门', dataIndex: 'dept', align: 'center'},
	              {text: '外协单位', dataIndex: 'wbdept', align: 'center'},
	              {text: '发生日期', dataIndex: 'createdate', align: 'center'},
	              {text: '整改意见书编号', dataIndex: 'panding', align: 'center'},
	              {text: '上传时间', dataIndex: 'ts', align: 'center'},
	              {text:'审核状态',dataIndex:'appstatus',align:'center',renderer:function(val,metadata){
	             		if(val == '1'){
	             			 return '<span style="color:blue">' + '审核通过' + '</span>'; 
	             		}else if(val == '2'){
	             		     return '<span style="color:green">' + '通报不处罚' + '</span>';
	             		}else if(val == '3'){
	             		     return '<span style="color:red">' + '审核不通过' + '</span>';
	             		}else{
	             		     return '未审核';
	             		}
	              }},
	              {text: '操作人', dataIndex: 'operator', align: 'center'},
	              {text: '文档上传', dataIndex: 'scanfilename', align: 'center'},
	              {text: '处罚总金额', dataIndex: 'totalmulct', align: 'center'}
	            ],
		initComponent : function() {		
			this.callParent(arguments);
		}
			
})