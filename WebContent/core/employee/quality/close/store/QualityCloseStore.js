 Ext.define("core.employee.quality.close.store.QualityCloseStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.close.model.QualityCloseModel',
	alias: 'widget.QualityCloseStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getRP2AllInfo',
        reader: {
            type: 'json'
       //     root:'data',
      //      totalProperty:'total'//总项数
        },
        actionMethods:{
        	read:'POST'
        },
    timeout:180000
    }   
 //   groupField:'operate'
//  ,  autoLoad:true
 });