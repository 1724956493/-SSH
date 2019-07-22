/**
 * 程序布局放大中间的部分
 */
Ext.define("core.authority.view.RoleView",{
	extend: 'Ext.grid.Panel',
	alias: 'widget.RoleView',
	//id:'RoleView',
	margins: '2 0 0 0',
	border : 2,
	bodyStyle: 'padding:2px',
	 title: '角色列表',  
     width: 200,
     selType: "checkboxmodel",
     //selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
     selModel:{mode: "SIMPLE"},
     forceFit:true,
     store:'core.authority.store.RoleStore',  
     columns: [  
         { header: 'uuidRole', dataIndex: 'uuidRole',hidden:true},  
         { header: 'rolename', dataIndex: 'rolename'},  
      // { header: 'createTime', dataIndex: 'createTime' }  ,
     ],  
     	plugins:[
         	Ext.create("Ext.grid.plugin.CellEditing")
         ],
         	tbar: [
				{ xtype: 'button', text: '增加',ref:"add"},
				{ xtype: 'button', text: '删除',ref:"delete"}
		  ],
	initComponent:function(){
		this.callParent(arguments);
	}
});