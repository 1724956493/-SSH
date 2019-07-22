var rescourestore = Ext.create("Ext.data.Store",{
	fields : [
		 'uuidMenu',
		 'menuname',
		 'menucode',
		 'action',
		 'type'
	  ] ,
	proxy: {
        type: 'ajax',
        url:'./json/resource_getResource',
        reader: {
            type: 'json'
        },
        actionMethods:{
        	read:'POST'
        },
      
        timeout:180000
    }
 });


Ext.define("core.util.ResourceCombo",{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.ResourceCombo',
	
    store:rescourestore,
    queryMode: 'local',
    displayField: 'menuname',
    valueField: 'menucode',	
	forceSelection:true,
	
})