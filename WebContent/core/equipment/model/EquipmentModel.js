/*
 * 人员信息实体类
 */
 Ext.define("core.equipment.model.EquipmentModel",{
	 extend : 'Ext.data.Model',
	 fields : ['pkEquip', 'equipCode', 'equipName','model','spec','pkCategory','pkMandept','pkUsedept','pkLocation','pkCorp']
 });