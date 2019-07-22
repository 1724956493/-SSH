 Ext.define("core.employee.quality.bills.ban.store.SubBanStore",{
	 	extend:'Ext.data.Store',
		model:'core.employee.quality.bills.ban.model.SubBanModel',
		alias: 'widget.SubBanStore',

		proxy: {
	        type: 'ajax',
	        url:'./json/adqualitybill_getsuball',
	        reader: {
	            type: 'json'
	        },
	        actionMethods:{
	        	read:'POST'
	        },
	         timeout:180000
	    },
 });