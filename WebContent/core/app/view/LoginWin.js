Ext.define('core.app.view.LoginWin', {
	extend : 'Ext.window.Window',
	title : "系统登陆",
	width : 300,
	height : 150,
	border:false,
	alias:"widget.LoginWin",
	modal : true,
	closable : false,
	closeAction:"destroy",
	autoShow:true,
	layout:'fit',
	
	items:[{
		xtype:'form',
		id:'loginform',
		frame:false,
		bodyPadding:15,
		defaults:{
			xtype:'textfield',
			anchor:'100%',
			labelWidth:60
		},
		
		items:[{name:'smuser.userCode',fieldLabel:"登陆账号"},
		       {inputType:'password',name:'password',fieldLabel:"密码",ref : "password",enableKeyEvents: true,
		           listeners: {
					    	 keyup: function(_this, e, eOpts) {
					    	 	if (e.getKey() == 13) {  
					    	 		_this.up("LoginWin").down("button").focus();					    	 	
					    	 	}},			    	 	
          		  buffer: 200
		       }}],
		       
		bbar:[{
			xtype:'tbfill'
        },
       {
        	xtype:'button',
        	ref:"load",
        	text:'登陆'
       },{
			xtype:'tbfill'
        },
       {
           	xtype:'button',
           	ref:"reset",
           	text:'重置'
       },{
			xtype:'tbfill'
       }]
	}]	
})