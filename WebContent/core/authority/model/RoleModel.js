/*
 * 商品实体类
 */
 Ext.define("core.authority.model.RoleModel",{
	 extend : 'Ext.data.Model',
     fields : [{name:'uuidRole',type: 'string'},
               {name:'rolename',type: 'string'},
               {name:'createTime',type: 'string'},
               {name:'roledesc',type: 'string'}]
 });