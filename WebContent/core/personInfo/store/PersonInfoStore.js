/*
 * 商品数据集类
 */
 Ext.define("core.app.store.PersonInfoStore",{
 	extend:'Ext.data.Store',
	model:'core.app.model.PersonInfoModel',
	pageSize:10,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/user_usertoJson',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });