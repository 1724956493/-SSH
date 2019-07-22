Ext.define("core.employee.quality.bills.reward.view.RewardSearchForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.RewardSearchForm',
			layout :'form',
			bodyPadding : 10,
			defaults: { // defaults are applied to items, not the container
				margin  : '15 10 15 10',
			},
			
	
		bbar:[
			   { xtype: 'button', text: '查询',ref:'search',formBind : true},
			   { xtype: 'button', text: '重置',ref:'reset'}
		],
			
		items:[
				
				{xtype: 'datefield', fieldLabel: '发生日期大于:',name:'begindate',format: 'Y-m-d',maxValue: new Date()},
				{xtype: 'datefield', fieldLabel: '发生日期小于:',name:'enddate',format: 'Y-m-d',maxValue: new Date()},
				{xtype: 'textfield',name:'resourceid', fieldLabel: '单据类型:',value : 'QCJL',allowBlank :false,hidden:true},
				{xtype: 'textfield', fieldLabel: '单据号:',name:'billcode'},
				{xtype: 'DeptCombo', fieldLabel: '奖励部门:',name:'dept'},
				
			],

			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})