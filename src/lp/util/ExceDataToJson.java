package lp.util;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExceDataToJson {

	public String  result(String filePath) {
		// TODO Auto-generated method stub

		// 读取Excel表生成Json文件
		ArrayList<head> list =new ArrayList<head>();

		head gethead = ReadExcel(filePath);
		
		String getJson = JSON.toJSONStringWithDateFormat(gethead,
				"yyyy-MM-dd HH:mm:ss");
		//System.out.println(getJson);
		return getJson;
	}

	private head ReadExcel(String filePath) {
		head head_detail = new head();

		try {
			Workbook wb = Workbook.getWorkbook(new File(filePath));
			Sheet mSheet = wb.getSheet(0);
			int row = mSheet.getRows();
			
			ArrayList<body> bodyArrayList = new ArrayList<body>();
			head_detail.setName(mSheet.getCell(0, 0).getContents());

			for (int i =3; i < row; i++) {
				int cols = 4;
				body bodydetail = new body();

				for (int j = 1; j < cols; j++) {

					Cell temp = mSheet.getCell(j, i);
					String content = temp.getContents() + "";
					switch (j) {
					case 0:
						break;
					case 1:
						bodydetail.setCheck_point(content);
						break;
					case 2:
						if (content.length()==0)
							bodydetail.setCheck_need("完好");
						else
							bodydetail.setCheck_need(content);
						break;
					case 3:
						if (content.length()==0)
							bodydetail.setCheck_result("达标或不达标");
						else
							bodydetail.setCheck_result(content);
						break;
					default:
						break;
					}
				}
				
				bodyArrayList.add(bodydetail);
			}
			head_detail.setCheckdetail(bodyArrayList);
			
			wb.close();
			return head_detail;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

class head {
	String name;
	List<body> checkdetail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<body> getCheckdetail() {
		return checkdetail;
	}

	public void setCheckdetail(List<body> checkdetail) {
		this.checkdetail = checkdetail;
	}

}

class body {
	String check_point;
	String check_need;
	String check_result;

	public String getCheck_point() {
		return check_point;
	}

	public void setCheck_point(String check_point) {
		this.check_point = check_point;
	}

	public String getCheck_need() {
		return check_need;
	}

	public void setCheck_need(String check_need) {
		this.check_need = check_need;
	}

	public String getCheck_result() {
		return check_result;
	}

	public void setCheck_result(String check_result) {
		this.check_result = check_result;
	}

}
