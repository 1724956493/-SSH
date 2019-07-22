/**主控制器*/
Ext.define("core.authority.controller.TestController",{
	extend : "Ext.app.Controller",
/*	mixins : {
		formUtils : "core.util.FormUtils"
	},*/
	init : function(){
		var self = this;
		
       var treeStoreSelectedSon = function(node,checked) 
       {              
    	   if(null!=node){
             node.eachChild(function(child) {         //循环下一级的所有子节点 
                    if (null != child.get('checked')) //这里这么写是因为后台有些节点的checked没赋值，其在web上不显示复选框，这里就过滤掉对它们
                    {
                         child.set('checked', checked);       //选中 
                         treeStoreSelectedSon(child,checked);          //递归选中子节点
                    }                                       
             });  
      }};   
      
      var treeStoregetchecked = function(node,str) 
      {              
   	   if(null!=node){
            node.eachChild(function(child) {         //循环下一级的所有子节点 
                   if (child.get('checked')) //这里这么写是因为后台有些节点的checked没赋值，其在web上不显示复选框，这里就过滤掉对它们
                   {
                	   str.push(child.get('id'));       //选中 
                	   treeStoregetchecked(child,str);          //递归选中子节点
                   }                                       
            });  
     }}; 
      
       var treeStoreSelectedFather = function(node) 
           {
    	   	if(null!=node){
               var parent = node.parentNode;   //获取父节点 
               
               if(null!=parent){
	               if (node.get('checked')) {                 //是否有父节点                        	   
	            	   parent.set('checked',true);        
	               }
               } 
               
              treeStoreSelectedFather (parent);                              
        }} ;	
		
		this.control({
			"RoleView button[ref=add]" : {
				click : function(_btn) {
					var form = _btn.up("RoleViewLayout").down("RoleFormView");
					form.getForm().reset();
			}},
			
			
			"RoleView" : {
				itemclick : function(_grid, record, item, index, e, eOpts) {
					var form = _grid.up("RoleViewLayout").down("RoleFormView");
					var grid= form.up("RoleViewLayout").down("RoleView");
					var record = grid.getSelectionModel().getSelection();
					form.loadRecord(record[0]);		
					
					var menupanel = _grid.up("RoleViewLayout").down("CheckedMenuView");
					var menustore = menupanel.getStore();
					menustore.getProxy().extraParams ={roleuuid : record[0].get('uuidRole')};
					menustore.load();
				}},
			
			"RoleFormView button[ref=reset]" : {
				click : function(_btn) {
					var form =_btn.up("RoleFormView").getForm();
					form.reset();
				}},
				
			"RoleFormView button[ref=save2]" : {
					click : function(_btn) {						
						var _form =_btn.up("RoleFormView").getForm();
						var abcformrecord = _form.getValues();
						var rolegrid = _btn.up("RoleViewLayout").down("RoleView");
				_form.submit({
							clientValidation:true,
							url:'./insert/user_saverole2',
							method:'POST',
							params:{},
							waitTitle : '请等待' ,
						    waitMsg: '正在提交中',
						    success:function(form,action){
						        //	var resProductObj = Ext.decode(action.response.responseText);
                                //url后台返回的数据{success:true,msg:'成功'}	+action.errors.error
						    	Ext.Msg.alert('提示','角色保存成功');
						    	rolegrid.getStore().load();
						    	},
						    failure:function(form,action){
									Ext.Msg.alert('提示','保存失败！，原因');
								}
						});
					}
			},
			
			"MenuView" : {
				itemclick : function(_this,record,item,index,e,eOpts ){
					var _formpanel = _this.up("TestViewlayout").down("MenuFormView");					
					var _form = _formpanel.getForm();
					_form.findField('uuidmenu').setValue(record.get('id'));
					_form.findField('parentId').setValue(record.get('parentId'));
					_form.findField('text').setValue(record.get('text'));
					var textf = _form.getFields().items;
					for(var i=0;i<textf.length;i++){
						textf[i].setReadOnly(true);
					}
					if(record.get('hrefTarget')!=null || record.get('hrefTarget') != "")
					{
						var fun = record.get('hrefTarget').split(",");
						_form.findField('funViewXtype').setValue(fun[0]);
						_form.findField('funController').setValue(fun[1]);
						_form.findField('funViewName').setValue(fun[2]);
					}
				}				
			},
			
			"MenuView button[ref=add]" : {
				click : function(_btn) {
					var _formpanel = _btn.up("TestViewlayout").down("MenuFormView");					
					var _form = _formpanel.getForm();
					_form.reset();
					var _tree =_btn.up("MenuView");
					var _node = _tree.getSelectionModel().getSelection();
					var id = _node[0].get('id');
					if(_node[0].get('leaf')==true){
						id = _node[0].get('parentId');
					}
					_form.findField('parentId').setValue(id);					
					var textf = _form.getFields().items;
					for(var i=2;i<textf.length;i++){
						textf[i].setReadOnly(false);
					}					
			}},
			
			
		    "MenuView button[ref=modify]" : {
				click : function(_btn) {
					var _formpanel = _btn.up("TestViewlayout").down("MenuFormView");					
					var _form = _formpanel.getForm();
					var _tree =_btn.up("MenuView");					
					var textf = _form.getFields().items;
					for(var i=2;i<textf.length;i++){
						textf[i].setReadOnly(false);
					}				
			}},
			
			
			"MenuView button[ref=delete]" : {
				click : function(_btn) {					
					var _formpanel = _btn.up("TestViewlayout").down("MenuFormView");
					var _tree =_btn.up("MenuView");
					var _form = _formpanel.getForm();
					Ext.MessageBox.confirm("重要提示","是否要删除吗？",
							function(e){
								if(e == 'yes'){
					_form.submit({
						clientValidation:true,
						url:'./json/user_deletemenu',
						method:'POST',
						params:{},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	Ext.Msg.alert('提示','菜单删除成功');	
					    	_tree.getStore().reload();
					    	},
					    failure:function(form,action){
					    	var resProductObj = Ext.decode(action.response.responseText);
						    Ext.Msg.alert('提示','删除失败！，原因'+resProductObj.errors.error);
					    }
					})}})
			}},
			
			"MenuFormView button[ref=save]" : {				
				click : function(_btn) {	
					var _form = _btn.up("MenuFormView").getForm();
					var _tree = _btn.up("TestViewlayout").down("MenuView");
					_form.submit({
						clientValidation:true,
						url:'./insert/user_savemenu',
						method:'POST',
						params:{},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					        //	var resProductObj = Ext.decode(action.response.responseText);
                            //url后台返回的数据{success:true,msg:'成功'}	+action.result.msg
					    	Ext.Msg.alert('提示','菜单保存成功');
					    	_tree.getStore().reload();
					    	var textf = _form.getFields().items;
							for(var i=0;i<textf.length;i++){
								textf[i].setReadOnly(true);
							}					    	
					    	},
					    failure:function(form,action){
						    var resProductObj = Ext.decode(action.response.responseText);
						    Ext.Msg.alert('提示','保存失败！，原因'+resProductObj.errors.error);
							    }
					})
				}},	
				
		"MenuFormView button[ref=reset]" : {				
				click : function(_btn) {	
				var _form = _btn.up("MenuFormView").getForm();				
				_form.reset();
		}},
		
		"MenuView button[ref=addresource]" : {				
			click : function(_btn) {
				var record = _btn.up("MenuView").getSelectionModel().getSelection();
				if(record.length<=0){
					Ext.Msg.alert('提示','请选择需要添加资源的项');
					return;
				}
				if(record[0].get('nodeType')!= 1){
					Ext.Msg.alert('提示','请选择正确的需要添加资源的项');
					return;
				}
				var win = Ext.create ('Ext.window.Window',{
				    title: '资源新增',					    
				    height: 200,
				    width: 300,
				    layout: 'fit',
				    closable :false,
				    modal : true,
				    });		
				var resourceform = Ext.create("core.authority.view.ResourceForm");
				resourceform.getForm().setValues({
					uuidmenu:'',
					parentId:record[0].get('id'),
					text:'',
					aciton:'',
					type : 2,
				}); 
				win.add(resourceform);
				win.show();
		}},
		
		
		"ResourceForm button[ref=save]" : {				
			click : function(_btn) {	
				var _form = _btn.up("ResourceForm").getForm();
				var treestore = Ext.getCmp("MainViewLayout").down("MenuView").getStore();
				_form.submit({
					clientValidation:true,
					url:'./insert/user_savemenu',
					method:'POST',
					params:{},
					waitTitle : '请等待' ,
				    waitMsg: '正在提交中',
				    success:function(form,action){
				        //	var resProductObj = Ext.decode(action.response.responseText);
                        //url后台返回的数据{success:true,msg:'成功'}	+action.result.msg
				    	Ext.Msg.alert('提示','菜单保存成功');
				    	treestore.reload();
				    	_form.reset();			    	
				    	},
				    failure:function(form,action){
					    var resProductObj = Ext.decode(action.response.responseText);
					    Ext.Msg.alert('提示','保存失败！，原因'+resProductObj.errors.error);
						    }
				})
			}},	

		"ResourceForm button[ref=modify]" : {				
				click : function(_btn) {	
				var _form = _btn.up("ResourceForm").getForm();				
				_form.reset();
		}},
			
			
			
		"ResourceForm button[ref=close]" : {				
				click : function(_btn) {	
				var _window = _btn.up("window");				
				_window.close();
		}},
		
		"CheckedMenuView" : {
			checkchange : function( node, checked, eOpts ){				
				treeStoreSelectedSon(node,checked);
				treeStoreSelectedFather(node,checked);				
	    }},

		"CheckedMenuView button[ref=reaccredit]" : {
			click : function(_btn) {
				var rolegrid = _btn.up("RoleViewLayout").down("RoleView");
				var record = rolegrid.getSelectionModel().getSelection();
				if(record.length<=0){
					Ext.Msg.alert('提示','请先选择一个角色');
					return;
				}
				
				
				var _tree	=	_btn.up('CheckedMenuView');
				var rootnodes = _tree.getStore().getRootNode(); 
				var str =new Array();
				treeStoregetchecked(rootnodes,str);
				
				var strjoin = str.join(',');
				
		        Ext.Ajax.request({
				      url:'./json/user_reaccredit',
				      method:'POST',
				      params:{strjoin:strjoin,roleid:record[0].get('uuidRole')},
				      success:function(response,option){ 
				        var obj =Ext.decode(response.responseText);	
				        if(obj.success == true){
				        	Ext.Msg.alert("提示","授权成功！");
				      	}else{
				      		Ext.Msg.alert("提示","授权失败！");
				      	}
				      	},
				      failure:function(){
				      	 Ext.Msg.alert("提示","授权失败")
				      }
				      });
		}}
	    
	    
		})
	},
		
	views : [
	    "core.authority.view.TestViewLayout",
	    "core.authority.view.UserView",
	    "core.authority.view.RoleView",
	    "core.authority.view.RoleFormView",
	    "core.authority.view.RoleViewLayout",
	    "core.authority.view.MenuFormView",
	    "core.authority.view.MenuView",
	    "core.authority.view.CheckedMenuView",
	    "core.authority.view.ResourceForm"
	],
	stores : ["core.authority.store.UserStore","core.authority.store.RoleStore","core.authority.store.MenuStore","core.authority.store.CheckedMenuStore"],
	models : ["core.authority.model.UserModel","core.authority.model.RoleModel","core.authority.model.MenuModel"]
});