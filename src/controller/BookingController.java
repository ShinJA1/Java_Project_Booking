package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;

import dbConn.util.CloseHelper;
import dbConn.util.ConnectionHelper;
import model.BookingVO;
import model.RTVO;

public class BookingController {
	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt = null;
	static Connection conn = null;	
	
	public static void connect() {
		try {
			conn = ConnectionHelper.getConnection("oracle");
			stmt = conn.createStatement();
			conn.setAutoCommit(false);  
			
		} catch (Exception e) {  e.printStackTrace(); }
	}
	
	public static void close() {
		try {
			CloseHelper.close(rs); CloseHelper.close(stmt);
			CloseHelper.close(pstmt); CloseHelper.close(conn);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	//menu
	public static void menu() throws SQLException{
		BookingVO b_vo = new BookingVO();
		RTVO rt_vo = new RTVO();

		while(true) {
			System.out.println();
			ConnectionHelper.menu();
		
			switch(sc.nextInt()) {
			case 0: System.out.println("Commit 하시겠습니까?(Y/N) ");
				System.out.println("안하시면 Rollback 됩니다");
				if(sc.next().equalsIgnoreCase("Y")) {
					conn.commit(); //예외발생
					selectAll(rt_vo.getClassName());
				} else {
					conn.rollback();
					selectAll(rt_vo.getClassName());
				}
				break;
			case 1: selectAll(rt_vo.getClassName()); break;
			case 2: insert();  break;
//			case 3: select(b_vo.getClassName()); break;
			case 4: update(b_vo.getClassName()); break;
			case 5: delete(b_vo.getClassName()); break;
			case 6: selectByType(rt_vo.getClassName()); break;
//			case 7: selectByInwon(rt_vo.getClassName()); break;
			case 8: close(); System.out.println("프로그램 종료합니다"); 
					System.exit(0); break;
			case 9: conn.commit();
					System.out.println("성공적으로 완료되었습니다"); break;
			}
		}//end while
		
	} //end menu
	
	

	    public static void selectByType(String className) throws SQLException {
        selectAll(className);
//    	pstmt = conn.prepareStatement("select * from " + className +" where rt_type = ?");
        System.out.print("원하는 카테고리 입력: ");
        String rt_type = sc.next();
        String sql = "Select * from "+className+" where rt_type = ?";
        pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, rt_type);
        rs =  pstmt.executeQuery();
        
        
		ResultSetMetaData rsmd = rs.getMetaData();  //해당 테이블에 대한 정보
		int count = rsmd.getColumnCount();  //컬럼 개수 리턴
		
		while(rs.next()) {
			for(int i=1; i<=count; i++) {   //database 는 1부터
				//각 타입별로 출력하기
				switch (rsmd.getColumnType(i)) {
				case Types.NUMERIC:
				case Types.INTEGER:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getInt(i)+" ");
					break;
				case Types.FLOAT:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getFloat(i)+" ");
					break;
				case Types.DOUBLE:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getDouble(i)+" ");
					break;
				case Types.CHAR:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i)+" ");
					break;
				case Types.DATE:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getDate(i)+" ");
					break;
					
					default:
						System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i)+" ");
						break;
				}  //switch end
			} //for end
			System.out.println();
		}//end while
        
        
        
        
//        ArrayList<GogakVO> list = new ArrayList();
//        while(rs.next()) {
//        	GogakVO gogak = new GogakVO();
//        	
//        	gogak.setGno(rs.getInt("GNO"));
//        	gogak.setGname(rs.getString("GNAME"));
//        	gogak.setJumin(rs.getString("JUMIN"));
//        	gogak.setPoint(rs.getInt("POINT"));
//        	list.add(gogak);
//        }
//        if(list.size()==0) {
//        	System.out.println("해당하는 사람이 없습니다");
//        	System.out.println();
//        	return;
//        }
//        for(GogakVO gogak: list) {
//        	System.out.println("GNO: "+gogak.getGno());
//        	System.out.println("GNAME: "+gogak.getGname());
//        	System.out.println("JUMIN: "+gogak.getJumin());
//        	System.out.println("POINT: "+gogak.getPoint());
//        	System.out.println();
//        }

    }

	


	//selectAll()
	public static void selectAll(String className) throws SQLException{
		rs = stmt.executeQuery("Select * from "+className);  //반환값 있는 경우
		
		ResultSetMetaData rsmd = rs.getMetaData();  //해당 테이블에 대한 정보
		int count = rsmd.getColumnCount();  //컬럼 개수 리턴
		
		while(rs.next()) {
			for(int i=1; i<=count; i++) {   //database 는 1부터
				//각 타입별로 출력하기
				switch (rsmd.getColumnType(i)) {
				case Types.NUMERIC:
				case Types.INTEGER:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getInt(i)+" ");
					break;
				case Types.FLOAT:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getFloat(i)+" ");
					break;
				case Types.DOUBLE:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getDouble(i)+" ");
					break;
				case Types.CHAR:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i)+" ");
					break;
				case Types.DATE:
					System.out.println(rsmd.getColumnName(i)+" : "+rs.getDate(i)+" ");
					break;
					
					default:
						System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i)+" ");
						break;
				}  //switch end
			} //for end
			System.out.println();
		}//end while
		
	}//end selectAll
	
	//insert
	private static void insert() {
		System.out.println("고객번호 : ");  String gno = sc.next();
		System.out.println("고객이름 : ");  String gname = sc.next();
		System.out.println("주민번호 : ");  String jumin = sc.next();
		System.out.println("포인트 : ");  String point = sc.next();

		try {
			pstmt = conn.prepareStatement("INSERT INTO GOGAK VALUES(?, ?, ?, ?)");
			pstmt.setString(1, gno);
			pstmt.setString(2, gname);
			pstmt.setString(3, jumin);
			pstmt.setString(4, point);

			int result = pstmt.executeUpdate();

			System.out.println(result + "개 테이터가 추가되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void delete(String className) throws SQLException {
		selectAll(className);
		System.out.println("삭제할 고객번호 입력");
		int gno = sc.nextInt();
		
		try {
			String sql = "DELETE GOGAK WHERE GNO=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gno);
			
			int result = pstmt.executeUpdate();
			System.out.println(result+"개 데이터 삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	// 3. update
	public static void update(String className) throws SQLException {
		selectAll(className);
		System.out.println("수정할 사람 gno : ");
		String gno = sc.next();

		System.out.print("GNAME: ");  String gname = sc.next();
		System.out.print("JUMIN: ");  String jumin = sc.next();
		System.out.print("POINT: ");  String point = sc.next();
		try {
			pstmt = conn.prepareStatement("update GOGAK set gno=?, jumin=?, point=? WHERE GNO = ? ");
			pstmt.setString(1, gno);
			pstmt.setString(2, gname);
			pstmt.setString(2, jumin);
			pstmt.setString(3, point);
			pstmt.setString(4, gno);

			int result = pstmt.executeUpdate();

			System.out.println(result + "개 데이터 수정 성공");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	
	
	
}
