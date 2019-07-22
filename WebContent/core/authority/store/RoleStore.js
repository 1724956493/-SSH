/*
 * 商品数据集类
 */
 Ext.define("core.authority.store.RoleStore",{
 	extend:'Ext.data.Store',
	model:'core.authority.model.RoleModel',
	proxy: {
        type: 'ajax',
        api: {
			read: './json/user_roletoJson',//查询
			create: './json/user_Jsontorole',//创建
			update: 'url/update',//更新
			destroy: 'url/destroy'//删除
		},
        reader: {
            type: 'json'
        },
        writer: {
			type: 'json'
		}
    },
    autoLoad:true
 });