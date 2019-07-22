 Ext.define("core.employee.quality.base.psntype.store.EmpSearchStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.base.psntype.model.EmpSearchModel',
	alias: 'widget.WzTypeStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/bdpsndoc_searchEmp',
        reader: {
            type: 'array'
        },
        actionMethods:{
        	read:'POST'
        },
         timeout:180000
    },
    
    sorters: [{
         property: 'id',
         direction: 'ASC'
     }]
 });