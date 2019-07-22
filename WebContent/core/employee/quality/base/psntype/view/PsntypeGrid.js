Ext.define("core.employee.quality.base.psntype.view.PsntypeGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.PsntypeGrid',
			autoScroll :true,
			store:"core.employee.quality.base.psntype.store.PsntypeStore",
		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 50},
              { dataIndex: 'uuid',hidden:true},
              { text: '姓名', dataIndex: 'psnname'},
              { text: '部门', dataIndex: 'deptpk',
              					renderer:function(val)
											{
												if(val!=""&&val!=null)
													{
														var jobstore =  Ext.getStore("core.employee.employeeAdd.store.DeptStore");
														var index = jobstore.find("pkDeptdoc",val);
														if(index == -1){return null;}
														else {return jobstore.getAt(index).get('deptname');}
													}	
												else
													{return null;}
											}
              },
              { text: '顶级部门', dataIndex: 'parentdeptpk', minWidth: 80,
              					renderer:function(val)
											{
												if(val!=""&&val!=null)
													{
														var jobstore =  Ext.getStore("core.employee.employeeAdd.store.DeptStore");
														var index = jobstore.find("pkDeptdoc",val);
														if(index == -1){return null;}
														else {return jobstore.getAt(index).get('deptname');}
													}	
												else
													{return null;}
											}       
              },
              { text: '工作区域', dataIndex: 'area'},                        
              { text: '主管项目', dataIndex: 'project'},
              { text: '日期', dataIndex: 'createTime',minWidth: 120 },
              { text: '人员类型', dataIndex: 'psntype'},
              { text: '备注', dataIndex: 'note'}
              ],

			
		initComponent : function() {
				this.callParent(arguments);}
			
})