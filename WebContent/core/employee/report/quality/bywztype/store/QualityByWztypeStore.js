 Ext.define("core.employee.report.quality.bywztype.store.QualityByWztypeStore",{
 	extend:'Ext.data.Store',
	model:'core.employee.report.quality.bywztype.model.QualityByWztypeModel',
	alias: 'widget.QualityByWztype',
	//autoSync:true,//与服务器同步
	proxy: {
        type: 'ajax',
        url:'./json/adpsndocrp_getWztypeReport',
        reader: {
            type: 'array'
       //     root:'data',
      //      totalProperty:'total'//总项数
        },
        actionMethods:{
        	read:'POST'
        },
        timeout:180000
    }
 ,  groupField:'wzname'
 });