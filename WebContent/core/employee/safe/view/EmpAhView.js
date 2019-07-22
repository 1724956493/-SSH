Ext.define("core.employee.safe.view.EmpAhView", {
			extend:"Ext.form.Panel",
			title:'安环巡检',
			alias :'widget.EmpAhView',
			autoScroll: true,	
			layout:'border',
			items :[
			{xtype:'EmpAhSearchForm',
			 region:'north'
	//		 title:'请选择查询条件',	
			},
			{
			 	xtype: 'EmpAhGrid',
			 	 region:'center'
			},{
			 	xtype: 'EmpAhForm',
			 	region:'east',
			 	hidden: true 
			}],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
			})