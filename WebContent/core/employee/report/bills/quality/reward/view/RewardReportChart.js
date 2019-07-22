Ext.define("core.employee.report.bills.quality.reward.view.RewardReportChart", {
			extend : 'Ext.chart.Chart',
			alias: 'widget.RewardReportChart',
			style: 'background:#fff',
            autoShow: true,

            animate: true,
            shadow: true,
            store: "core.employee.report.bills.quality.reward.store.RewardReportStore",
            legend: {
                position: 'right'
            },
            axes: [{
                type: 'Numeric',
                position: 'bottom',
                fields: ['JLA1','JLA2'],
                title: '奖励数量',
                grid: true,
            }, {
                type: 'Category',
                position: 'left',
                fields: ['jobcode'],
                title: false
            }],
            series: [{
                type: 'bar',
                axis: 'bottom',
                gutter: 80,
                xField: 'jobcode',
                yField: ['JLA1','JLA2'],
                tips: {
                    trackMouse: true,
                    width: 140,
                    height: 28,
                    renderer: function(storeItem, item) {
                      this.setTitle(storeItem.get('jobcode'));
                    }
                  },
                stacked: true ,
                title : ['项目一次性通过奖励','月度通过率奖励']
            }],
        
	  
		initComponent : function() {
				this.callParent(arguments);
			}
			
})