 Ext.define("core.employee.report.quality.byempleader.store.QualityByEmpleaderStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.quality.byempleader.model.QualityByEmpleaderModel',
	alias: 'widget.QualityByEmpleaderStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getEmpleaderReport',
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