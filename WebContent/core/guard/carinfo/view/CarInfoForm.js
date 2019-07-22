Ext.define("core.guard.carinfo.view.CarInfoForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.CarInfoForm',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
			id:'CarInfoForm',
		//	height:400,
			width:250,
			
			defaults:{
				labelAlign: 'left',
				labelWidth: 60,
				msgTarget:'side',
			//	width:150,
				labelSeparator:':',
				readOnly : true
			},
			
		tbar:{
			hidden:true,
			items:[
			{
				xtype:'button',
			    text:'保存',
			    ref:"save",
			    formBind:true
			},{
				xtype:'button',
			    text:'重置',
			    ref:"reset"
			}	]
		},
						
		items:[{ xtype:'textfield',name:'uuid',readOnly:true,hidden:true},
			   { fieldLabel:'车牌号',xtype:'textfield',name:'carid',allowBlank:false},
			   { fieldLabel:'车型',xtype:'textfield',name:'cartype'},
			   { fieldLabel:'特征',xtype:'textfield',name:'carcolor'},
			   { fieldLabel:'车主',xtype:'textfield',name:'carowner',allowBlank:false},
			   { fieldLabel:'部门',xtype:'textfield',name:'ownerdept'},
			   { fieldLabel:'岗位',xtype:'textfield',name:'ownerjob'},
			   { fieldLabel:'联系方式',xtype:'textfield',name:'ownertelephone',allowBlank:false},
			   { fieldLabel:'登记时间',xtype:'textfield',name:'createTime',disabled:true},
			   { fieldLabel:'备注',xtype:'textarea',name:'note',maxLength :100}
			],	
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})