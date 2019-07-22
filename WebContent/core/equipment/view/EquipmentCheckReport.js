Ext.define("core.equipment.view.EquipmentCheckReport", {
	extend : 'Ext.panel.Panel',
//	title: 'Hello',
//    height: 400,
//    width : 1500,
	alias :'widget.EquipmentCheckReport',
	layout:'fit',
	scrollable:true,
		tbar:[
		{
			xtype:'datefield',
			fieldLabel: '开始日期',
			maxValue: new Date(),
			format: 'Y-m-d',
			name:'startdate',
			id:'equip_report_startd',
			value: Ext.Date.add(new Date(), Ext.Date.DAY, -30)
		},{
			xtype:'datefield',
			fieldLabel: '结束日期',
			format: 'Y-m-d',
			name:'enddate',
			id:'equip_report_endd',
			value: new Date()
		},{
			xtype:'button',
			text:'--->查询',
			border : '1,1,1,1',
			ref:'find'			
		}
	],
	items:[
		{xtype:'chart',
		store:"core.equipment.store.EquipReportStore",
		autoScroll:true,
		autoSize:true,
		animate: true,
        shadow: true,
        legend: {
                position: 'right'
            },
        axes: [
        {
            type: 'Numeric',
            position: 'left',
            fields: ['s3','s2'],
            label: {
                renderer: Ext.util.Format.numberRenderer('0,0')
            },
            title: '数量',
            grid: true,
            minimum: 0
        },
        {
            type: 'Category',
            position: 'bottom',
            fields: ['s1'],
            title: '设备名称'
        }
    ],
    series: [
        {
            type: 'column',
            axis: 'left',
            highlight: true,
            stacked: true,
            listeners : {  
                    itemclick : function(o) { 
                        alert(o.value);
                    }
                },
            tips: {
              trackMouse: true,
              width: 200,
              height: 28,
              renderer: function(storeItem, item) {
                this.setTitle(storeItem.get('s1') + ':未点检 ' + storeItem.get('s2')+ ':已点检 ' + storeItem.get('s3') );
              }
            },
            label: {
              display: 'insideEnd',
               'text-anchor': 'middle',
                field: ['s3','s2'],
                renderer: Ext.util.Format.numberRenderer('0'),
                orientation: 'horizontal',
                color: '#333'
            },
            xField: 's1',
            yField: ['s3','s2'],
            title:['已点检','未点检']
        }
    ]
	  }
	]
})