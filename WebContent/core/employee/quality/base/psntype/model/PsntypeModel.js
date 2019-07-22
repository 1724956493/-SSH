 Ext.define("core.employee.quality.base.psntype.model.PsntypeModel",{
	 extend : 'Ext.data.Model',
	 fields : ['uuid',
	 		   'psnname',
	 		   'deptpk',
	 		   'parentdeptpk',
	 		   'area',
	 		   'project',
	 		   'psntype',
	 		   'note',
	 		   'createTime'   
	           ]
 });