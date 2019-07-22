Ext.define("core.employee.quality.base.wzlist.view.WzTypeCombo",{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.WzTypeCombo',
	listConfig:{
		loadingText:'正在加载',
		emptyText:'未找到匹配值',
		maxHeight:120
	},
	
	
	forceSelection:true,
	triggerAction:'query',
	store:"core.employee.quality.base.wztype.store.WzTypeStore",
	displayField:'text',
	valueField:'id',
	queryMode:'remote'
})