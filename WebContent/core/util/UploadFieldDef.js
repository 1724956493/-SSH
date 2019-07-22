var uploadid = "";  
var types = ""; 
var filepath = "";
Ext.define("core.util.UploadFieldDef",{
	extend: 'Ext.form.field.Text',
	alias: 'widget.UploadFieldDef',
	blankText :'请点击上传文件',
	readOnly : true,	
	config:{  
		contexType : 'jpg', 
		filepath : '/upload',
    },  
	
	listeners: {		
		focus: function(_this){
			uploadid = _this.getId() ;   
			types = _this.getContexType();
			filepath = _this.getFilepath();
			winUpload.show();		 
		}
	},
    		
	initComponent: function(){
	    this.callParent(arguments);
	}
})


var formUpload = new Ext.form.FormPanel({  
  
    baseCls: 'x-plain',  
  
    labelWidth: 80,  
  
    fileUpload:true,  
  
  
    items: [{  
  
      xtype: 'fileuploadfield',  
  
      fieldLabel: '文 件',  
  
      name: 'file',  
  
      allowBlank: false,  
  
      blankText: '请上传文件',  
  
      anchor: '90%'  // anchor width by percentage  
  
    }]  
  
  });  


var winUpload = new Ext.Window({  	  
    title: '资源上传',    
    width: 400,   
    height:100,    
    layout: 'fit',    
    plain:true,  
    modal : true, 
    bodyStyle:'padding:5px;',   
    buttonAlign:'center',   
    items: formUpload,   
    buttons: [{   
      text: '上 传',  
      handler: function() {  
  
        if(formUpload.form.isValid()){  
  
          Ext.MessageBox.show({  
  
               title: 'Please wait',  
  
               msg: 'Uploading...',  
  
               progressText: '',  
  
               width:300,  
  
               progress:true,  
  
               closable:false,  
  
               animEl: 'loading'  
  
             });  
  
          formUpload.getForm().submit({      
  
            url:'./json/upload_uploadFileUtils',  
            method:'POST',  
            params:{fileType:types ,filePath:filepath},
            success: function(form, action){  
               var objxzdz = Ext.getCmp(uploadid); 
               var result = action.result;  
               Ext.Msg.alert('成功',result.message);  
               objxzdz.setValue(result.filename);
               formUpload.getForm().reset();
               winUpload.hide();    
            },      
  
             failure: function(form, action){      
  
              //... action生成的json{msg:上传失败},页面就可以用action.result.msg得到非常之灵活  
  
              Ext.Msg.alert('Error', action.result.message);      
  
             }  
  
          })             
  
        }  
  
       }  
  
    },{    
      text: '取 消',  
      handler:function(){winUpload.hide();}   
    }]  
  
  });  