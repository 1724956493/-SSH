 Ext.define("core.employee.report.bills.quality.reward.store.RewardListStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.bills.reward.model.RewardModel',
	alias: 'widget.RewardListStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adqualitybill_getreportgriddata',
        reader: {
            type: 'json'
        },
        actionMethods:{
        	read:'POST'
        },
        timeout:180000
    },
    sorters:[{
    	property: 'billcode',  
    	direction: 'ASC'
    }]
 });