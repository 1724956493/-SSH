 Ext.define("core.employee.quality.bills.reward.store.QualityRewardTypeStore",{
 	extend:'Ext.data.Store',
 	alias: 'widget.QualityRewardTypeStore',
 	fields:['code','typename'],
 	data:[{"code":"JLA1","typename":"项目一次性通过奖励"},
 		  {"code":"JLA2","typename":"月度通过率奖励"}]
})