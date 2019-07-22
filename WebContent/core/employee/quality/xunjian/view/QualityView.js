Ext.define("core.employee.quality.xunjian.view.QualityView", {
			extend:"Ext.form.Panel",
			title:'工艺纪律巡检',
			alias : 'widget.QualityView',
			autoScroll: true,	
			layout:'border',
			items :[
		    {xtype:'QualitySearchForm',
			 region:'north'
		//	 title:'请选择查询条件',	
			},
			{
			 	xtype: 'QualityGrid',
			 	 region:'center'
			}/*,{
			 	xtype: 'QualityForm',
			 	region:'east',
			 	hidden: true 
			}*/],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
			})