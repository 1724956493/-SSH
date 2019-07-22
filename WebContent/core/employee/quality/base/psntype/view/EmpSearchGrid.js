Ext.define("core.employee.quality.base.psntype.view.EmpSearchGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.EmpSearchGrid',
			autoScroll :true,
			scroll  : true ,
			store:"core.employee.quality.base.psntype.store.EmpSearchStore",
			
			tbar:[{xtype:'textfield',fieldLabel: '人员姓名'},
			{
				xtype:'button',
			    text:'查询',
			    width: 120,
			    ref:"search"
			}],
			
		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 50},
              { text: '基本PK', dataIndex: 'pkpsndoc', align: 'center',hidden:true},
              { text: '管理PK', dataIndex: 'pkpsnbasdoc', align: 'center',hidden:true},
              { text: '姓名', dataIndex: 'psnname', align: 'center' },
              { text: '身份证号', dataIndex: 'id', align: 'center' },
              { text: '部门', dataIndex: 'deptname', align: 'center', minWidth: 80}
              ],

			
		initComponent : function() {
				this.callParent(arguments);}
			
})