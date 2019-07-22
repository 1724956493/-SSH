/**主控制器*/
Ext.define("core.app.controller.MainController",{
	extend : "Ext.app.Controller",
	init : function(){
		var self = this;
		
		/**显示登陆窗口*/
	//	var loginWin =Ext.create("core.app.view.LoginWindow");
	//	loginWin.show();
		
		/**公用添加页面的方法*/
		/**
		 * 动态加载controller并渲染它的主窗体
		 */
		this.addFunItem=function(funInfo){
			if(funInfo){
				var mainView=funInfo.mainView;
				var funPanel=mainView.down(funInfo.funViewXtype);
			//	Ext.Msg.alert('1',funPanel);
				if(!funPanel){
					self.application.getController(funInfo.funController);
					funPanel=Ext.create(funInfo.funViewName,{
						closable:true,
						closeAction:'destroy'
					});	
					mainView.add(funPanel);
					mainView.setActiveTab(funPanel);
				}else{									
					mainView.setActiveTab(funPanel);
				}
			}
		},
		/**下在是控制部分*/
		this.control({
			"LoginWin textfield[ref=password]" : {
				keypress : function(e) {
					        if(e.charCode==Ext.EventObject.ENTER){
        						   Ext.Msg.alert('info','回车');
        					}
				}},
			
			
			"LoginWin button[ref=load]" : {
				click : function(_btn) {
					var _form = Ext.getCmp("loginform").getForm();
					var  win= _btn.up("LoginWin");
					_form.submit({
						clientValidation:true,
						url:'./json/user_login',
						method:'POST',
					//	params:{newStatus: 'delivered'},
						waitTitle : '请等待' ,
					    waitMsg: '正在提交中',
					    success:function(form,action){
					    	var result = Ext.decode(action.response.responseText);
					    	win.hide();
					    	Ext.getStore("core.app.store.MenuStore").load();
					     	Ext.getCmp("MainViewLayout").show();
					     	var userpk = Ext.getCmp("userpk");
					     	userpk.setValue(result.user.userpk);
					    	var dis=Ext.getCmp("displaylogin");
							dis.setValue("<font color=blue><b>"+result.user.deptname+":"+result.user.username+":您好</b></font>");					    	
					    },
					    failure:function(form,action){
					    	var result = Ext.decode(action.response.responseText);
					    	Ext.Msg.alert('提示',result.error);
					    }
					})}},
			
			"LoginWin button[ref=reset]" : {
				click : function(_btn) {
					var form = Ext.getCmp("loginform").getForm();
					form.reset();
					Ext.Ajax.request({
						url:'./json/user_loginout',
						method:'POST'
					})
			}},
			
			"topview button[ref=logout]" : {
						click: function(_btn){
							var  win= _btn.up("viewport").down("LoginWin");
							win.show();			
							Ext.getCmp("MainViewLayout").hide();
					//		Ext.Msg.alert('提示',Ext.getCmp("userpk").getValue()+'已退出');
					//		Ext.util.Cookies.clear("autologin");

						}
					},
					
					/**退出系统*/
			"topview button[ref=exit]" : {
						click: function(btn){
							Ext.Msg.confirm("提示","是否要退出系统",function(btn){
								if(btn == 'yes'){
									if(document.all){//IE
										window.open('', '_parent', '');
										window.close();
									}else{//FF
										window.open('', '_self', '');
										window.close();
									}
								}
							},this);
						}
					},
			
			
			"westview treepanel":{
				itemclick:function(tree,record,item,index,e,eOpts){
					var mainView=tree.up("Mainviewlayout").down("centerview");
					var aaa = record.get('hrefTarget').split(',');
					/**用户管理*/
					if(aaa.length == 3){
					self.addFunItem({
						mainView:mainView,
						funViewXtype:aaa[0],
						funController:aaa[1],
						funViewName:aaa[2]
					});
					}
				}
			}
		});
	},
	views : [
		"core.app.view.TopView",
		"core.app.view.WestView",
		"core.app.view.CenterView",
		"core.app.view.MainViewLayout",
		"core.app.view.LoginWin",
		"core.app.view.MenuLayout"
	],
	stores : ["core.app.store.MenuStore"],
	models : []
});