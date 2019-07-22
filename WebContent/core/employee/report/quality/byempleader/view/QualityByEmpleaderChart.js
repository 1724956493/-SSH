Ext.define("core.employee.report.quality.byempleader.view.QualityByEmpleaderChart", {
			extend : 'Ext.chart.Chart',
			alias: 'widget.QualityByEmpleaderChart',
			style: 'background:#fff',
            autoShow: true,

            animate: true,
            shadow: true,
            store: "core.employee.report.quality.byempleader.store.QualityByEmpleaderStore",
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['count'],
                label: {
                    renderer: Ext.util.Format.numberRenderer('0,0')
                },
                title: '数量',
                grid: true,
                minimum: 0
            }, {
                type: 'Category',
                position: 'bottom',
                fields: ['psnname'],
                title: '主管领导'
            }],
            series: [{
                type: 'column',
                axis: 'left',
                highlight: true,
                tips: {
                  trackMouse: true,
                  width: 140,
                  height: 28,
                  renderer: function(storeItem, item) {
                    this.setTitle(storeItem.get('dept')+ ': ' +storeItem.get('psnname') + ': ' + storeItem.get('count'));
                  }
                },
                label: {
                  display: 'insideEnd',
                  'text-anchor': 'middle',
                    field: 'count',
                    renderer: Ext.util.Format.numberRenderer('0'),
                    orientation: 'horizontal',
                    color: '#333'
                },
                xField: 'psnname',
                yField: 'count'
            }],
        
	  
		initComponent : function() {
				this.callParent(arguments);
			}
			
})