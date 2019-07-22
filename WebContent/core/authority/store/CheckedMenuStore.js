 Ext.define("core.authority.store.CheckedMenuStore",{
 	extend:'Ext.data.TreeStore',
	//model:'core.authority.model.MenuModel',
 	fields: [ 
 		'id','text','code','leaf','href','hrefTarget','cls','expandable','expanded',
 		'description','checked','action','parent','nodeType','nodeInfo','nodeInfoType'		
 		],
	//autoSync:true,//与服务器同步
	proxy:{
		type:"ajax",
		url:"./json/user_treemenucheckedtojson",
		reader:{
			type:"json"				
		}	
	},
	
	root:{},	
 });