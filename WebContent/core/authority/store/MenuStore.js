 Ext.define("core.authority.store.MenuStore",{
 	extend:'Ext.data.TreeStore',
	//model:'core.authority.model.MenuModel',

	//autoSync:true,//与服务器同步
	proxy:{
		type:"ajax",
		url:"./json/user_treemenutoJson",
		reader:{
			type:"json"				
		}
		
	},
	
	root:{},
	autoLoad:true	
 });