Ext.define("core.employee.quality.base.wztype.view.WzTypeGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.WzTypeGrid',
			autoScroll :true,
			scroll  : true ,
			store:"core.employee.quality.base.wztype.store.WzTypeStore",
		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 50},
              { text: '序号', dataIndex: 'uuid', align: 'center',hidden:true},
              { text: '违章类别编号', dataIndex: 'wzcode', align: 'center' },
              { text: '违章类别名称', dataIndex: 'wzname', align: 'center', minWidth: 80},
              { text: '工作部门', dataIndex: 'wzparent', align: 'center' },                        
              { text: '备注', dataIndex: 'note'},
              { text: '日期', dataIndex: 'createTime', align: 'center' ,minWidth: 120 }
              ],

			
		initComponent : function() {
				this.callParent(arguments);}
			
})