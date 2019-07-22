 Ext.define("core.employee.report.bills.quality.reward.store.RewardReportStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.bills.quality.reward.model.RewardReportModel',
	alias: 'widget.RewardReportStore',
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