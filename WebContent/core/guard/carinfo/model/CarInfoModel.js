/*
 * 人员信息实体类
 */
 Ext.define("core.guard.carinfo.model.CarInfoModel",{
	 extend : 'Ext.data.Model',
	 fields : ['uuid', 'carid', 'cartype','carcolor', 'carowner', 'ownerdept', 'ownerjob','ownertelephone', 'note', 'createTime']
 });