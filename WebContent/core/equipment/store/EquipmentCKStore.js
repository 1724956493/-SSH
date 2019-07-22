/*
 * 商品数据集类
 */
 Ext.define("core.equipment.store.EquipmentCKStore",{
 	extend:'Ext.data.Store',
	model:'core.equipment.model.EquipmentCKModel',
	pageSize:30,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/pamequip_pagetoJson',
        extraParams:{     
        	        	deptpk :'1002C11000000000008B' 
        },
        reader: {
            type: 'json',
            root:'data',
            totalProperty:'total'//总项数
        }
    },
    autoLoad:true
 });