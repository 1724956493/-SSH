Ext.define("core.employee.energy.xunjian.view.EnergyView", {
			extend:"Ext.form.Panel",
			title:'能源巡检',
			alias : 'widget.EnergyView',
			autoScroll: true,	
			layout:'border',
			items :[
			{xtype:'EnergySearchForm',
			 region:'north'
	//		 title:'请选择查询条件',	
			},
			{
			 	xtype: 'EnergyGrid',
			 	 region:'center'
			},{
			 	xtype: 'EnergyForm',
			 	region:'east',
			 	hidden: true 
			}],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
			})