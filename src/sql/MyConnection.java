package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
	static { //static 초기화 블럭
			 //MyConnection.getConnection처럼 static으로 사용하니까
			 //static 메서드보다 먼저 초기화되려면 생성자는 소용이 없다.
		//JDBC드라이버 로드 (JVM에서 한번만 해도됨)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("JDBC드라이버 로드 성공");	
	}

	public static Connection getConnection() throws Exception{
		String url = "jdbc:oracle:thin:@13.125.126.131:1521:xe";
		String user = "kosta210";
		String password = "tigerOracle6";
		Connection con = DriverManager.getConnection(url, user, password);
		return con;
	}
	
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(con, stmt);
	}
	
	public static void close(Connection con, Statement stmt) { 
		// PreparedStatement는 Statement의 자식이라 업캐스팅 가능
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(con);
	}
	
	public static void close(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
