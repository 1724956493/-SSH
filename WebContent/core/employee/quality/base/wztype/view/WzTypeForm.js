Ext.define("core.employee.quality.base.wztype.view.WzTypeForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.WzTypeForm',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
	        layout: 'form',
			width: 350,
			
			defaults:{
				labelAlign: 'left',
				labelWidth: 100,
				msgTarget:'side',
				anchor: '40%',
				labelSeparator:':'
			//	readOnly : true
			},			
			
	   tbar:[{
				xtype:'button',
				text:'保存',
				ref:"save"
				},{
				xtype:'button',
			    text:'重置',
			    ref:'reset'
				}
				],
			
		items:[{ xtype:'hidden',name:'uuid'},
			   { fieldLabel:'违章类别编号',xtype:'textfield',name:'wzcode',allowBlank :false},
			   { fieldLabel:'违章类别名称',xtype:'textfield',name:'wzname',allowBlank :false},
			   { fieldLabel:'违章父类别',xtype:'textfield',name:'wzparent'},
			// { fieldLabel:'建立时间',xtype:'textfield',name:'createTime'},
			   { fieldLabel:'备注',xtype:'textarea',name:'note',maxLength :100}
			],	
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})