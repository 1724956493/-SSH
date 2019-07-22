 Ext.define("core.employee.report.quality.byproject.store.QualityByProjectStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.quality.byproject.model.QualityByProjectModel',
	alias: 'widget.QualityByQcStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getProjectReport',
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