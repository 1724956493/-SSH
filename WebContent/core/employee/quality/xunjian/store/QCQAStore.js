 Ext.define("core.employee.quality.xunjian.store.QCQAStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.xunjian.model.LeaderModel',
	alias: 'widget.QCQAStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/user_getQAQC',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });