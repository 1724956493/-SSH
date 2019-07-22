Ext.define("core.employee.report.bills.quality.byjob.view.QcBanByjobChart", {
			extend : 'Ext.chart.Chart',
			alias: 'widget.QcBanByjobChart',
			style: 'background:#fff',
            autoShow: true,

            animate: true,
            shadow: true,
            store: "core.employee.report.bills.quality.byjob.store.QcBanByjobStore",
            legend: {
                position: 'right'
            },
            axes: [{
                type: 'Numeric',
                position: 'bottom',
                fields: ['A101','A102','A103','A104','A105','A106','A107','A108','A109','A110','A111','A112','A113'],
                title: '违章数量',
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
                yField: ['A101','A102','A103','A104','A105','A106','A107','A108','A109','A110','A111','A112'],
                tips: {
                    trackMouse: true,
                    width: 140,
                    height: 28,
                    renderer: function(storeItem, item) {
                      this.setTitle(storeItem.get('jobcode'));
                    }
                  },
                stacked: true ,
                title : ['质量弄虚作假','外检项目三次及三次以上报检','除部门不可抗力外的因素导致外检拒检','不合格品流转下道','同类质量事故公司内重复发生','员工未经过培训就上岗 ','材料用错/错用','船东书面质量投诉','未按图纸施工','焊道夹杂不清','双相工艺违章','项目未准备好','项目未准备好','未按要求预热']
            }],
        
	  
		initComponent : function() {
				this.callParent(arguments);
			}
			
})