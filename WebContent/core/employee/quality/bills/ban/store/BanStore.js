 Ext.define("core.employee.quality.bills.ban.store.BanStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.bills.ban.model.BanModel',
	alias: 'widget.BanStore',
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
    	property: 'billcode',  
    	direction: 'ASC'
    }]
 });