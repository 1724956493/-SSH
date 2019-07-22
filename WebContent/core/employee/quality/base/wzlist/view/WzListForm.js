Ext.define("core.employee.quality.base.wzlist.view.WzListForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.WzListForm',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			width: 350,
			
			defaults:{
				labelAlign: 'left',
				labelWidth: 100,
				msgTarget:'side',
				anchor: '40%',
				labelSeparator:':',
				disabled : true
			},			
		
	   tbar:[
   				{ xtype: 'button', text: '新增', ref:"add"},
				{ xtype: 'button', text: '修改', ref:"modify"},				
				{ xtype: 'button', text: '保存', ref:"save", formBind: true},
				{ xtype: 'button', text: '删除', ref:"delete"}
			//,	{ xtype: 'button', text: '重置', ref:'reset'}
			],


			
		items:[{ xtype:'hidden',name:'uuid'},
			   { fieldLabel:'违章明细编号',xtype:'textfield',name:'wzlistcode',allowBlank :false},
			   { fieldLabel:'违章明细名称',xtype:'textarea',name:'wzlistname',allowBlank :false},
			   { xtype:'hidden',name:'wztype',allowBlank :false},
			   { fieldLabel:'违章程度',xtype:'combo',name:'wzlisttype',store:['一般','高压线'],allowBlank :false,value : '一般',editable : false,forceSelection : true},
			   { fieldLabel:'违章扣分',xtype:'numberfield',name:'wzlistscore',width : 40 ,maxValue: 10,minValue: 0,allowBlank :false},
		//	   { fieldLabel:'建立时间',xtype:'textfield',name:'createTime'},
			   { fieldLabel:'备注',xtype:'textarea',name:'note',maxLength :100}
			],	
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})