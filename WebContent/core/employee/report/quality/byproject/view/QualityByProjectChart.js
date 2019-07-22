Ext.define("core.employee.report.quality.byproject.view.QualityByProjectChart", {
			extend : 'Ext.chart.Chart',
			alias: 'widget.QualityByProjectChart',
			style: 'background:#fff',
            autoShow: true,

            animate: true,
            shadow: true,
            store: "core.employee.report.quality.byproject.store.QualityByProjectStore",
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
                fields: ['jobname'],
                title: '项目名称'
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
                    this.setTitle(storeItem.get('jobname') + ': ' + storeItem.get('count'));
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
                xField: 'jobname',
                yField: 'count'
            }],
        
	  
		initComponent : function() {
				this.callParent(arguments);
			}
			
})