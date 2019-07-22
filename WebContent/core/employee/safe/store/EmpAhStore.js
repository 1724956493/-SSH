 Ext.define("core.employee.safe.store.EmpAhStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.quality.xunjian.model.QualityModel',
	alias: 'widget.EmpAhStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getRP2AllInfo',
        reader: {
            type: 'json'
       //     root:'data',
       //     totalProperty:'total'//总项数
        },
        actionMethods:{
        	read:'POST'
        }
    }
//  ,  autoLoad:true
 });