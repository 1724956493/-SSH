Ext.define("core.employee.employeeAdd.view.IdAddWin",{
	extend: 'Ext.window.Window',
	alias: 'widget.IdAddWin',
	title:'身份校验',
	layout:'fit',
	//closeAction:'hide',
	height:180,
	width:400,
	resizable:false,
	shadow:true,
	modal:true,
	closable:false,
	items:[{
		xtype: 'form',
		id:'IdAddForm',
		margins: '2 2 2 2',		
		bodyPadding: '20',
		layout: 'anchor',
	    defaults: {
	        anchor: '100%'
	    },
	    items: [{
	    	xtype: 'textfield',
	        fieldLabel: '姓名',
	        name: 'employee.Psnname',
	        allowBlank: false
	    },{
	    	xtype: 'textfield',
	    	id:'IdAddForm_id',
	        fieldLabel: '身份证号',
	        name: 'employee.Id',
	        allowBlank: false
	    }],

		bbar:[
		      {xtype:'button',
			   text:'保存',
			   ref:"check",
			   formBind:true
			  },
			  {
				xtype:'button',
				text:'返回',
				ref:'return'
			   }]
	}]
})