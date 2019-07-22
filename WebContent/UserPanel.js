Ext.onReady(function () { 
	Ext.define('UserModel', {
			extend : 'Ext.data.Model',
			fields : ['cuserid', 'userCode', 'userName']
		});
	
	Ext.create('Ext.data.Store', {
	    storeId:'UserStore',
	//	   	fields : ['uuidRole', 'rolename', 'createTime'],
	     model: 'UserModel',
/*	    data:{"root":[{"uuidRole":"bc81e16f-b1f8-4e18-8bcf-ac8a93f9e08a","rolename":"部门设备员","createTime":"2015-10-17 09:49:38"},
	    			  {"uuidRole":"f62b2e74-6da1-45e2-a97b-0bd5e293aa7f","rolename":"设备部复核员","createTime":"2015-10-17 09:49:38"},
	    			  {"uuidRole":"448d41aa-9f76-4529-91c0-2c58f125bfff","rolename":"船用设备巡检","createTime":"2015-11-18 08:24:21"},
	    			  {"uuidRole":"76ac32d3-401d-4501-90b9-f298214b82b8","rolename":"人员管理","createTime":"2015-11-18 08:24:21"},
	    			  {"uuidRole":"89e5f8ce-299d-47e6-8e21-ac8099b92dad","rolename":"系统管理员","createTime":"2015-11-17 16:42:18"}
	    			 ]},*/
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
        title: 'Simpsons',  
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
				{ xtype: 'button', id:'delete',text: '删除' },
			    { xtype: 'button', text: '保存' },
			    { xtype: 'button', text: '查看' }
		  ],
        renderTo: 'windowDiv'  
    });  
});
