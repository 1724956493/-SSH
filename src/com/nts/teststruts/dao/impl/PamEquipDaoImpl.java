package com.nts.teststruts.dao.impl;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.hibernate.Query;
import org.hibernate.Session;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.nts.teststruts.model.PamEquip;
import com.nts.teststruts.service.impl.MapInfo;
import com.nts.teststruts.util.DBUtil;
import com.nts.teststruts.util.MatrixToImageWriter;
import com.nts.teststruts.util.Merge2;


public class PamEquipDaoImpl {

	 Session session;
	 PamEquip pamequip;
		

	public List<PamEquip> GetAll() throws SQLException
	{
		String hql ="from PamEquip";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		List<PamEquip> PamEquip = query.list();	
		DBUtil.closeSession();
		return PamEquip;
	}
	
	public int getTotal() throws SQLException
	{
		String hql ="select count(p) from PamEquip p";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		Number total = (Number)query.uniqueResult();	
		DBUtil.closeSession();
		return total.intValue();
	}
	
	public int getDeptTotal(String deptpk) throws SQLException
	{
		String hql ="select count(*) from PamEquip where pkMandept =:pkMandept";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkMandept",deptpk);
		Number total = (Number)query.uniqueResult();	
		DBUtil.closeSession();
		return total.intValue();
	}
	
	public List<PamEquip> GetByPage(int pageNo,int pageSize) throws SQLException
	{
		String hql ="from PamEquip";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<PamEquip> PamEquip = query.list();	
		DBUtil.closeSession();
		return PamEquip;
	}
	
	public List<PamEquip> GetByDeptPage(int pageNo,int pageSize,String deptpk) throws SQLException
	{
		String hql ="from PamEquip where pkMandept = :pkMandept";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkMandept",deptpk);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		List<PamEquip> PamEquip = query.list();	
		DBUtil.closeSession();
		return PamEquip;
	}
	
	public PamEquip GetByPk(String pk_equip) throws SQLException
	{
		String hql ="from PamEquip where pk_equip =:pk_equip";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pk_equip",pk_equip);
		List<PamEquip> PamEquips = query.list();
		DBUtil.closeSession();
		if(PamEquips.size()==0)
		{
			return null;
		}else
		{
			pamequip = PamEquips.get(0);
			return pamequip;
		}			
	}
	
	public List<Map> groupByPk(String pkMandept){
		String hql = "select new map(equipName,count(*)) from PamEquip where pkMandept = :pkMandept group by equipName";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("pkMandept",pkMandept);
		List<Map> mapinfos = query.list();
	//	Object object = query.list();
		DBUtil.closeSession();		
		return mapinfos;
	} 
	
	public PamEquip GetByCode(String equipcode) throws SQLException
	{
		String hql ="from PamEquip where equipCode =:equipcode and dr=0";
		session =DBUtil.currentSession();
		Query query =session.createQuery(hql);
		query.setParameter("equipcode",equipcode);
		List<PamEquip> PamEquips = query.list();
		DBUtil.closeSession();
		if(PamEquips.size()==0)
		{
			return null;
		}else
		{
			PamEquip pamequip = PamEquips.get(0);
			return pamequip;
		}			
	}
	
	public void PrintZxing(String equipcode) throws SQLException, WriterException, IOException
	{
		pamequip = GetByCode(equipcode);
		if(pamequip !=null)
		{
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			 BufferedImage image = null;
			
		     Map hints = new HashMap();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(pamequip.getPkEquip(), BarcodeFormat.QR_CODE, 100, 100,hints);
		     image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		     
		     BufferedImage image2 = new BufferedImage(100,15,BufferedImage.TYPE_INT_RGB);
		     Graphics2D g = image2.createGraphics();
			 g.setBackground(Color.WHITE);
			 g.clearRect(0, 0, 100, 15);
			 g.setColor(Color.black);
			 g.setFont(new Font("Serif",Font.ITALIC,12));
			 
			 g.drawString(pamequip.getEquipCode(), 12, 10);
			 
			 
			 File file = new File("d:/abc1/",pamequip.getEquipCode()+".jpg");	
		     
			 Merge2.yPic(image2, image, file);
		     

	//	     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		     
		  
		//	 ImageIO.write(image,"JPG",file1);
		   //  Desktop.getDesktop().open(file);
		}
		}

}