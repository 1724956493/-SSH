package lp.util;

import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

/**
 * 当插入数据的值为所执行的函数或sql语句时 请在前面加上‘$’符(插入是不加单引号)
 */

public class SqlCreate {
	/**
	 * 1.作用：生成可执行sql语句 2.功能：当数据库有符合条件的数据时，则更新，否则插入新数据 3.参数说明： tableName：表名 SetInsertValue：更新或插入的数据,以JSONObject形式存储,键key对应表中的字段名 不可为空 Condition：需要更新时的赛选条件 ,以JSONObject形式存储,键key字段名,值value筛选数据 4.改语句更新条件不能做区间赛选
	 */

	public static String UpdateOrInsertSQL(String tableName, JSONObject SetInsertValue, JSONObject Condition) {
		String sql = null;
		String usingTable = "";
		String where = "";
		String cols = "";
		String values = "";
		
		if (SetInsertValue != null && Condition != null && Condition.size() > 0 && SetInsertValue.size() > 0) {
			for (Entry<String, Object> entry : Condition.entrySet()) {
				String getValue = entry.getValue() + "";
				if (getValue.length() > 0 && getValue.charAt(0) == '$') {
					usingTable += getValue.substring(1) +" "+entry.getKey() + ",";
				} else
					usingTable += "'" + getValue + "'  " + entry.getKey() + ",";

				where += "a." + entry.getKey() + "=" + "b." + entry.getKey() + " and ";
			}
			usingTable = usingTable.substring(0, usingTable.length() - 1);
			where = where.substring(0, where.lastIndexOf("and"));
			sql = "merge into ";
			sql += tableName + " a ";
			sql += " using (select " + usingTable + " from dual) b ";
			sql += " on (" + where + ")  ";
			sql += " when matched then update set ";
			for (Entry<String, Object> entry : SetInsertValue.entrySet()) {
				String value = (entry.getValue() + "").trim();
				cols += entry.getKey() + ",";
				if (value.contains("null"))
					value = "";
				if (value.length() > 0 && value.charAt(0) == '$') {
					values += value.substring(1) + ",";
				} else
					values += "'" + value + "',";
				// 更新时条件不能更新 
				if (Condition.getString(entry.getKey())==null) {
					if (value.length() > 0 && value.charAt(0) == '$')
						sql += entry.getKey() + " = " + value.substring(1) + ",";
					else
						sql += entry.getKey() + " = '" + entry.getValue() + "',";
				}

			}
			cols = cols.substring(0, cols.length() - 1);
			values = values.substring(0, values.length() - 1);
			sql = sql.substring(0, sql.length() - 1) + " ";
			sql += "when  not matched then insert (";
			sql += cols;
			sql += ") values(" + values + ")";
			// System.out.println(sql);
			return sql;
		} else
			return null;

	}

	/**
	 * 1.作用：生成可执行sql语句 2.功能：更新数据
	 */
	public static String UpdateSQL(String tableName, JSONObject SetCondition, String where) {
		String sql = null;
		if (SetCondition != null && SetCondition.size() > 0) {
			sql = "update " + tableName + " set ";
			for (Entry<String, Object> entry : SetCondition.entrySet()) {
				String value = (entry.getValue() + "").trim();
				if (value.length() > 0 && value.charAt(0) == '$')
					sql += entry.getKey() + " = " + value.substring(1) + ",";
				else
					sql += entry.getKey() + " = '" + entry.getValue() + "',";

			}
			if (where != null)
				sql = sql.substring(0, sql.length() - 1) + " " + where;
			else
				sql = sql.substring(0, sql.length() - 1);
			return sql;
		} else {
			return null;
		}

	}

	/**
	 * 作用：生成可执行sql语句； 功能：插入数据 如果数据是函数或语句，则在前面加$
	 */
	public static String InsertSQL(String tableName, JSONObject InsertContext) {
		if (InsertContext != null && InsertContext.size() > 0) {
			String cols = "";
			String values = "";
			for (Entry<String, Object> entry : InsertContext.entrySet()) {
				cols += entry.getKey() + ",";
				String value = (entry.getValue() + "").trim();
				if (value.contains("null"))
					value = "";
				if (value.length() > 0 && value.charAt(0) == '$') {
					values += value.substring(1) + ",";
				} else
					values += "'" + value + "',";
			}
			cols = cols.substring(0, cols.length() - 1);
			values = values.substring(0, values.length() - 1);
			String sql = "insert into " + tableName + " (" + cols + ") values (" + values + ")";
			return sql;
		} else {
			return null;
		}
	}

}
