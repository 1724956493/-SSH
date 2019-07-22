Ext.define("core.guard.carinfo.view.CarInfoView", {
			extend:"Ext.form.Panel",
			title:'车辆台帐登记',
			alias :'widget.CarInfoView',
			autoScroll: true,
			layout:'fit',
			items :[
			{
			 	xtype: 'CarInfoGrid'
			},{
				xtype: 'window',
				title:'车辆登记信息',
				height: 400,
			    width: 350,
			    layout: 'fit',
			    modal: true ,
			    closeAction : 'hide',
			    items:{
			    	xtype: 'CarInfoForm'
			    },
			    hidden:true
			}],
						
			initComponent : function() {
				this.callParent(arguments);
			}
			
})