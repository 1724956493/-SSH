package com.nts.teststruts.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlDBUtil {
	
	public static Connection getConnection() {  
		Connection conn = null;
		try
	    {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	      //  System.out.println("数据库驱动程序注册成功");
	        String url = "jdbc:sqlserver://172.16.168.100:1433;databaseName=hrdb";
	        String user = "sa";
	        String password = "ncsykt";
	        conn = DriverManager.getConnection(url, user, password);
	     //   System.out.println("数据库连接成功");
	     }
			catch (ClassNotFoundException e)
		    {
		        e.printStackTrace();
		        System.out.println("数据库连接失败");
		    }
			catch (SQLException e)
		    {
		        e.printStackTrace();
		        System.out.println("数据库连接失败");
		    }
			return conn;		
		  }
		
	/** 
     * 鍏抽棴     PreparedStatement锛堥澶勭悊鎵ц璇彞锛�鐩殑锛氬彲浠ラ槻姝QL娉ㄥ叆銆佸湪鐗瑰畾鐨勯┍鍔ㄦ暟鎹簱涓嬬浉瀵规晥鐜囪楂�涓嶇粷瀵�銆佷笉闇�
 
棰戠箒缂栬瘧.鍥犱负宸茬粡棰勫姞杞戒簡 
     * @param pstmt 
     */ 
	    public static void close(PreparedStatement pstmt) {  
	        if (pstmt != null) {  
	            try {  
	                pstmt.close();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    
	    
	    /** 
	     * 鍏抽棴鎵ц璇彞 
	     * @param stmt 
	     */ 
	    public static void close(Statement stmt) {  
	        if (stmt != null) {  
	            try {  
	                stmt.close();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }   
	
	    /** 
	     * 鍏抽棴杩炴帴 
	     * @param conn 
	     */ 
	    public static void close(Connection conn) {  
	        if (conn != null) {  
	            try {  
	                conn.close();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }     
	        }  
	    }  
	
	    /** 
	     * 鍥炴粴浜嬪姟 
	     * @param conn 
	     */ 
	    public static void rollback(Connection conn) {  
	        if (conn != null) {  
	            try {  
	                conn.rollback();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    } 
	    
	    /** 
	     * 鎻愪氦浜嬪姟 
	     * @param conn 
	     */ 
	    public static void commit(Connection conn) {  
	        if (conn != null) {  
	            try {  
	                conn.commit();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    
	    /** 
	     * 鍏抽棴鏁版嵁搴撶粨鏋滈泦鐨勬暟鎹〃 
	     * @param rs 
	     */ 
	    public static void close(ResultSet rs) {  
	        if (rs != null) {  
	            try {  
	                rs.close();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    } 
	    
	    /** 
	     * 鑷姩鎻愪氦浜嬪姟 
	     * @param conn 
	     * @param autoCommit 
	     */ 
	    public static void setAutoCommit(Connection conn, boolean autoCommit) {  
	        if (conn != null) {  
	            try {  
	                conn.setAutoCommit(autoCommit);  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    } 
	
	/*public void testOracle(String sql)
	{
	    Connection con = null;// 鍒涘缓涓�釜鏁版嵁搴撹繛鎺�
	    PreparedStatement pre = null;// 鍒涘缓棰勭紪璇戣鍙ュ璞★紝涓�埇閮芥槸鐢ㄨ繖涓�涓嶇敤Statement
	    ResultSet result = null;// 鍒涘缓涓�釜缁撴灉闆嗗璞�
	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// 鍔犺浇Oracle椹卞姩绋嬪簭
	        System.out.println("寮�灏濊瘯杩炴帴鏁版嵁搴擄紒");
	        String url = "jdbc:oracle:" + "thin:@xf-test1:1521:orcl";// 127.0.0.1鏄湰鏈哄湴鍧�紝XE鏄簿绠�増Oracle鐨勯粯璁ゆ暟鎹簱鍚�
	        String user = "nc58";// 鐢ㄦ埛鍚�绯荤粺榛樿鐨勮处鎴峰悕
	        String password = "sanyuan17";// 浣犲畨瑁呮椂閫夎缃殑瀵嗙爜
	        con = DriverManager.getConnection(url, user, password);// 鑾峰彇杩炴帴
	        System.out.println("杩炴帴鎴愬姛锛�);
	       // 棰勭紪璇戣鍙ワ紝鈥滐紵鈥濅唬琛ㄥ弬鏁�
	        // pre = con.prepareStatement(sql); // 瀹炰緥鍖栭缂栬瘧璇彞
	        // pre.setString(1, "BGYP003");// 璁剧疆鍙傛暟锛屽墠闈㈢殑1琛ㄧず鍙傛暟鐨勭储寮曪紝鑰屼笉鏄〃涓垪鍚嶇殑绱㈠紩
	      // result = pre.executeQuery();// 鎵ц鏌ヨ锛屾敞鎰忔嫭鍙蜂腑涓嶉渶瑕佸啀鍔犲弬鏁�
	        Statement stmt=con.createStatement();
	        result = stmt.executeQuery(sql);
	        while (result.next())
	            // 褰撶粨鏋滈泦涓嶄负绌烘椂
	            System.out.println("閮ㄩ棬鍙�" + result.getString("invcode") + "閮ㄩ棬鍚嶇О:"
	                    + result.getString("invname")+"閮ㄩ棬浣嶇疆"+result.getString("invtype"));
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            // 閫愪竴灏嗕笂闈㈢殑鍑犱釜瀵硅薄鍏抽棴锛屽洜涓轰笉鍏抽棴鐨勮瘽浼氬奖鍝嶆�鑳姐�骞朵笖鍗犵敤璧勬簮
	            // 娉ㄦ剰鍏抽棴鐨勯『搴忥紝鏈�悗浣跨敤鐨勬渶鍏堝叧闂�
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (con != null)
	                con.close();
	            System.out.println("鏁版嵁搴撹繛鎺ュ凡鍏抽棴锛�);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
*/
}
