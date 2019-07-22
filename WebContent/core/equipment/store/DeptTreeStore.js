 Ext.define("core.equipment.store.DeptTreeStore",{
 	extend:'Ext.data.TreeStore',
	//model:'core.authority.model.MenuModel',

	//autoSync:true,//与服务器同步
	proxy:{
		type:"ajax",
		url:"./json/corp_getTreeDeptdocs",
		reader:{
			type:"json"				
		}		
	},
	
	root:{},
	autoLoad:true	
 });