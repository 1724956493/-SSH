 Ext.define("core.employee.report.quality.byqc.store.QualityByQcStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.quality.byqc.model.QualityByQcModel',
	alias: 'widget.QualityByQcStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getQCReport',
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