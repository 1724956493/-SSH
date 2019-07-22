Ext.define("core.employee.quality.bills.ban.view.SubBanGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.SubBanGrid',
			autoScroll :true,
			height: 150,
			store:"core.employee.quality.bills.ban.store.SubBanStore",

		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 10},
		   	  {dataIndex: 'uuid', align: 'center',hidden:true},
              { text: '部门', dataIndex: 'dept', align: 'center'},
              { text: '姓名', dataIndex: 'psnname', align: 'center'},
              { text: '职责级别', dataIndex: 'joblevel', align: 'center'},
              { text: '处罚金额', dataIndex: 'mulct', align: 'center'},
              { text: '备注', dataIndex: 'note', align: 'center'}
              ],
			
		initComponent : function() {
				this.callParent(arguments);}
			
})