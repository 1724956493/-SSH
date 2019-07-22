Ext.define("core.employee.quality.base.wzlist.view.WzTypeListTree", {
						extend : 'Ext.tree.Panel',
			alias : 'widget.WzTypeListTree',
			//title: 'tree测试',
			store : "core.employee.quality.base.wztype.store.WzTypeStore",
			width: 250,
			minSize: 100,
			maxSize: 300,
			rootVisible : false,
				   
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
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
			})