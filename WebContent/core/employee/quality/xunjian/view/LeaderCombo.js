Ext.define("core.employee.quality.xunjian.view.LeaderCombo",{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.LeaderCombo',
	listConfig:{
		loadingText:'正在加载',
		emptyText:'未找到匹配值',
		maxHeight:120
	},
	
	
	//allQuery:'',
	minChars:2,
	//queryDelay:300,
	forceSelection:true,
//	queryParam:'name',
	triggerAction:'query',
	store:'core.employee.quality.xunjian.store.LeaderStore',
	displayField:'psnname',
	valueField:'uuid',
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