/*
 * 商品数据集类
 */
 Ext.define("core.guard.carinfo.store.CarInfoStore",{
 	extend:'Ext.data.Store',
	model:"core.guard.carinfo.model.CarInfoModel",
	pageSize:10,
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adcarinfo_alltojson',
        reader: {
            type: 'json'
        }
    },
    autoLoad:true
 });