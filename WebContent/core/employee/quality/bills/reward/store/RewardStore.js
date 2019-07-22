 Ext.define("core.employee.quality.bills.reward.store.RewardStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.bills.reward.model.RewardModel',
	alias: 'widget.RewardStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adqualitybill_getall',
        reader: {
            type: 'json'
        },
        actionMethods:{
        	read:'POST'
        },
         timeout:180000
    },

    sorters:[{
    	property: 'ts',  
    	direction: 'ASC'
    }]
 });