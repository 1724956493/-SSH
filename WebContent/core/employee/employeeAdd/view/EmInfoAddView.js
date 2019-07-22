Ext.define("core.employee.employeeAdd.view.EmInfoAddView",{
	extend: 'Ext.form.Panel',
	alias: 'widget.EmInfoAddView',
	margins: '2 0 0 0',
	//title:'人员新增',
	bodyPadding: 5,
	
	defaults:{
		labelAlign: 'left',
		labelWidth: 40,
		msgTarget:'side',
	//	width:150,
		labelSeparator:':',
		allowBlank:false
	},
	
	tbar:[
			{
				xtype:'button',
			    text:'保存',
			    id:'EmInfoAddView_save',
			    ref:"save",
			    formBind:true
			},{
				xtype:'button',
				text:'重置',
				ref:'reset'
			},{
				xtype:'button',
				text:'关闭',
				ref:'close'
			}
		],
	 
	 items:[{
		  title:'个人信息',
		  xtype:'fieldset',
		  bodyPadding:5,
		 // collapsible:true,
		  layout: 'column',
		  items:[
		         {xtype:'panel',
		          bodyPadding:5,
		          border:0,
		          columnWidth: 0.33,
		          
		          items:[{
					  xtype:'hiddenfield',
					  name:'employeeuuid'
				  		},{
					  fieldLabel:'姓名',
					  xtype:'textfield',
					  name:'psnname',
					  readOnly:true
				  		},{
				  	  fieldLabel:'身份证号码',
					  xtype:'textfield',
					  regex : /^(\d{18,18}|\d{15,15}|\d{17,17}x)$/,
					  regexText : '输入正确身份号码',
					  name:'id',
					  readOnly:true
				  		},{
				  	  fieldLabel:'省份',
					  xtype:'textfield',
					  name:'province',
					  readOnly:true
						}]},
				  {xtype:'panel',
				   bodyPadding:5,
				   border:0,
				   columnWidth: 0.33,
			          items:[{
					  fieldLabel:'性别',
					  xtype:'radiogroup',
				//	  layout: 'column',
					  name:'sex',
				   // columns:2,
					  items:[
					      {boxLabel:'男',name:'sex',inputValue:'1',checked:true},   
					      {boxLabel:'女',name:'sex',inputValue:'2'}  
				  		]},{
				  	  fieldLabel:'出生日期',
					  xtype:'datefield',
					  name:'birthdate',
					  format: 'Y-m-d',
					  readOnly:true
				  		},{
			  		  fieldLabel:'城市名称',
					  xtype:'textfield',
					  name:'city'
							}]},
					{xtype:'panel',
					 bodyPadding:5,
					 border:0,
					 columnWidth: 0.33,
				          items:[{
				        	  fieldLabel:'健康状态',
							  xtype:'radiogroup',
						//	  layout: 'column',
							  name:'health',
							  columns:2,
							  items:[
							      {boxLabel:'健康',name:'health',inputValue:'1',checked:true},   
							      {boxLabel:'一般',name:'health',inputValue:'2'}  
						  		]},{
					  	  fieldLabel:'手机号码',
						  xtype:'textfield',
						  name:'mobile',
						  regex: /^\d+$/,
						  regexText: "电话号码只能为数字",
						  maxLength: '15'						  
					  		},{							  
						  fieldLabel:'家庭住址',						   
						  xtype:'textfield',
						  name:'addr'
							}]}
		         ]
	 },{title:'工作信息',
		  xtype:'fieldset',
		  bodyPadding:5,
		  height: 50,
		  collapsible:true,
		  layout: 'column',
		  defaults: { // defaults 将会应用所有的子组件上,而不是父容器
			  margin: '5 10 10 5',
			  labelAlign: 'center',
			  labelWidth: 60
			},
		  items:[{fieldLabel:'工种',xtype:'JobCombo',name:'job',columnWidth: 0.22,bodyPadding:5},
		         {fieldLabel:'部门',xtype:'DeptCombo',name:'dept',columnWidth: 0.22,bodyPadding:5},
		         {fieldLabel:'工作区域',xtype:'DeptCombo',name:'usedept',columnWidth: 0.22,bodyPadding:5},	
		         {fieldLabel:'班组信息',xtype:'textfield',name:'teamname',columnWidth: 0.22,bodyPadding:5}
			   ]
	 },{title:'保险信息',
	  xtype:'fieldset',
	  bodyPadding:5,
	  collapsible:true,
	  layout: 'column',
	  defaults: { // defaults 将会应用所有的子组件上,而不是父容器
		  margin: '5 10 10 5',
		  labelAlign: 'center',
		  labelWidth: 60
		},
		items:[{fieldLabel:'保险公司',xtype:'textfield',name:'inscompany',columnWidth: 0.22,bodyPadding:5},
		       {fieldLabel:'保险单号',xtype:'textfield',name:'inscode',columnWidth: 0.22,bodyPadding:5},
		       {fieldLabel:'生效日期',xtype:'datefield',name:'insbegindate',format: 'Y-m-d',value :Ext.util.Format.date(new Date(), 'Y-m-d'),columnWidth: 0.22,bodyPadding:5},	
		       {fieldLabel:'结束日期',xtype:'datefield',name:'insenddate',format: 'Y-m-d',columnWidth: 0.22,bodyPadding:5},
		       {fieldLabel:'工伤保险',xtype:'textfield',name:'injuryinscode',columnWidth: 0.22,bodyPadding:5}
			   ]
	 },{title:'银行卡信息',
		  xtype:'fieldset',
		  bodyPadding:5,
		  collapsible:true,
		  layout: 'column',
		  defaults: { // defaults 将会应用所有的子组件上,而不是父容器
			  margin: '5 25 10 5',
			  labelAlign: 'center',
			  labelWidth: 60
			},
		  items:[{fieldLabel:'银行名称',xtype:'textfield',name:'bankname',columnWidth: 0.22,bodyPadding:5},
			     {fieldLabel:'银行卡号',xtype:'textfield',name:'bankcode',columnWidth: 0.22,bodyPadding:5}
			    ]
	 }],
	 
	initComponent : function() {
				this.callParent(arguments);				
			}
})