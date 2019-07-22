 Ext.define("core.employee.quality.base.wztype.model.WzTypeModel",{
	 extend : 'Ext.data.Model',
	 fields : ['id',
	 		   'text',
	 		   'code',
	 		   'leaf',
	 		   'parent',
	 		   'expandable',
	 		   'expanded',
	 		   'children'	 		   
	           ]
 });