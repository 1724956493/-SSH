Ext.define("core.employee.employeeAdd.view.DeptCombo",{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.DeptCombo',
	listConfig:{
		loadingText:'正在加载',
		emptyText:'未找到匹配值',
		maxHeight:120
	},
	
	
	allQuery:'allbook',
	minChars:2,
	queryDelay:300,
	forceSelection:true,
	queryParam:'deptname',
	triggerAction:'query',
	store:'core.employee.employeeAdd.store.DeptStore',
	displayField:'deptname',
	valueField:'pkDeptdoc',
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