Ext.define("core.util.GridSearchText",{
	extend: 'Ext.form.field.Text',
	alias: 'widget.GridSearchText',
    flex : 1,
    margin: 2,
    enableKeyEvents: true,
    listeners: {
    	 keyup: function(_this, e, eOpts) {
    	 	var store = _this.up('grid').store;
    	 	var column = _this.up('gridcolumn');
            store.clearFilter();
            if (this.value) {
                store.filter({
                    property     : column.dataIndex,
                    value         : _this.value,
                    anyMatch      : true,
                    caseSensitive : false
                });
            }            
    	 },
    	 buffer: 500
    }
  })