package com.nts.teststruts.service.impl;

import java.sql.SQLException;

import com.nts.teststruts.dao.impl.BdCorpDaoImpl;
import com.nts.teststruts.dao.impl.BdDefdocDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.dao.impl.PamLocationDaoImpl;
import com.nts.teststruts.model.PamEquip;

public class PamEquipServ {

	private String pkEquip;
	private String EquipName;
	private String EquipCode;
	private String Equipdepcode;
	private String EquipModel;
	private String EquipSpec;
	private String EquipDept;
	private String EquipLocal;
	private String EquipUseDept;
	private String EquipCorp;
	private String EquipCategoryPk;
	
	public PamEquipServ() {
	}

	public PamEquipServ(PamEquip pamequip) throws Exception {
		this.pkEquip = pamequip.getPkEquip();
		this.EquipCode = pamequip.getEquipCode();
		this.EquipName = pamequip.getEquipName();
		this.EquipModel = pamequip.getModel();
		this.EquipSpec = pamequip.getSpec();
		this.Equipdepcode = pamequip.getDef3();
		this.EquipCategoryPk = pamequip.getPkCategory();

		if(pamequip.getPkCorp()==null)
			{
				this.EquipCorp = null;
			}
		else
			{
				this.EquipCorp = new BdCorpDaoImpl().GetByPk(pamequip.getPkCorp()).getUnitname();
			}
		
		if(pamequip.getPkMandept()==null)
		{
			this.EquipDept = null;
		}
		else
		{
			this.EquipDept = new BdDeptdocDaoImpl().GetByPk(pamequip.getPkMandept()).getDeptname();
		}
		if(pamequip.getPkUsedept()==null)
		{
			this.EquipUseDept = null;
		}
		else
		{
			this.EquipUseDept = new BdDeptdocDaoImpl().GetByPk(pamequip.getPkUsedept()).getDeptname();
		}
		if(pamequip.getPkLocation()==null)
		{
			this.EquipLocal = null;
		}
		else
		{
			this.EquipLocal =new PamLocationDaoImpl().GetByPk(pamequip.getPkLocation()).getLocationName();
		}
		
		
	}
	
	
	
	public String getPkEquip() {
		return pkEquip;
	}
	public void setPkEquip(String pkEquip) {
		this.pkEquip = pkEquip;
	}
	public String getEquipName() {
		return EquipName;
	}
	public void setEquipName(String equipName) {
		EquipName = equipName;
	}
	public String getEquipCode() {
		return EquipCode;
	}
	public void setEquipCode(String equipCode) {
		EquipCode = equipCode;
	}
	public String getEquipDept() {
		return EquipDept;
	}
	public void setEquipDept(String equipDept) {
		EquipDept = equipDept;
	}
	public String getEquipLocal() {
		return EquipLocal;
	}
	public void setEquipLocal(String equipLocal) {
		EquipLocal = equipLocal;
	}
	public String getEquipUseDept() {
		return EquipUseDept;
	}
	public void setEquipUseDept(String equipUseDept) {
		EquipUseDept = equipUseDept;
	}
	public String getEquipCorp() {
		return EquipCorp;
	}
	public void setEquipCorp(String equipCorp) {
		EquipCorp = equipCorp;
	}

	public String getEquipModel() {
		return EquipModel;
	}

	public void setEquipModel(String equipModel) {
		EquipModel = equipModel;
	}

	public String getEquipSpec() {
		return EquipSpec;
	}

	public void setEquipSpec(String equipSpec) {
		EquipSpec = equipSpec;
	}

	public String getEquipdepcode() {
		return Equipdepcode;
	}

	public void setEquipdepcode(String equipdepcode) {
		Equipdepcode = equipdepcode;
	}

	public String getEquipCategoryPk() {
		return EquipCategoryPk;
	}

	public void setEquipCategoryPk(String equipCategoryPk) {
		EquipCategoryPk = equipCategoryPk;
	}
	
	
	
}
