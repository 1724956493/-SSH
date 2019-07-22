Ext.define("core.app.view.MenuLayout", {
			extend : 'Ext.tree.Panel',
			alias : 'widget.Menulayout',
//			title: 'tree测试',
			store : "core.app.store.MenuStore",
			width: 225,
			minSize: 100,
			maxSize: 250,
			rootVisible: false,
		    initComponent: function(){
		        this.callParent(arguments);
		    }
});