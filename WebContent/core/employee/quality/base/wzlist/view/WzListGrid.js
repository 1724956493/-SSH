Ext.define("core.employee.quality.base.wzlist.view.WzListGrid", {
			extend:"Ext.grid.Panel",
			alias : 'widget.WzListGrid',
			store : "core.employee.quality.base.wzlist.store.WzListStore",
			height : 250,
            enableKeyNav:true,  //可以使用键盘控制上下
	        columnLines:true, //展示竖线
	        scroll: true ,
	        defaults:{
	        	align: 'center'
	        },
	        columns:[
					  {xtype: 'rownumberer'},
		              { text: '序号', dataIndex: 'uuid',hidden:true},
		              { text: '违章明细编号', dataIndex: 'wzlistcode'},
		              { text: '违章明细内容', dataIndex: 'wzlistname',minWidth:200},
		         //     { text: '违章类别', dataIndex: 'wztype'},                        
		              { text: '违章类型', dataIndex: 'wzlisttype'},
		              { text: '违章扣分', dataIndex: 'wzlistscore'},
		              { text: '备注', dataIndex: 'note'},
		              { text: '建立日期', dataIndex: 'createTime'}						
					],
			
			initComponent : function() {
				this.callParent(arguments);
			}
			
})