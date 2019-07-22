Ext.define("core.employee.employeeAdd.view.EmApplyGrid",{
	extend:"Ext.grid.Panel",
	alias:"widget.EmApplyGrid",
	store:"core.employee.employeeAdd.store.EmployeeStore",
	height:270,
	border:0,
	frame:true,
	defaults: {align : 'center',
		       field : {xtype:"textfield"}
              },
	tbar:[
		{
			xtype:'button',
		    text:'新增',
		    width: 60,
		    ref:"add",
		    formBind:true
		},{
			xtype:'button',
			text:'修改',
			width: 60,
			ref:'modify'
		},{
			xtype:'button',
			text:'删除',
			width: 60,
			ref:'delete'
		},{
			xtype:'button',
			text:'关闭',
			width: 60,
			ref:'close'
		},
		"->",
		'按名称查询:',
		{
			xtype: 'triggerfield', 
			triggerCls: Ext.baseCSSPrefix + 'form-search-trigger',
			listeners:{
            	"change":function(_this,_new,_old,_opt){ 
            		var _store = _this.ownerCt.ownerCt.getStore();
            		_store.clearFilter(false);
            		_store.filter("psnname",_new);
                }
            },
            onTriggerClick: function() {
            	var _store = this.ownerCt.ownerCt.getStore();
            	_store.clearFilter(false);
            	_store.filter("psnname",this.getValue());
		    }
		},
		'按工作区域查询:',
		{
			xtype: 'DeptCombo', 
			forceSelection:false,
			listeners:{
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
	        },
	        select : function(_this,records,eOpts ){
	        	var _store = _this.ownerCt.ownerCt.getStore();
	            _store.clearFilter(false);
	        	_store.filter("usedept",records[0].get('pkDeptdoc'));
	    //    	Ext.Msg.alert("提示",records[0].get('pkDeptdoc'));
	        }}
			/*         onTriggerClick: function() {
            	var _store = this.ownerCt.ownerCt.getStore();
            	_store.clearFilter(false);
            	if(this.getValue()=="提交"||this.getValue()==1){
            	_store.filter("status",1);}
		    }*/
		},
		'按状态查询:',
		{
			xtype: 'triggerfield', 
			triggerCls: Ext.baseCSSPrefix + 'form-search-trigger',
			listeners:{
            	"change":function(_this,_new,_old,_opt){ 
            		var _store = _this.ownerCt.ownerCt.getStore();
            		_store.clearFilter(false);
            		if(_new=="提交"||_new==1)
            		{
            		_store.filter("status",1);}
            		else{
                		_store.filter("status",_new)}
                }
            },
            onTriggerClick: function() {
            	var _store = this.ownerCt.ownerCt.getStore();
            	_store.clearFilter(false);
            	if(this.getValue()=="提交"||this.getValue()==1){
            	_store.filter("status",1);}else{
            		_store.filter("status",this.getValue());
            	}
		    }
		}
	],
	
  //  selModel: new Ext.selection.CheckboxModel({checkOnly:true}),
	enableKeyNav:true,  //可以使用键盘控制上下
	columnLines:true, //展示竖线
	columns:[
		{xtype: 'rownumberer'},
		{text:"姓名",dataIndex:"psnname",width:100},
		{text:"性别",dataIndex:"sex",width:50,renderer:function(val){if(val==1)return '男';else return '女';	}},
		{text:"身份证号",dataIndex:"id",width:130},
		{text:"工种",dataIndex:"job",width:110,
			renderer:function(val){
					var jobstore =  Ext.getStore("core.employee.employeeAdd.store.JobStore");
					var index = jobstore.find("pkDefdoc",val);
					if(index == -1){return null;}
						else {return jobstore.getAt(index).get('docname')  ;}				
					}			
		},
		{text:"工作区域",dataIndex:"usedept",width:130,
			renderer:function(val)
			{
				if(val!=""&&val!=null)
					{
						var jobstore =  Ext.getStore("core.employee.employeeAdd.store.DeptStore");
						var index = jobstore.find("pkDeptdoc",val);
						if(index == -1){return null;}
						else {return jobstore.getAt(index).get('deptname');}
					}	
				else
					{return null;}
			}
		},
		{text:"状态",dataIndex:"status",width:130,
			renderer:function(val){
				if(val==1) {return '提交';}
					else if(val==2)  {return "<span style='color:red;font-weight:bold;'>已审核</span>";}	
					else if(val==3)  {return "<span style='color:red;font-weight:bold;'>已派工</span>";}	
					else {return '草稿';}
         }}
	],
	
	bbar: [{xtype: 'pagingtoolbar',
			store: "core.employee.employeeAdd.store.EmployeeStore",
			displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
			emptyMsg: "没有数据",
            beforePageText: "当前页",
            afterPageText: "共{0}页",
            displayInfo: true   
            }],
    

	initComponent:function(){
	//	var jobstore =  Ext.getStore("core.employee.employeeAdd.store.JobStore");
	//	jobstore.load();
	//	var deptstore =  Ext.getStore("core.employee.employeeAdd.store.DeptStore");
	//	deptstore.load();
		//this.editing=Ext.create("Ext.grid.plugin.CellEditing");
		//this.plugins=[this.editing];
		this.callParent(arguments);
	}
});