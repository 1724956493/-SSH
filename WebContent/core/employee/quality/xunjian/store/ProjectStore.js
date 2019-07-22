 Ext.define("core.employee.quality.xunjian.store.ProjectStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.xunjian.model.ProjectModel',
	alias: 'widget.ProjectStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/job_getjobbasfils',
        reader: {
            type: 'array'
        }
    },
    autoLoad:true
 });