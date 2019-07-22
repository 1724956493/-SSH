 Ext.define("core.equipment.store.EquipReportStore",{
 	extend:'Ext.data.Store',
	model:'core.equipment.model.EquipmentReportModel',

	//autoSync:true,//与服务器同步
	proxy:{
		type:"ajax",
		url:"./json/pamequip_reportJson",
		extraParams:{         
//			deptpk : '1002C11000000000008B'
        },
		reader:{
			type:"json"				
		}		
	}
//	autoLoad:true	
	
	
/*	    data: [
        { 's1': '风磨机',   's2':1621 },
        { 's1': '2吨手拉葫芦',   's2': 292 },
        { 's1': '冷风机', 's2': 13 },
        { 's1': '100吨千斤顶',  's2': 61 },
        { 's1': '全方位手动割刀',  's2':1 }
    ]*/

	
	
 });