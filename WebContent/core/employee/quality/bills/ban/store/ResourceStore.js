 Ext.define("core.employee.quality.bills.ban.store.ResourceStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.bills.ban.model.ResourceModel',
	alias: 'widget.ResourceStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/resource_getResource',
        reader: {
            type: 'json'
        },
        actionMethods:{
        	read:'POST'
        },
      
        timeout:180000
    }
 });