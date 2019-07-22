Ext.define("core.employee.quality.xunjian.view.EmprpSearchForm",{
	extend: 'Ext.form.Panel',
	alias: 'widget.QualityCloseSearchForm',
	
    minWidth : 800 ,
	layout: 'column',
	defaults:{
	 	bodyPadding : 5 ,
	 	border : 0,
	 	labelAlign: 'right',
        labelWidth: 40
			 },
	 padding : '5 5 5 5' ,
//	 margin :'5  0  0 5',
	 items :[{columnWidth: 0.33,
					 items:[
					 {xtype: 'datefield',fieldLabel:'检查开始日期',name:'begindate',format: 'Y-m-d',value :Ext.util.Format.date(new Date())},
					 {xtype: 'datefield',fieldLabel:'检查结束日期',name:'enddate',format: 'Y-m-d' ,value :Ext.util.Format.date(new Date())},
					 {xtype: 'button',ref:'search2',text :'查询',width:80,height:20,style:'font-size:15px'}
					 ]},{columnWidth: 0.33,
					items:[
					 {xtype: 'ProjectCombo',name:'project',fieldLabel:'项目名称'},
					 {xtype: 'LeaderCombo',name:'empleader',fieldLabel:'主管领导'},
					 {xtype: 'button',ref:'clearsearch',text :'清除查询条件',width:80,height:20,style:'font-size:15px'}					 
			 ]},					
			// 	{columnWidth: 0.25,items:[ {xtype: 'textfield',name:'empdept',fieldLabel:'外包队名称'},
			//		 {xtype: 'textfield' ,name:'empuserdept',fieldLabel:'工作区域'},			 ]},
				{columnWidth: 0.33,
					items:[
				//	 {xtype: 'textfield',name:'operate' ,fieldLabel:'检查人'},
					 {xtype: 'hiddenfield',name:'type'},
					 {xtype: 'hiddenfield',name:'status'},
					 {xtype: 'radiogroup',fieldLabel:'缴费状态',
				 		 items:[
						      {boxLabel:'未缴费',name:'paystatus',inputValue:'0'},   
						      {boxLabel:'已缴费',name:'paystatus',inputValue:'1'}  
					  		]		 		 
			 		 },
			 		 {xtype: 'button',ref:'payclose',text :'缴费关闭',width:80,height:20,style:'font-size:15px'},
					 {xtype: 'button',ref:'export',text :'导出',width:80,height:20,style:'font-size:15px'}				  
			 ]}
			 ],
	
	
	initComponent : function() {
				this.callParent(arguments);				
			}
	
	})