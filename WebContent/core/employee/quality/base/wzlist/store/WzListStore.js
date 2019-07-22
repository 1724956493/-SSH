 Ext.define("core.employee.quality.base.wzlist.store.WzListStore",{
 	extend:'Ext.data.Store',
	model:"core.employee.quality.base.wzlist.model.WzListModel",
	alias: 'widget.WzListStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adwztype_adWzListsoJson',
        reader: {
            type: 'json'
        }
    }
//    autoLoad:true
 });