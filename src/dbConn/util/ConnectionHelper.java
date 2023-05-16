package dbConn.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

	public static Connection getConnection(String dsn) { 
		Connection conn = null;
		try {
			if(dsn.equals("mysql")) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kosaDB", "jj", "mysql");
			} else if(dsn.equals("oracle")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "jj", "oracle");
				System.out.println("oracle connection success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return conn;
		}

	}
	
	public static Connection getConnection(String dsn, String userid, String userpwd) { 
		Connection conn = null;
		try {
			if(dsn.equals("mysql")) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kosaDB", userid, userpwd);
			} else if(dsn.equals("oracle")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", userid, userpwd);
//				System.out.println("oracle connection success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return conn;
		}

	}
	
	public static void menu() {
		System.out.println("\n---------- Booking ------------");
		System.out.println("\t 0. rollback");
		System.out.println("\t 1. 식당 목록 전체보기 ");
		System.out.println("\t 2. 식당 예약 ");
		System.out.println("\t 3. 예약 확인 ");
		System.out.println("\t 4. 예약 수정 ");
		System.out.println("\t 5. 예약 삭제 ");
		System.out.println("\t 6. 카테고리 별 식당 조회 ");
		System.out.println("\t 7. 예약 가능 식당 조회 ");
		System.out.println("\t 8. 종료 ");
		System.out.println("\t 9. commit");
		System.out.println("\t>> 원하는 메뉴 선택하세요 ");

	}
	
	
}