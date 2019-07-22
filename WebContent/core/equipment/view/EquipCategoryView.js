Ext.define("core.equipment.view.EquipCategoryView", {
			extend : 'Ext.tree.Panel',
			alias : 'widget.EquipCategoryView',
			//title: 'tree测试',
			store : "core.equipment.store.EquipCategoryStore",
			width: 225,
			minSize: 100,
			maxSize: 250,
			rootVisible : false,
			
			tbar: [
					{ xtype: 'button', text: '增加',ref:"add"},
					{ xtype: 'button', text: '修改',ref:"modify"},
					{ xtype: 'button', text: '删除',ref:"delete"}
				   ],
		    initComponent: function(){
		        this.callParent(arguments);
		    }
});