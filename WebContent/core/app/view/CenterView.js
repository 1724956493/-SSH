/**
 * 程序布局放大中间的部分
 */
Ext.define("core.app.view.CenterView",{
	extend: 'Ext.tab.Panel',
	alias: 'widget.centerview',
	id:'centerid',
	defaults: { // defaults 将会应用所有的子组件上,而不是父容器
	},
	//margins: '2 0 0 0',
	border : 0,
	bodyStyle: 'padding:0px',
	menuAlign:"center",
	items:[{
		title:'<center height=40>首页</center>',
//		iconCls:'home',
		bodyPadding :5,
		layout:'fit',
		items:{
			//xtype:'taskjobgrid'
		},
		closable:true
	}],    
    
	initComponent:function(){
		this.callParent(arguments);
	}
});