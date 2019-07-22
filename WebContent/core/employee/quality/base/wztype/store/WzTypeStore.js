 Ext.define("core.employee.quality.base.wztype.store.WzTypeStore",{
 	extend:'Ext.data.TreeStore',
	model:'core.employee.quality.base.wztype.model.WzTypeModel',
	alias: 'widget.WzTypeStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adwztype_adWztypesoJson',
        reader: {
            type: 'json'
        }
    },
    
    sorters: [{
         property: 'code',
         direction: 'ASC'
     }],
     
    root:{},
    autoLoad:true
 });