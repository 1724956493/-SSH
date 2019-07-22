Ext.define("core.employee.quality.bills.ban.view.SBBanForm", {
			extend: 'Ext.form.Panel',
			alias: 'widget.SBBanForm',
			autoScroll:true,
			layout :'column',
	    //    layout:'vbox',
	
		tbar:[{xtype: 'button', text: '设备处罚单保存',ref:'save',formBind : true},
			  {xtype: 'button', text: '重置',ref:'reset'},
			  {xtype: 'button', text: '关闭',ref:'close'}
		],
			
		items:[				
						{xtype: 'textfield',name:'uuid', hidden: true},
						{xtype: 'textfield',name:'type', hidden: true},
						{xtype: 'textfield',name:'ts', hidden: true,value: '2017-05-06 13:24:01'},
						{xtype: 'numberfield',name:'totalmulct',value:0,hidden: true},
						{xtype: 'numberfield',name:'totalreward',value:0,hidden: true},
						{xtype: 'textfield', fieldLabel: '单据号:',margin: '5 40 0 20',name:'billcode',allowBlank :false,columnWidth: 0.5},						
						{xtype: 'combo', fieldLabel: '违章类别:',margin: '5 40 0 20',name:'billhead',forceSelection:true,store:['设备违章操作','设备高压线','设备保养不达标','能源浪费'],allowBlank :false,columnWidth: 0.5},
					//	{xtype: 'ProjectCombo', fieldLabel: '项目号:',margin: '5 40 0 20',name:'project',allowBlank :false,columnWidth: 0.5},
						{xtype: 'combo', fieldLabel: '检查区域:',margin: '5 40 0 20',name:'projectobj',forceSelection:true,store:"core.employee.quality.bills.ban.store.SBProjectStore",queryMode: 'local',valueField: 'typename',displayField: 'typename', editable : false,allowBlank :false,columnWidth: 0.5},
						{xtype: 'DeptCombo', fieldLabel: '主责部门:',margin: '5 40 0 20',name:'dept',allowBlank :false,columnWidth: 0.5},
						{xtype: 'DeptCombo', fieldLabel: '外协单位:',margin: '5 40 0 20',name:'wbdept',columnWidth: 0.5},
						{xtype: 'datefield', fieldLabel: '发生日期:',margin: '5 40 0 20',name:'createdate',allowBlank :false,columnWidth: 0.5,format: 'Y-m-d',maxValue: new Date()},						
					    {xtype: 'textfield',fieldLabel: '整改意见书编号:',margin: '5 40 0 20',name:'panding',allowBlank :false,columnWidth: 0.5},
						{xtype: 'htmleditor',fieldLabel: '问题描述:',margin: '5 40 0 20',name:'miaoshu',columnWidth: 1},
					    {xtype: 'UploadFieldDef',fieldLabel: '图片一:',name: 'image1',margin:'5 40 0 20',contexType:'image',filepath :'/upload/image',columnWidth: 0.5},	
					 	{xtype: 'UploadFieldDef',fieldLabel: '图片二:',name: 'image2',margin: '5 40 0 20',contexType:'image',filepath :'/upload/image',columnWidth: 0.5},						
						{xtype: 'multicombobox',fieldLabel: '责任界定:',columnWidth: 1,margin: '5 40 0 20',name:'yiju',forceSelection:true,multiSelect:true,
					 		valueField: 'name',displayField: 'name',   
					 		store:Ext.create('Ext.data.Store', {
					 			fields: ['name'],  
								data : [ 
					 				{name:'整顿不合格'},{name:'整理不合格'},{name:'清洁不合格'},{name:'清扫不合格'}
					 		    ]	
					 		}),queryMode: 'local',editable : false,allowBlank :false},
					 	{xtype: 'htmleditor',fieldLabel:'处罚建议',name:'fenxi',margin: '5 40 0 20',columnWidth: 1,enableFont :false},
						{xtype: 'htmleditor',fieldLabel:'整改要求',name:'fangfa',margin: '5 40 10 20',columnWidth: 1,enableSourceEdit :false},
				],

			
		initComponent : function() {
				this.callParent(arguments);
			}
			
})