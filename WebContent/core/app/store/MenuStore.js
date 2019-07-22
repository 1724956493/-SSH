/*
 * ClassName 用户数据集
 */
 Ext.define("core.app.store.MenuStore",{
 	extend:'Ext.data.TreeStore',
//	model:'core.menu.model.MenuModel',

	//autoSync:true,//与服务器同步
	proxy:{
		type:"ajax",
		url:"./json/user_treemenutoJson",
		reader:{
			type:"json"				
		}		
	},
	
	root:{
	}
//	autoLoad:true	
 });