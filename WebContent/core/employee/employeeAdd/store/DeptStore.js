 Ext.define("core.employee.employeeAdd.store.DeptStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.employeeAdd.model.DeptModel',
	alias: 'widget.DeptStore',
	pageSize:10,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/corp_getdeptdocs',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });