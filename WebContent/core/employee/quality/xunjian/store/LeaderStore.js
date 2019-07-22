 Ext.define("core.employee.quality.xunjian.store.LeaderStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.xunjian.model.LeaderModel',
	alias: 'widget.LeaderStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/user_getExtManager',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });