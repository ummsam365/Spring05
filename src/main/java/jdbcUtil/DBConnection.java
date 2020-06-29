package jdbcUtil;

import java.sql.*;

public class DBConnection {
	// Connection 생성 method
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl"; 
			// oracle 11g XE version SID :  XE
			// oracle 11g re version SID :  orcl
			return DriverManager.getConnection(url,"system","oracle");
		} catch (Exception e) {
			System.out.println("getConnection Exception =>"+e.toString());
			System.out.println("** DB Connection 실패 ~~~ ");
			return null;
		}
	} // getConnection()
	
	// Connection 종료 method
	// => 사용한 JDBC 객체들을 종료
	// => 생성의 역순으로 해야 함
	public static void close(ResultSet rs,PreparedStatement pst,Statement st,Connection cn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (st != null) {
				st.close();
			}
			if (cn != null) {
				cn.close();
			}
			
		} catch (Exception e) {
			System.out.println("close Exception => "+e.toString());
		}
	} // close()
} // class
