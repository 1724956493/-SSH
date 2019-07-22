package lp.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nts.teststruts.util.DBUtil;

public class HibernateBySQL {
	static Session session;

	public static Boolean Sql(String sql) {
		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Query query = null;
		try {
			if (sql.contains("where")) {
				query = session.createSQLQuery(sql);
				query.executeUpdate();
				tx.commit(); // 关闭session
				return true;
			} else {
				throw new Exception("语法错误,缺少条件！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static Map<String, Object> Update(String tableName, String set, String where) {
		Map<String, Object> returnStatus = new HashMap<String, Object>();
		returnStatus.put("success", true);
		returnStatus.put("msg", "");
		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Query query = null;
		String sql = "update " + tableName + " " + set + " " + where;
		try {
			if (sql.contains("where") && sql.contains("set")) {
				query = session.createSQLQuery(sql);
				query.executeUpdate();
				tx.commit(); // 关闭session
				return returnStatus;
			} else {
				returnStatus.put("success", false);
				returnStatus.put("msg", "语法错误,缺少条件！");
				return returnStatus;
			}
		} catch (Exception e) {
			// TODO: handle exception
			returnStatus.put("success", false);
			returnStatus.put("msg", e.getMessage());
			return returnStatus;
		}
	}

	public static Map<String, Object> UpdateOrInsert(String tableName, JSONArray data) {
		// 返回-2 语句错误，返回-1时 ,执行正确。返回正数,为错误的行。
		Map<String, Object> returnStatus = new HashMap<String, Object>();
		returnStatus.put("success", true);
		returnStatus.put("msg", "");

		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Query query = null;
		int rowId = -1;
		ArrayList<String> allColumn = new ArrayList<String>();
		// data [{SetInsertValue:{},Condition:{}}]
		try {
			// 首先获取表结构 表名需要大写
			String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('" + tableName
					+ "')) order by column_id";
			query = session.createSQLQuery(sql_column);
			for (Object o : query.list()) {
				allColumn.add(((String) o).toLowerCase());
			}
			// 将原始JSON中的key与表列名相同的数据提取出来赋值给新的JSON
			// 达到所插入的数据都是 符合表结构

			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				rowId++;
				JSONObject InsertContextMid = new JSONObject();
				JSONObject jsonObject = (JSONObject) iterator.next();
				JSONObject SetInsertValue = (JSONObject) jsonObject.get("SetInsertValue");
				JSONObject Condition = (JSONObject) jsonObject.get("Condition");
				for (String key : allColumn) {
					if (SetInsertValue.get(key) != null) {
						InsertContextMid.put(key, SetInsertValue.get(key));
					}
				}

				String sql = SqlCreate.UpdateOrInsertSQL(tableName, InsertContextMid, Condition);

				if (sql == null) { 
					returnStatus.put("success", false);
					returnStatus.put("msg", "sql语法错误,条件不能为空");
					return returnStatus;
				} 
				query = session.createSQLQuery(sql);
				query.executeUpdate();

			}
			tx.commit(); // 关闭session
			return returnStatus;
		} catch (Exception e) {
			returnStatus.put("success", false);
			returnStatus.put("msg",rowId);
			return returnStatus;
		} finally {
			session.close();
		}

	}

	public static Map<String, Object> UpdateOrInsert(String tableName, JSONObject SetInsertValue, JSONObject Condition) {
		Map<String, Object> returnStatus = new HashMap<String, Object>();
		returnStatus.put("success", true);
		returnStatus.put("msg", "");
		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Query query = null;
		ArrayList<String> allColumn = new ArrayList<String>();
		JSONObject InsertContextMid = new JSONObject();
		try {
			// 首先获取表结构 表名需要大写
			String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('" + tableName
					+ "')) order by column_id";
			query = session.createSQLQuery(sql_column);
			for (Object o : query.list()) {
				allColumn.add(((String) o).toLowerCase());
			}
			// 将原始JSON中的key与表列名相同的数据提取出来赋值给新的JSON
			// 达到所插入的数据都是 符合表结构
			for (String key : allColumn) {
				if (SetInsertValue.get(key) != null) {
					InsertContextMid.put(key, SetInsertValue.get(key));
				}
			}

			String sql = SqlCreate.UpdateOrInsertSQL(tableName, InsertContextMid, Condition);

			if (sql == null) {
				returnStatus.put("success", false);
				returnStatus.put("msg", "sql语法错误,条件不能为空");
				return returnStatus;
			}
			query = session.createSQLQuery(sql);
			query.executeUpdate();
			tx.commit(); // 关闭session
			return returnStatus;
		} catch (Exception e) {
//			System.out.println(e);
			returnStatus.put("success", false);
			returnStatus.put("msg", e.getMessage());
			return returnStatus;
		} finally {
			session.close();
		}

	}

	public static Map<String, Object> Update(String tableName, JSONObject SetCondition, String where) {
		Map<String, Object> returnStatus = new HashMap<String, Object>();
		returnStatus.put("success", true);
		returnStatus.put("msg", "");
		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Query query = null;
		ArrayList<String> allColumn = new ArrayList<String>();
		JSONObject InsertContextMid = new JSONObject();
		try {
			// 首先获取表结构 表名需要大写
			String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('" + tableName
					+ "')) order by column_id";
			query = session.createSQLQuery(sql_column);
			for (Object o : query.list()) {
				allColumn.add(((String) o).toLowerCase());
			}
			// 将原始JSON中的key与表列名相同的数据提取出来赋值给新的JSON
			// 达到所插入的数据都是 符合表结构
			for (String key : allColumn) {
				if (SetCondition.get(key) != null) {
					InsertContextMid.put(key, SetCondition.get(key));
				}
			}
			String sql = SqlCreate.UpdateSQL(tableName, InsertContextMid, where);
			query = session.createSQLQuery(sql);
			query.executeUpdate();
			tx.commit(); // 关闭session
			return returnStatus;

		} catch (Exception e) {
			returnStatus.put("success", false);
			returnStatus.put("msg", e.getMessage());
			return returnStatus;
		} finally {
			session.close();
		}

	}

	/**
	 * @功能：查询数据并将结果以ListMap返回；list存每条数据集合，Map存表字段和对应的每个数据
	 * @select 表的字段，
	 * @只是 * 查询表所有字段，select * from {tabA a},{tabB b} 每个字段 的别名都是表的别名_字段 a_x，b_x
	 * @如果只是需要某几张表的字段，则直接书写
	 * @如果 一张表的全部字段+其他包的某个字段，则需要显示全部字段的表别名.*即可 select a.*,b.xx from {tabA a},{tabB b} where a.x=b.x 显示 tabA的全部字段，和字段 b.xx
	 * @如果想自己指定 别名，则 select a.x as s,b.xx from
	 * @from 查询语句 from后面的部分 表名 和别名都要放在{}里from {tabA a},{tabB b}
	 * @wher 条件语句 where 需要自己手动加
	 * @返回Map数据中,没有字段重名的则直接返回字段名，字段重名的则重名字段前加表别名
	 */

	public static List<Map<String, Object>> QueryTablesSample(String select, String from, String where) {
		from = from.trim();
		select = select.trim();
		try {
			session = DBUtil.currentSession();
			// Transaction tx = session.beginTransaction();
			Query query = null;

			// 新建一个List 用于字段 表别名.字段名 as 表别名_字段名
			List<String> allColumn = new ArrayList<String>();
			List<String> allColumnMap = new ArrayList<String>();
			// 首先 格式化from 并得出每张表，和 表别名
			String rule = "(?<=\\{)(.+?)(?=\\})";
			// 忽略大小写的写法 Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Pattern pattern = Pattern.compile(rule);
			// 现在创建 matcher 对象
			Matcher match = pattern.matcher(from);
			// 找出{}内的表名和别名
			Map<String, String> tableMap = new HashMap<String, String>();
			String table = "";
			while (match.find()) {
				String a = match.group().trim();
				if (a.contains(" ")) {
					String a1 = a.substring(0, a.indexOf(" "));
					String a2 = a.substring(a.indexOf(" "), a.length());
					tableMap.put(a1, a2.trim());
				} else
					table = a;

			}
			if ((table.length() > 0 && tableMap.size() > 0) || select.length() == 0
					|| (table.length() == 0 && tableMap.size() == 0)) {
				throw new Exception("语法错误,请检查sql代码，代码规范，表名需在{}内");
			}
			if (select.equals("*")) {
				if (table.length() > 0) {
					String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('" + table
							+ "')) order by column_id";
					// 执行sql
					query = session.createSQLQuery(sql_column);

					for (Object o : query.list()) {
						allColumn.add(((String) o).toLowerCase());
						allColumnMap.add(((String) o).toLowerCase());
					}
				} else {
					// 多张表
					for (Map.Entry<String, String> entry : tableMap.entrySet()) {
						String tableName = entry.getKey().trim();
						String tableAlias = entry.getValue().trim();
						String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('"
								+ tableName + "')) order by column_id";
						// 执行sql
						query = session.createSQLQuery(sql_column);
						for (Object o : query.list()) {
							String a = (String) o;
							String add = "";
							add = tableAlias + "." + a + " as " + tableAlias + "_" + a;
							allColumn.add(add.toLowerCase());
							allColumnMap.add((tableAlias + "_" + a).toLowerCase());
						}

					}

				}

			} else {
				String regex = "(.*)(\\.)(\\s*)(\\*)(.*)";// 判定字符串中是否拥有.*这个字符，.与*中有任意空格
				String regex2 = "(.*)([\\)\\s+])(as)(\\s+)(.*)";// 判定字符串中是否 有 as
				// 这个字符并且左右都有空格
				if (!select.matches(regex)) {
					String[] b = null;
					if (select.contains("$(")) {
						b = select.split(",+(?=[^\\)]*(\\$\\(|$))");
					} else {
						b = select.split(",");
					}
					for (Object o : b) {
						String temp = (String) o;
						if (temp.indexOf("$(") != -1) {
							temp = temp.replace("$(", "(");
						}
						if (temp.matches(regex2)) {
							allColumn.add(temp);
							allColumnMap.add((temp.lastIndexOf(")") >= 0 ? temp.substring(temp.lastIndexOf(")")) : temp)
									.split("[\\)\\s+](as)(\\s+)")[1].trim());
						} else {
							allColumn.add(temp + " as " + temp.replace(".", "_"));
							temp = temp.replace(".", "_");
							allColumnMap.add(temp);
						}
					}

				} else {
					String[] b = null;
					if (select.contains("$(")) {
						b = select.split(",+(?=[^\\)]*(\\$\\(|$))");
					} else {
						b = select.split(",");
					}
					for (Object o : b) {
						String temp = (String) o;
						if (temp.indexOf("$(") != -1) {
							temp = temp.replace("$(", "(");
						}
						if (!temp.matches(regex)) {
							if (temp.matches(regex2)) {
								allColumn.add(temp);
								allColumnMap
										.add((temp.lastIndexOf(")") >= 0 ? temp.substring(temp.lastIndexOf(")")) : temp)
												.split("[\\)\\s+](as)(\\s+)")[1].trim());

							} else {
								allColumn.add(temp + " as " + temp.replace(".", "_"));
								temp = temp.replace(".", "_");
								allColumnMap.add(temp);
							}
						} else {
							temp = temp.replace("*", "").replace(".", "").trim();
							for (Map.Entry<String, String> entry : tableMap.entrySet()) {
								String tableName = entry.getKey().trim();
								String tableAlias = entry.getValue().trim();
								if (tableAlias.equals(temp)) {
									// 首先获取表结构 表名需要大写
									String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('"
											+ tableName + "')) order by column_id";
									// 执行sql
									query = session.createSQLQuery(sql_column);
									// 将得到的字段 加上 表别名
									for (Object m : query.list()) {
										String a = (String) m;
										// 表别名.字段名 as 表别名_字段名
										String add = tableAlias + "." + a + " as " + tableAlias + "_" + a;
										allColumn.add(add.toLowerCase());
										allColumnMap.add((tableAlias + "_" + a).toLowerCase());
									}

								}
							}

						}
					}
				}
			}

			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

			String selectColumToString = allColumn.toString();

			String selectInString = selectColumToString.substring(1, selectColumToString.length() - 1);
			// 查询结果
			String sql = null;
			// 格式化from
			from = from.replace("{", "").replace("}", "");
			// 判断是否拥有条件
			if (where != null && where.indexOf("where") != -1)
				sql = "select " + selectInString + " from " + from + " " + where;
			else
				sql = "select " + selectInString + " from " + from;
			// System.out.println(sql);
			query = session.createSQLQuery(sql);
			List list = query.list();
			if (tableMap.size() > 0) {
				// 一张或者多张，且有重名tableMap
				Map<String, String> inner = new HashMap<String, String>();
				Set<String> set = new HashSet<String>();
				for (Map.Entry<String, String> entry : tableMap.entrySet()) {
					String tableName = entry.getKey().trim();
					String tableAlias = entry.getValue().trim();
					inner.put(tableAlias, tableName);
				}
				for (int i = 0; i < allColumnMap.size(); i++) {
					String item = allColumnMap.get(i);
					// 将别名换成表名
					String[] arr = item.split("_");
					String tabName = inner.get(arr[0].trim());
					if (arr.length > 1) {
						// 判断第一个是否是表别名
						if (tabName != null) {
							String col = item.substring(item.indexOf("_") + 1);
							// 是否包含重名
							boolean contains = set.contains(col);
							if (contains) {
								allColumnMap.set(i, tabName + "_" + col);
								set.add(tabName + "_" + col);
							} else {
								allColumnMap.set(i, col);
								set.add(col);
							}
						} else {
							set.add(item);
						}
					} else {
						set.add(item);
					}
				}
			}

			// 封装Json
			for (Object h : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (allColumnMap.size() > 1) {
					Object[] o = (Object[]) h;
					for (int i = 0; i < o.length; i++) {
						String isnull = o[i] + "";
						String colum = allColumnMap.get(i).toString();
						colum = colum.trim();
						if (isnull.contains("null"))
							map.put(colum, "");
						else
							map.put(colum, isnull);
					}
					listMap.add(map);
				}
				if (allColumnMap.size() == 1) {
					String isnull = h + "";
					String colum = allColumnMap.get(0).toString();
					colum = colum.trim();
					if (isnull.contains("null"))
						map.put(colum, "");
					else
						map.put(colum, isnull);

					listMap.add(map);
				}
			}
			return listMap;
		} catch (Exception e) {
			if (e.getMessage().contains("Index"))
				System.out.println("语法错误,请检查sql代码,select中,一个列带有$(,其他列的(前面都要加$");
			System.out.println(e.getMessage());
			return new ArrayList<Map<String, Object>>();
		} finally {
			session.close();
		}
	}

	/**
	 * @只需保证插入的json中包含表的结构不需要完全等同
	 */
	public static Map<String, Object> Insert(String tableName, JSONArray InsertContext) {
		Map<String, Object> returnStatus = new HashMap<String, Object>();
		returnStatus.put("success", true);
		returnStatus.put("msg", "");
		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		ArrayList<String> allColumn = new ArrayList<String>();
		Query query = null;
		try {
			String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('" + tableName
					+ "')) order by column_id";
			query = session.createSQLQuery(sql_column);
			for (Object o : query.list()) {
				allColumn.add(((String) o).toLowerCase());
			}
			for (Iterator iterator = InsertContext.iterator(); iterator.hasNext();) {
				JSONObject jsonObject = (JSONObject) iterator.next();
				JSONObject InsertContextMid = new JSONObject();
				for (String key : allColumn) {
					if (jsonObject.get(key) != null) {
						InsertContextMid.put(key, jsonObject.get(key));
					}
				}
				String sql = SqlCreate.InsertSQL(tableName, InsertContextMid);
				query = session.createSQLQuery(sql);
				query.executeUpdate();
			}
			tx.commit(); // 关闭session
			return returnStatus;
		} catch (Exception e) {
			returnStatus.put("success", false);
			returnStatus.put("msg", e.getMessage());
			return returnStatus;
		} finally {
			session.close();
		}
	}

	public static Map<String, Object> Insert(String tableName, JSONObject InsertContext) {
		Map<String, Object> returnStatus = new HashMap<String, Object>();
		returnStatus.put("success", true);
		returnStatus.put("msg", "");

		session = DBUtil.currentSession();
		Transaction tx = session.beginTransaction();
		ArrayList<String> allColumn = new ArrayList<String>();
		JSONObject InsertContextMid = new JSONObject();
		Query query = null;
		try {
			String sql_column = "select column_name from all_tab_columns where table_name= upper(trim('" + tableName
					+ "')) order by column_id";
			query = session.createSQLQuery(sql_column);
			for (Object o : query.list()) {
				allColumn.add(((String) o).toLowerCase());
			}
			for (String key : allColumn) {
				if (InsertContext.get(key) != null) {
					InsertContextMid.put(key, InsertContext.get(key));
				}
			}
			String sql = SqlCreate.InsertSQL(tableName, InsertContextMid);
			query = session.createSQLQuery(sql);
			query.executeUpdate();
			tx.commit(); // 关闭session
			return returnStatus;
		} catch (Exception e) {
			returnStatus.put("success", false);
			returnStatus.put("msg", e.getMessage());
			return returnStatus;
		} finally {
			session.close();
		}
	}

	// JDBC
	public static List<Map<String, Object>> ResultSetToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<String> colNameList = new ArrayList<String>();
		for (int i = 0; i < colCount; i++) {
			colNameList.add(rsmd.getColumnName(i + 1));
		}
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < colCount; i++) {
				String isnull = rs.getString(colNameList.get(i).toString()) + "";
				String colum = colNameList.get(i).toString().toLowerCase();
				colum = colum.trim();
				if (isnull.contains("null"))
					map.put(colum, "");
				else
					map.put(colum, isnull.toLowerCase());
			}
			results.add(map);
		}
		return results;
	}
}
