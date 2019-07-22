/*
 * 商品实体类
 */
 Ext.define("core.authority.model.MenuModel",{
	 extend : 'Ext.data.Model',
	 fields : ['id', 'text', 'code', 'leaf','expandable','expanded','hrefTarget']
 });