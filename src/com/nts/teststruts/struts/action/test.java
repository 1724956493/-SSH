package com.nts.teststruts.struts.action;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
	static Map<String, Object> demo = new HashMap<String, Object>();
	static Map<String, Object> demo1 = new HashMap<String, Object>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * demo1.put("content","a"); demo.put("touser", "a");
		 * demo.put("toparty", "a"); demo.put("agentid", "a"); demo.put("text",
		 * demo1); demo.put("msgtype", "a");
		 * System.out.println(JSON.toJSONString(demo)); ;
		 */
		/*
		 * String m=JSON.toJSONStringWithDateFormat("success",
		 * "yyyy-MM-dd HH:mm:ss"); System.out.println(m);
		 */
		// String m = "";

		// m=m.indexOf(".");
		/*
		 * int index=m.indexOf("."); if(index!=-1){
		 * m=m.substring(index+1,m.length()).trim();
		 * 
		 * }
		 */

		String from = " {sm_user} ,{sm_userandclerk b}, {bd_psnbasdoc c},{bd_psndoc d},{bd_deptdoc e}";

		// String[] arr1= from.split("}");

		String line = "{(This) {{{{order}}} {was} placed for QT3000! OK?";
		// String pattern = "(?=\\{)(.+?)(?=\\})";
		String pattern = "(?<=\\{).+?";
		// 创建 Pattern 对象
		// 忽略大小写的写法 Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Pattern r = Pattern.compile(pattern);

		// 现在创建 matcher 对象
		/*	Matcher matcher = r.matcher(line);
		
		
			while (matcher.find()) {
				  System.out.println("Found the text \"" + matcher.group()
		      + "\" starting at " + matcher.start()
		      + " index and ending at index " + matcher.end());
				
		
			}
			Pattern   pattern1 = Pattern.compile("\\W");
		    String[] words = pattern1.split("one@two#three:four$five");
		    for (String s : words) {
		        System.out.println("Split using Pattern.split(): " + s);
		    }
		
		    // using Matcher.replaceFirst() and replaceAll() methods
		    pattern1 = Pattern.compile("1*2");
		    matcher = pattern1.matcher("11234512678");
		    System.out.println("Using replaceAll: " + matcher.replaceAll("_"));
		    System.out.println("Using replaceFirst: " + matcher.replaceFirst("_"));
		}  */
		String r2 = "(.*)(\\.)(\\s*)(\\*)(.*)";// 判定字符串中是否拥有.*这个字符，.与*中有任意空格"a.ss*,r.d *,sum(*),p.equip_code,p.model,p.spec,p.def3";
		String regex2 = "(.*)([\\)\\s+])(as)(\\s+)(.*)";
		String t = "bbbas )   ooo";

		System.out.println(t.lastIndexOf(")") >= 0 ? t.substring(t.lastIndexOf(")")) : t);
		// System.out.println(t.matches(regex2) );
		/*String r3="(.*)(\\s+)(as)(\\s+)(.*)";//判定字符串中是否 有 as 这个字符并且左右都有空格
		// System.out.println(t.split("as")[0]);
		  Set set = new HashSet();
		    set.add(new Date());   //向列表中添加数据
		
		    set.add("apple");    //向列表中添加数据
		
		    set.add(new Socket());   //向列表中添加数据
		
		    boolean contains = set.contains("apple");
		
		    if (contains) {
		
		   System.out.println("Set集合包含字符串apple");
		
		    } else {
		
		   System.out.println("Set集合不包含字符串apple");
		
		    }*/
		/*List<String> lst1 = new ArrayList<String>();
		List<String> lst2 = new ArrayList<String>();
		lst1.add("北京");
		lst1.add("上海");
		lst2.add("南京");
		lst2.add("重庆");
		lst1.addAll(lst2);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			System.out.println(listMap.size());*/

		String string1 = "sdfsdf,$sdlfksd ,$(a,$a,($ss,dd)),$(dd,aa),jjjj";
		
//		String[] str = string1.split(",(?![^(\\$)])");
//		(?=[^\\$\\(|$])")
		String[] str = string1.split(",+(?=[^\\)]*(\\$\\(|$))");
		for (String s : str) {
			System.out.println(s);
		}
System.out.println("ssssff".contains("sf"));
	}
}
