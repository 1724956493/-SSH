Ext.define("core.equipment.view.EquipmentGridView", {
			extend:"Ext.grid.Panel",
	//		title:'新增人员列表',
			layout: 'border',
			store:"core.equipment.store.EquipmentStore",
	//		id:'emviewlayout',
			alias : 'widget.EquipmentGridView',
			defaults: {
			   align : 'center',
		       field : {xtype:"textfield"}
              },
            enableKeyNav:true,  //可以使用键盘控制上下
	        columnLines:true, //展示竖线
	        columns:[
					{xtype: 'rownumberer',width:100},
					{text:"设备编码",dataIndex:"equipCode",width:100},
					{text:"设备名称",dataIndex:"equipName",width:100},
					{text:"设备规格",dataIndex:"model",width:130},
					{text:"设备型号",dataIndex:"spec",width:130},
					{text:"设备地点",dataIndex:"pkLocation",width:130}							
					],
					
			bbar: [{xtype: 'pagingtoolbar',
			store: "core.equipment.store.EquipmentStore",
			pageSize:25,
			displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
			emptyMsg: "没有数据",
            beforePageText: "当前页",
            afterPageText: "共{0}页",
            displayInfo: true   
            }],
	        
					
			initComponent : function() {
				this.callParent(arguments);
			}
		});