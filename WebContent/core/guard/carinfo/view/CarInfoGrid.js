Ext.define("core.guard.carinfo.view.CarInfoGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.CarInfoGrid',
	//		minHeight: 400,
			autoScroll :true,
			store:"core.guard.carinfo.store.CarInfoStore",
			viewConfig: {
       		 } ,
       
       	selModel: {
                selType: 'checkboxmodel',
                mode: "SINGLE" 
            },
       		 
       
       	tbar:[
			{
				xtype:'button',
			    text:'新增',
			    ref:"create"
			},
			{
				xtype:'button',
			    text:'修改',
			    ref:"modify"
			},{
				xtype:'button',
				text:'删除',
				ref:'delete'
			}],
       		 
		    columns: [            
		   	  {xtype: 'rownumberer',minWidth: 50},
              { text: '序列号', dataIndex: 'uuid', align: 'left',hidden:true},
		   	  { text: '车牌号', dataIndex: 'carid', align: 'center',items: { xtype: 'GridSearchText'} },
              { text: '车型', dataIndex: 'cartype' },
              { text: '特征', dataIndex: 'carcolor', align: 'left', minWidth: 80 },
              { text: '车主', dataIndex: 'carowner', align: 'left' ,items: { xtype: 'GridSearchText'}},                        
              { text: '部门', dataIndex: 'ownerdept',items: { xtype: 'GridSearchText'}},
              { text: '岗位', dataIndex: 'ownerjob'},
              { text: '联系方式', dataIndex: 'ownertelephone'},
              { text: '登记时间', dataIndex: 'createTime'},
              { text: '备注', dataIndex: 'note'}
              ],
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})