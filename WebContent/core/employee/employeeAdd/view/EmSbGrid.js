Ext.define("core.employee.employeeAdd.view.EmSbGrid",{
	extend:"core.employee.employeeAdd.view.EmApplyGrid",
	alias:"widget.EmSbGrid",
	title:"用户提交",
	tbar:[
		{
			xtype:'button',
		    text:'提交',
		    width: 60,
		    ref:"add",
		    formBind:true
		}
	],
	
    selModel: new Ext.selection.CheckboxModel({checkOnly:true}),
	

	initComponent:function(){
		//this.editing=Ext.create("Ext.grid.plugin.CellEditing");
		//this.plugins=[this.editing];
		this.callParent(arguments);
	}
});