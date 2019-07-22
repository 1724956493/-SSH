/**
 * 程序布局放大中间的部分
 */
Ext.define("core.authority.view.UserView",{
	extend: 'Ext.grid.Panel',
	alias: 'widget.UserView',
	//id:'UserView',
	margins: '2 0 0 0',
	border : 2,
	bodyStyle: 'padding:2px',
	 title: '用户列表',  
     width: 200,
     height:200,
     selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SINGLE"}),
     forceFit:true,
     store: 'core.authority.store.UserStore',  
     columns: [  
         { header: 'cuserid', dataIndex: 'cuserid',hidden:true},  
         { header: '用户名', dataIndex: 'userName'},  
         { header: '账号', dataIndex: 'userCode' } 
     ],  
     	plugins:[
         	Ext.create("Ext.grid.plugin.CellEditing")
         ],
         	tbar: [
				{ xtype: 'button', text: '增加'},
				{ xtype: 'button',text: '删除' },
			    { xtype: 'button', text: '保存' },
			    { xtype: 'button', text: '查看' }],
	initComponent:function(){
		this.callParent(arguments);
	}
});