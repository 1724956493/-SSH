 Ext.define("core.employee.employeeAdd.store.EmployeeStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.employeeAdd.model.EmployeeModel',
	pageSize:10,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/employee_toJsonall',
        reader: {
            type: 'json'
        }
    },
    sorters: [{
        //排序字段。
        property: 'psnname',
        //排序类型，默认为 ASC 
        direction: 'desc'
    }],
    autoLoad:true
 });