Ext.define("core.authority.view.CheckedMenuView", {
			extend : 'Ext.tree.Panel',
			alias : 'widget.CheckedMenuView',
			//title: 'tree测试',
			store : "core.authority.store.CheckedMenuStore",
			width: 400,
			rootVisible : false,
			
			tbar: [
				{ xtype: 'button', text: '重新授权',ref:"reaccredit"}
			   ],
			

		    initComponent: function(){
		        this.callParent(arguments);
		    }
});