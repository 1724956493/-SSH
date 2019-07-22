 Ext.define("core.employee.report.bills.quality.byjob.store.QcBanByjobStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.bills.quality.byjob.model.QcBanByjobModel',
	alias: 'widget.QcBanByjobStore',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adqualitybill_bydept',
        reader: {
            type: 'array'
       //     root:'data',
      //      totalProperty:'total'//总项数
        },
        actionMethods:{
        	read:'POST'
        },
        autoLoad:true
    }
 });