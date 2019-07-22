Ext.define("core.employee.quality.base.wztype.view.WzTypeTree", {
			extend : 'Ext.tree.Panel',
			alias : 'widget.WzTypeTree',
			//title: 'tree测试',
			store : "core.employee.quality.base.wztype.store.WzTypeStore",
			width: 250,
			minSize: 100,
			maxSize: 300,
			rootVisible : false,
			
			tbar: [
					{ xtype: 'button', text: '增加',ref:"add"},
					{ xtype: 'button', text: '修改',ref:"modify"},
					{ xtype: 'button', text: '删除',ref:"delete"}
				   ],
				   
		 columns: [{
            xtype: 'treecolumn',
            text: '类别序号',
            dataIndex: 'code',
            width: 150,
            sortable: true
	        }, {
	            text: '类别名称',
	            dataIndex: 'text',
	            flex: 1,
	            sortable: true
	        }],	   
			
		    initComponent: function(){
		        this.callParent(arguments);
		    }
});