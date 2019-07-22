Ext.define("core.employee.quality.xunjian.view.QCQACombo",{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.QCQACombo',
	listConfig:{
		loadingText:'正在加载',
		emptyText:'未找到匹配值',
		maxHeight:120
	},
	
	
	//allQuery:'alljob',
	minChars:2,
	//queryDelay:300,
	forceSelection:true,
//	queryParam:'jobname',
	triggerAction:'query',
	store:'core.employee.quality.xunjian.store.QCQAStore',
	displayField:'psnname',
	valueField:'psndocpk',
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