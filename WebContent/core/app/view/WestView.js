/**
 * 宸﹁竟閮ㄥ垎
 */
Ext.define("core.app.view.WestView",{
	extend: 'Ext.panel.Panel',
	alias: 'widget.westview',
	collapsible: true,
	split: true,
	defaults: {
		bodyStyle: 'padding:2px'
	}, 	
	border:1,
	margins: '2 2 0 0',
	width: 225,
	minSize: 100,
	maxSize: 250,
	title:"功能模块导航",
	layout : 'fit',
	
	bbar: [{
	    xtype: 'label',
	    width: 250,
	    id: 'clock',
	    listeners: {
	      'render': function( _this, eOpts ) {
	    	  Ext.TaskManager.start({
	        	    run: function() {	        	      
	        	      _this.setText(Ext.Date.format(new Date(), 'Y-m-d g:i:s A D'));
	        	    },
	        	    interval: 1000
	        	  });
	      }
	    }
	  }],

	items:[{
		xtype:"Menulayout"
	}],
    initComponent: function(){
        this.callParent(arguments);
        
        
        	  
    }
});



