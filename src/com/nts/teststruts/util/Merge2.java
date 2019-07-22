package com.nts.teststruts.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Merge2 {

	 public static void yPic(BufferedImage imageFirst,BufferedImage imageSecond,File outFile){//纵向处理图片  
	        try {  
	            /* 1 读取第一张图片*/    
	            int width = imageFirst.getWidth();// 图片宽度  
	            int height = imageFirst.getHeight();// 图片高度  
	            int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB  
	            imageArrayFirst = imageFirst.getRGB(0, 0, width, height, imageArrayFirst, 0, width);  
	  
	            /* 1 对第二张图片做相同的处理 */   
	            int width2 = imageSecond.getWidth();// 图片宽度  
	            int height2 = imageSecond.getHeight();// 图片高度  
	            int[] imageArraySecond = new int[width2 * height2];  
	            imageArraySecond = imageSecond.getRGB(0, 0, width2, height2, imageArraySecond, 0, width);  
	              
	            // 生成新图片   
	            BufferedImage imageResult = new BufferedImage(width, height+height2,BufferedImage.TYPE_INT_RGB);  
	            imageResult.setRGB(0, 0, width, height, imageArrayFirst, 0, width);// 设置上半部分的RGB  
	            imageResult.setRGB(0, height, width2, height2, imageArraySecond, 0, width);// 设置下半部分的RGB  
	            ImageIO.write(imageResult, "jpg", outFile);// 写图片  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
