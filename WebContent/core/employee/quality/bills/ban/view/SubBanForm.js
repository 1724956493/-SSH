Ext.define("core.employee.quality.bills.ban.view.SubBanForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.SubBanForm',
			defaults : {
				margin :'10 5 10 5'			
			},
			
	bbar:[{xtype: 'button', text: '保存',ref:'save',formBind : true},
		  {xtype: 'button', text: '重置',ref:'reset'},
		  {xtype: 'button', text: '关闭',ref:'close'}
	],		
			
	items:[
		{xtype: 'textfield',name:'uuid',hidden: true},
		{xtype: 'textfield',name:'huuid',hidden: true},
		{xtype: 'DeptCombo',fieldLabel: '部门:',allowBlank : false, name:'dept',ref:'deptcombo'},
		{xtype: 'EmployeeCombo',fieldLabel: '姓名:', name:'psnname',allowBlank : false,isdeptparam:true},
		{xtype: 'combo',fieldLabel: '责任类别:',allowBlank : false, forceSelection:true,store:['员工','班组长','外包主管','外包队负责','QA','设备员','安全员','主管','主管领导','区域负责人','部门长'],name:'joblevel'},
		{xtype: 'numberfield',fieldLabel: '处罚金额:',allowBlank : false, minValue: 0,name:'mulct'},
		{xtype: 'textfield',fieldLabel: '备注:',name:'note'}
	],
	
	initComponent : function() {
		this.callParent(arguments);
	}
})