package com.nts.teststruts.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillCodeUtil {

	public static String  getbillcode(){
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		return format.format(date);
	}
}
