 Ext.define("core.employee.quality.bills.reward.store.SubRewardStore",{
	 	extend:'Ext.data.Store',
		model:'core.employee.quality.bills.reward.model.SubRewardModel',
		alias: 'widget.SubRewardStore',

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