 Ext.define("core.employee.employeeAdd.store.JobStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.employeeAdd.model.JobModel',
	storeId:'jobStore2',
	alias: 'widget.JobStore',
	pageSize:10,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/corp_getjobs',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });