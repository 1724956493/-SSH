Ext.define("core.authority.view.MenuView", {
			extend : 'Ext.tree.Panel',
			alias : 'widget.MenuView',
			//title: 'tree测试',
			store : "core.authority.store.CheckedMenuStore",
			width: 225,
			minSize: 100,
			maxSize: 250,
			rootVisible : false,
			
			tbar: [
					{ xtype: 'button', text: '增加',ref:"add"},
					{ xtype: 'button', text: '修改',ref:"modify"},
					{ xtype: 'button', text: '删除',ref:"delete"},
					{xtype: 'button', text: '增加资源',ref:"addresource"}					
				   ],
		    initComponent: function(){
		        this.callParent(arguments);
		    }
});