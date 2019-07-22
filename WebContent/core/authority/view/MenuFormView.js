/**
 * 程序布局放大中间的部分
 */
Ext.define("core.authority.view.MenuFormView",{
	extend: 'Ext.form.Panel',
	alias: 'widget.MenuFormView',
//	margins: '2 0 0 0',
	title:'菜单新增',
	bodyPadding: 5,
	defaults: {
		xtype: 'textfield',
        readOnly: true 
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
				fieldLabel:'菜单名称',
				name:'text',				
				allowBlank: false
				},{
				name:'funViewXtype',
				fieldLabel:'页面别名'
				},{
				name:'funController',
				fieldLabel:'页面主控'
				},{
				name:'funViewName',
				fieldLabel:'页面全称'
				}],
	   tbar:[{
				xtype:'button',
				text:'保存',
		//		disabled :true ,
				ref:"save"
		    //	formBind:true
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