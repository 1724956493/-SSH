Ext.define("core.authority.view.ResourceForm",{
	extend: 'Ext.form.Panel',
	alias: 'widget.ResourceForm',
	height: 50,

	bodyPadding: 5,
	defaults: {
		xtype: 'textfield',
    },
	//id:'UserView',
	layout:'anchor',
	//modal: true,
		items: [{		        
	            name : 'uuidmenu',
	            fieldLabel: 'uuidmenu',
	            labelWidth:60,
	            allowBlank: true,
	            hidden : true
        		},{
    		    name : 'parentId',
    	         fieldLabel: '父节点ID',
    	         allowBlank: true,
    	         readOnly: true 
    	  //     hidden : true
            	},{
				fieldLabel:'资源名称',
				name:'text',				
				allowBlank: false
				},{
					xtype:'textfield',
					fieldLabel:'资源编号',
					name:'menucode',
					allowBlank: false
				},{
				name:'aciton',
				fieldLabel:'资源内容'
				},{
				name:'type',
				fieldLabel:'资源类型',
				xtype: 'numberfield',
				value:2,
				}],
	   bbar:[{
				xtype:'button',
				text:'保存',
		//		disabled :true ,
				ref:"save"
		    //	formBind:true
				},{
					xtype:'button',
				    text:'修改',
				    ref:'modify'
				},{
				xtype:'button',
			    text:'关闭',
			    ref:'close'
				}],

	initComponent:function(){
		this.callParent(arguments);
	}
});