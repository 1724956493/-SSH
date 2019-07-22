Ext.define("core.employee.quality.base.psntype.view.PsntypeForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.PsntypeForm',
			id:"PsntypeForm",
			//title:'人员新增',
	//		bodyPadding: 0,
	    	layout: 'column',
			width:250,
			
			defaults:{
				labelAlign: 'left',
				labelWidth: 60,
				msgTarget:'side',
				labelSeparator:':',
				bodyPadding : 5 ,
	 	        border : 0
	 	  //      disabled: true
			},
			
			
			tbar:[{
				xtype:'button',
			    text:'新增',
			    ref:"add"
			},
			{
				xtype:'button',
			    text:'修改',
			    ref:"modify"
			},{
				xtype:'button',
			    text:'保存',
			    ref:"save",
		//	    hidden  : true,
			    formBind:true
			},{
			//	xtype:'button',text:'删除',ref:'delete'},{
				xtype:'button',
				text:'停用',
				ref:'close'
			}],
			
		items:[	{columnWidth: 0.33,
				   items:[
				   { xtype:'hidden',name:'uuid',readOnly:true},
				   { xtype:'hidden',name:'psndocpk',allowBlank :false},
				   { xtype:'hidden',name:'psnbasdocpk',allowBlank :false},
				   { fieldLabel:'姓名',xtype:'textfield',name:'psnname',readOnly:true},
				   { fieldLabel:'部门',xtype:'DeptCombo',name:'dept',allowBlank :false},
				   { fieldLabel:'所属部门',xtype:'DeptCombo',name:'parentdept',allowBlank :false}
			   ]},
			   {columnWidth: 0.33,
			   		items:[			   
				   { fieldLabel:'所属区域',xtype:'textfield',name:'area',allowBlank :false},
				   { fieldLabel:'负责项目',xtype:'ProjectCombo',name:'project'},	
				   { fieldLabel:'人员类型',xtype:'combo',name:'psntype',store:['主管领导','QC','QA'],allowBlank :false}
			   ]},
			   {columnWidth: 0.33,
					items:[
					{ fieldLabel:'建立时间',xtype:'textfield',name:'create_time',disabled:true}	,
					{ fieldLabel:'备注',xtype:'textarea',name:'note',maxLength :100}
			   ]}
			   		   
			],	
			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})