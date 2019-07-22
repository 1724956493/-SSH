/*
 * 商品数据集类
 */
 Ext.define("core.authority.store.UserStore",{
 	extend:'Ext.data.Store',
	model:'core.authority.model.UserModel',
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