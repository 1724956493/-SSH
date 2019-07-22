Ext.define("core.employee.employeeAdd.view.JobCombo",{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.JobCombo',
	listConfig:{
		loadingText:'正在加载',
		emptyText:'未找到匹配值',
		maxHeight:60
	},
	
	allQuery:'allbook',
	minChars:2,
	queryDelay:300,
	queryParam:'docname',
	forceSelection:true,
	triggerAction:'query',
	store:'core.employee.employeeAdd.store.JobStore',
	displayField:'docname',
	valueField:'pkDefdoc',
	queryMode:'remote',
		
		
	listeners : {  
	        beforequery : function(e) {  
	            var combo = e.combo;     
	            if(!e.forceAll){     
	                var value = e.query;     
	                combo.store.filterBy(function(record,id){     
	                    var text = record.get(combo.displayField);     
	                    return (text.indexOf(value)!=-1);     
	                });  
	                combo.expand();     
	                return false;     
	            }  
	        }}

})