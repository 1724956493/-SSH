Ext.define('core.util.MultiComboBox', {  
    extend: 'Ext.form.ComboBox',  
    alias: 'widget.multicombobox',  
    xtype: 'multicombobox',  
    initComponent: function(){  
        this.multiSelect = true;  
        this.listConfig = {                     
        	  itemTpl  :  '<tpl for="."><input type=checkbox>{name}</br></tpl>',      
        	  
              listeners:{  
                  itemclick:function(view, record, item, index, e, eOpts ){  
                      var isSelected = view.isSelected(item);  
                      var checkboxs = item.getElementsByTagName("input");  
                      if(checkboxs!=null)  
                      {  
                          var checkbox = checkboxs[0];  
                          if(!isSelected)  
                          {  
                              checkbox.checked = true;  
                          }else{  
                              checkbox.checked = false;  
                          }  
                      }  
                  }  
              }         
        }         
        this.callParent();  
    }  
}); 