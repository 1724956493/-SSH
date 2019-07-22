Ext.define("core.authority.view.RoleFormView",{
	extend: 'Ext.form.Panel',
	alias: 'widget.RoleFormView',
	margins: '2 0 0 0',
	title:'角色新增',
	bodyPadding: 5,
    height:200,

	//id:'UserView',
	layout:'anchor',
	//modal: true,
		items: [{
		        xtype: 'textfield',
	            name : 'uuidRole',
	            fieldLabel: 'uuidRole',
	            labelWidth:60,
	            allowBlank: true,
	            hidden : true
        		},{
				xtype:'textfield',
				fieldLabel:'角色名称',
				name:'rolename',
				allowBlank: false
				},{
				xtype:'textfield',
				name:'createTime',
				fieldLabel:'创建时间',
				readOnly:true
				},{
				xtype:'textfield',
				name:'roledesc',
				fieldLabel:'角色备注'
				}],
	   tbar:[{
				xtype:'button',
				text:'保存',
				ref:"save2",
				formBind:true
				},{
				xtype:'button',
			    text:'重置',
			    ref:'reset'
				}
				],

	initComponent:function(){
		this.callParent(arguments);
	}
});