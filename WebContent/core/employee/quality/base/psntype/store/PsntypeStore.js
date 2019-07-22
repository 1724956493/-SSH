 Ext.define("core.employee.quality.base.psntype.store.PsntypeStore",{
 	extend:'Ext.data.Store',
	model:"core.employee.quality.base.psntype.model.PsntypeModel",
	alias: 'widget.PsntypeStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsntype_getpsntypes',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });