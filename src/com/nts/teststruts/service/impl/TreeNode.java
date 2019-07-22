package com.nts.teststruts.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nts.teststruts.dao.impl.AdMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdRoleMenuDaoImpl;
import com.nts.teststruts.dao.impl.AdWztypeDaoImpl;
import com.nts.teststruts.dao.impl.BdCorpDaoImpl;
import com.nts.teststruts.dao.impl.BdDeptdocDaoImpl;
import com.nts.teststruts.model.AdMenu;
import com.nts.teststruts.model.AdRolemenu;
import com.nts.teststruts.model.AdWztype;
import com.nts.teststruts.model.BdCorp;
import com.nts.teststruts.model.BdDeptdoc;

public class TreeNode {

	private String id;
	private String text;
	private String code;
	private Boolean leaf;
	private String href;
	private String hrefTarget;
	private String cls;
	private String icon;
	private Boolean expandable=true;
	private Boolean expanded;
	private String description;
	private Boolean checked;
	private String action;
	private String parent;
	private String nodeType;
	private String nodeInfo;
	private String nodeInfoType;
	private Integer orderIndex;
	private Boolean disabled;
	private List<TreeNode> children=new ArrayList<TreeNode>();
	
	public TreeNode(){}
	
	public TreeNode(AdMenu admenu,String cuserid){
		this.id = admenu.getUuidMenu();
		this.text =admenu.getMenuname();
//		this.code = admenu.getMenucode();
		this.parent =admenu.getParentid();
		this.hrefTarget = admenu.getAction();
		//this.href = admenu.getAction();
		if(admenu.getAction() == null || admenu.getAction() == "")
		   {this.leaf = false;}
		else 
		   {this.leaf = true;}
		List<AdMenu> childAdmenus = new AdMenuDaoImpl().getByRole(admenu.getUuidMenu(), cuserid);
		for(AdMenu childAdmenu : childAdmenus)
			{
				this.children.add(new TreeNode(childAdmenu,cuserid));
			}
	}
	
	public TreeNode(AdMenu admenu,List<String> rolemenus){
		this.id = admenu.getUuidMenu();
		this.text =admenu.getMenuname();
//		this.code = admenu.getMenucode();
		this.parent =admenu.getParentid();
		this.nodeType = admenu.getType()+"";
		this.hrefTarget = admenu.getAction();
		//this.href = admenu.getAction();
/*		if(admenu.getAction() == null || admenu.getAction() == "")
		   {this.leaf = false;}
		else 
		   {this.leaf = true;}*/
		if(rolemenus.contains(admenu.getUuidMenu())){
			this.checked = true;
		}else{
			this.checked = false;
		}
		List<AdMenu> childAdmenus = new AdMenuDaoImpl().getByResourceParentID(admenu.getUuidMenu());
		for(AdMenu childAdmenu : childAdmenus)
			{
				this.children.add(new TreeNode(childAdmenu,rolemenus));
			}
	}

	
	public TreeNode(AdWztype aswztype,int type) throws SQLException{
		this.id= aswztype.getUuid();
		this.text = aswztype.getWzname();
		this.parent = aswztype.getWzparent();
		List<AdWztype> adWztypes = new AdWztypeDaoImpl().getByParentID(aswztype.getUuid(),type);
		this.code = aswztype.getWzcode();
		this.description =aswztype.getNote();
		this.leaf = false;
		for(AdWztype aswztype1 : adWztypes){		
			this.children.add(new TreeNode(aswztype1,type));}
	}
	
	public TreeNode(BdDeptdoc bddeptdoc) throws SQLException{
		this.id = bddeptdoc.getPkDeptdoc();
		this.text = bddeptdoc.getDeptname();
		if(bddeptdoc.getPkFathedept() == null || bddeptdoc.getPkFathedept() ==""){
			this.parent = bddeptdoc.getPkCorp();
		}else {
			this.parent = bddeptdoc.getPkFathedept();
		}
		List<BdDeptdoc> childbddeptdocs = new BdDeptdocDaoImpl().GetByFtPk(bddeptdoc.getPkDeptdoc());
		if(childbddeptdocs.size() == 0){
			this.leaf = true;
		}else{
			this.leaf = false;
		}
		for(BdDeptdoc childbddeptdoc : childbddeptdocs)
		{
			this.children.add(new TreeNode(childbddeptdoc));
		}
	}
	
	public TreeNode(BdCorp bdcorp) throws SQLException{
		this.id = bdcorp.getPkCorp();
		this.text = bdcorp.getUnitname();
		this.parent = bdcorp.getFathercorp();
		this.leaf = false;
		List<BdCorp> childAdcorps = new BdCorpDaoImpl().GetByCode(bdcorp.getPkCorp());
		if(childAdcorps.size() != 0){
			for(BdCorp childAdcorp : childAdcorps){		
				this.children.add(new TreeNode(childAdcorp));
			};
		}else{
			List<BdDeptdoc> bddeptdocs = new BdDeptdocDaoImpl().GetByCorp(bdcorp.getPkCorp());
			for(BdDeptdoc childbddeptdoc : bddeptdocs)
			{
				this.children.add(new TreeNode(childbddeptdoc));
			}
//			this.children.add(new TreeNode(childAdcorp));
		}
	}
	

	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getExpandable() {
		return expandable;
	}
	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(String nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
	public String getNodeInfoType() {
		return nodeInfoType;
	}
	public void setNodeInfoType(String nodeInfoType) {
		this.nodeInfoType = nodeInfoType;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", text=" + text + ",leaf=" + leaf + ", href=" + href
				+ ", hrefTarget=" + hrefTarget + ", cls=" + cls + ", icon=" + icon + ", expandable=" + expandable
				+ ", expanded=" + expanded + ", description=" + description + ", checked=" + checked + ", action="
				+ action + ", parent=" + parent + ", nodeType=" + nodeType + ", nodeInfo=" + nodeInfo
				+ ", nodeInfoType=" + nodeInfoType + ", orderIndex=" + orderIndex + ", disabled=" + disabled
				+ ", children=" + children + "]";
	}

	
}