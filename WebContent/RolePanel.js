Ext.onReady(function () { 
	Ext.define('RoleModel', {
			extend : 'Ext.data.Model',
			fields : ['uuidRole', 'rolename', 'createTime']
		});
	
	Ext.create('Ext.data.Store', {
	    storeId:'simpsonsStore',
	//	   	fields : ['uuidRole', 'rolename', 'createTime'],
	     model: 'RoleModel',
	    proxy: {
	        type: 'ajax',
	      //  url:'',
	        api: {
				read: './json/user_roletoJson',//查询
				create: './json/user_Jsontorole',//创建
				update: 'url/update',//更新
				destroy: 'url/destroy'//删除
			},
	        reader: {
	            type: 'json',
	            root: 'root'
	        },
	        writer: {
				type: 'json'
			}
	    },
	    autoLoad:true
	});
	
    Ext.create('Ext.grid.Panel', {  
        title: '角色列表',  
        width: 200,
        selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
        forceFit:true,
        store: Ext.data.StoreManager.lookup('simpsonsStore'),  
        columns: [  
            { header: 'uuidRole', dataIndex: 'uuidRole',hidden:true},  
            { header: 'rolename', dataIndex: 'rolename'},  
         //   { header: 'createTime', dataIndex: 'createTime' }  ,
        ],  
        	plugins:[
            	Ext.create("Ext.grid.plugin.CellEditing")
            ],
            	tbar: [
				{ xtype: 'button', text: '增加'},
				{ xtype: 'button', id:'delete',text: '删除' },
			    { xtype: 'button', text: '保存' },
			    { xtype: 'button', text: '查看' }
		  ],
        renderTo: 'windowDiv'  
    }); 
    
    Ext.create('Ext.form.Panel',{
						width: 300,
    					height: 150,
						renderTo: document.body,
						 layout:'anchor',
				//		modal: true,
    					autoShow:true,
    					title: '用户注册',

	/*			        defaults: {
				            anchor: '100%'
				        },
				        fieldDefaults: {
				            labelWidth: 110,
				            labelAlign: 'left',
				            msgTarget: 'none',
				            invalidCls: '' //unset the invalidCls so individual fields do not get styled as invalid
				        },*/
    					
    					items: [{
    						xtype:'textfield',
    						fieldLabel:'角色名称'    						
		    					},{
		    				xtype:'textfield',
		    			    fieldLabel:'角色备注'}]
					,buttons:[{
		    				xtype:'button',
		    			    text:'确认'},{
		    				xtype:'button',
		    			    text:'取消'}]
		    			    
		    	});	
		    	
Ext.define('UserModel', {
			extend : 'Ext.data.Model',
			fields : ['cuserid', 'userCode', 'userName']
		});
	
	Ext.create('Ext.data.Store', {
	    storeId:'UserStore',
	//	   	fields : ['uuidRole', 'rolename', 'createTime'],
	     model: 'UserModel',
	    proxy: {
	        type: 'ajax',
	        url:'./json/user_usertoJson',
	        reader: {
	            type: 'json',
	           root: 'root'
	        }
	    },
	    autoLoad:true
	});
	
    Ext.create('Ext.grid.Panel', {  
        title: '用户列表',  
        width: 200,
        height:200,
        selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SINGLE"}),
        forceFit:true,
        store: Ext.data.StoreManager.lookup('UserStore'),  
        columns: [  
            { header: 'cuserid', dataIndex: 'cuserid',hidden:true},  
            { header: '用户名', dataIndex: 'userName'},  
            { header: '账号', dataIndex: 'userCode' } 
        ],  
        	plugins:[
            	Ext.create("Ext.grid.plugin.CellEditing")
            ],
            	tbar: [
				{ xtype: 'button', text: '增加',handler:function(){
					Ext.create('Ext.panel.Panel',{
						width: 300,
    					height: 400,
    				//	renderTo: Ext.getBody(),
    					autoShow:true,
    					items: [{
    						xtype:'textfield',
    						fieldLabel:'角色名称'    						
		    					},{
		    				xtype:'textfield',
		    			    fieldLabel:'角色备注'}]
					})}	
					},
				{ xtype: 'button', id:'delete2',text: '删除' },
			    { xtype: 'button', text: '保存' },
			    { xtype: 'button', text: '查看' }
		  ],
        renderTo: 'windowDiv'  
    });
});
