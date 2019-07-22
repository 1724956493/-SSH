Ext.define("core.employee.quality.bills.reward.view.RewardGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.RewardGrid',
			autoScroll :true,
			minheight:250,
			store:"core.employee.quality.bills.reward.store.RewardStore",			
			
		columns: [            
		   	  {xtype: 'rownumberer',minWidth: 10},
		   	  {hidden: true, dataIndex: 'uuid'},
              {text: '单据号', dataIndex: 'billcode', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '奖励标题', dataIndex: 'billhead', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '项目号', dataIndex: 'project', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '项目对象', dataIndex: 'projectobj', align: 'center'},
              {text: '奖励部门', dataIndex: 'dept', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '外协单位', dataIndex: 'wbdept', align: 'center',items: { xtype: 'GridSearchText'}},
              {text: '发生日期', dataIndex: 'createdate', align: 'center'},
              {text: '上传时间', dataIndex: 'ts', align: 'center'},
              {text: '周', dataIndex: 'diaocha', align: 'center',items: { xtype: 'GridSearchText'}},
              {text:'审核状态',dataIndex:'appstatus',align:'center',renderer:function(val,metadata){
             		if(val == '1'){
             			 return '<span style="color:red">' + '已审核' + '</span>';
            //  			metadata.style = 'background-color:#CCFFFF;'; 
             		}else{
             		     return '<span style="color:green">' + '未审核' + '</span>';
             		}
              }},
              {text: '文档上传', dataIndex: 'scanfilename', align: 'center'},
              {text: '操作人', dataIndex: 'operator', align: 'center'}
            ],
			
		initComponent : function() {		
			this.callParent(arguments);
		}
			
})