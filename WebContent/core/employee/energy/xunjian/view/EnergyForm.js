Ext.define("core.employee.Energy.xunjian.view.EmprpForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.EnergyForm',
			margins: '0 0 0 0',
			//title:'人员新增',
			bodyPadding: 5,
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
			
			
			bbar:[
			{
				xtype:'button',
			    text:'修改',
			    ref:"modify",
			    id:"Energy_EmprpForm_modify"
			},{
				xtype:'button',
			    text:'保存',
			    ref:"save",
			    id:"Energy_EmprpForm_save",
		//	    hidden  : true,
			    formBind:true
			},{
			//	xtype:'button',text:'删除',ref:'delete'},{
				xtype:'button',
				text:'关闭',
				ref:'close'
			}],
			
		items:[{ xtype:'hidden',name:'uuidRp',readOnly:true},
			   { fieldLabel:'姓名',xtype:'textfield',name:'psnname'},
			   { fieldLabel:'项目号',xtype:'ProjectCombo',name:'project',allowBlank :false},
			   { fieldLabel:'主管领导',xtype:'LeaderCombo',name:'empleader',allowBlank :false},
			   { fieldLabel:'是否违规',xtype:'combo',name:'status',store:['合格','违规'],allowBlank :false},
			   { fieldLabel:'检查时间',xtype:'textfield',name:'create_time'},
			   { fieldLabel:'检查人',xtype:'textfield',name:'operate'},
			   { fieldLabel:'备注',xtype:'textarea',name:'cknote',maxLength :100}
			],	
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
			})