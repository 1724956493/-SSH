Ext.define("core.employee.quality.bills.reward.view.SubRewardGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.SubRewardGrid',
			autoScroll :true,
			height: 150,
			store:"core.employee.quality.bills.reward.store.SubRewardStore",

		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 10},
		   	  {dataIndex: 'uuid', align: 'center',hidden:true},
              { text: '部门', dataIndex: 'dept', align: 'center'},
              { text: '姓名', dataIndex: 'psnname', align: 'center'},
              { text: '职责级别', dataIndex: 'joblevel', align: 'center'},
              { text: '奖励金额', dataIndex: 'reward', align: 'center'},
              { text: '备注', dataIndex: 'note', align: 'center'}
              ],
			
		initComponent : function() {
				this.callParent(arguments);}
			
})