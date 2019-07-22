/*
 * 商品数据集类
 */
 Ext.define("core.equipment.store.EquipmentStore",{
 	extend:'Ext.data.Store',
	model:'core.equipment.model.EquipmentModel',
	pageSize:30,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/pamequip_pagetoJson',
        extraParams:{             	        	
        },
        reader: {
            type: 'json',
            root:'data',
            totalProperty:'total'//总项数
        }
    },
    autoLoad:true
 });