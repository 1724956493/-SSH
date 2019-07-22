 Ext.define("core.employee.report.quality.bydept.store.QualityByDeptStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.quality.bydept.model.QualityByDeptModel',
	alias: 'widget.QualityByDeptStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getDeptReport',
        reader: {
            type: 'array'
       //     root:'data',
      //      totalProperty:'total'//总项数
        },
        actionMethods:{
        	read:'POST'
        }
    }
 });