 Ext.define("core.employee.quality.bills.ban.store.AHForbidtypeStore",{
 	extend:'Ext.data.Store',
 	alias: 'widget.AHForbidtypeStore',
 	fields:['code','typename'],
 	data:[{"code":"1","typename":"作业中途离开舱室时或班后未将氧、乙炔、天燃气管随人员一同拉出作业舱室。"},
 	{"code":"2","typename":"起重作业未正确使用吊索具。"},
 	{"code":"3","typename":"私自拆除脚手架。"},
	{"code":"4","typename":"高处作业未使用保险带。"},
	{"code":"5","typename":"特殊作业未征得申报同意和确认合格后开始施工。"},
	{"code":"6","typename":"脚手架或高处舷边浮动物未固定或未及时清除。"},
	{"code":"7","typename":"高处割物未采取安全措施或违章高处抛物。"},
	{"code":"8","typename":"除了装配工和火工外，其他人员一律不允许带打火机、火柴上船，所有的人一律不准带香烟上船。"},
	{"code":"9","typename":"行车行走警戒线内人和物品均不能停留。"},
	{"code":"10","typename":"电磁吊起吊钢板后在其下方影响区域严禁站人。"}]
})