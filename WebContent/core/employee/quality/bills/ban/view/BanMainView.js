Ext.define("core.employee.quality.bills.ban.view.BanMainView", {
			extend:"Ext.form.Panel",
			title:'处罚报告编制',
			alias : 'widget.BanMainView',	
			frame:true,
			layout:'border',
			
			tbar:[
				{xtype: 'button', text: '新增',ref:'add',disabled:true},
				{xtype: 'button', text: '修改',ref:'modify'},
				{xtype: 'button', text: '删除',ref:'delete'}	,
				{xtype: 'button', text: '查询',ref:'search'}	,
				{text:'子表操作',menu: {
					xtype:'menu',
					items:[{text:'子表增加',ref:'rowadd'},
					//	   {text:'子表修改',ref:'rowmodify'},
						   {text:'子表删除',ref:'rowdelete'}]	
				}},
				{xtype: 'button', text: '预览',ref:'review'}
				],	

			
			items:[
				{xtype:'BanGrid',width:'100%',height:'60%',region:'center'},
				{xtype:'SubBanGrid',width:'100%',height:'40%',region:'south'}
			],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
})