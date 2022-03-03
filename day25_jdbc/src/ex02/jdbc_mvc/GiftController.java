package ex02.jdbc_mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import connUtil.DBConnection;

public class GiftController {

	// 연결, 삽입, 삭제, 수정, 검색....
	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection conn = null;
	static PreparedStatement pstmt = null;

	// connect
	public static void connect() {
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);

		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}

	// close
	public static void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// menu
	public static void menu() throws SQLException {
		Gift gift = new Gift();
		while (true) {
			System.out.println("\t0. rollback()");
			DBConnection.menu();
			switch (sc.nextInt()) {
			case 0:
				System.out.println("Commit 하시겠습니까?(Y/N)");
				System.out.println("안하시려면 Rollback 됩니다.");
				if (sc.next().equalsIgnoreCase("y")) {
					commit();
					selectAll(gift.getClassName());
				} else {
					rollback();
				}
				break;
			case 1:
				selectAll(gift.getClassName());
				insert();
				selectAll(gift.getClassName());
				break;
			case 2:
				update(gift.getClassName());
				break;
			case 3:
				selectAll(gift.getClassName());
				break;
			case 4:
				select(gift.getClassName());
				break;
			case 5:
				delete(gift.getClassName());
				break;
			case 6:
				close();
				System.out.println("프로그램종료!");
				System.exit(0);
				break;
			default:
				break;
			}
		} // end while
	}
	
	//gift 객체 이용해서 업데이트 
	private static void update(String className) throws SQLException {

		System.out.println("변경할 선물 번호 입력");
		
		Gift gift = findGno(sc.nextInt());
		System.out.println(gift);
		
		System.out.println("선물이름을 변경하시겠습니까?(y/n)");
		if (sc.next().equalsIgnoreCase("y")) {
			System.out.println("변경할 선물 이름을 입력해주세요");
			gift.setGname(sc.next()); 
		}
		
		System.out.println("선물최저가를 변경하시겠습니까?(y/n)");
		if (sc.next().equalsIgnoreCase("y")) {
			System.out.println("변경할 선물 최저가 입력해주세요");
			gift.setG_start(sc.nextInt());
		}
		
		System.out.println("선물최고가를 입력하시겠습니까?(y/n)");
		if (sc.next().equalsIgnoreCase("y")) {
			System.out.println("변경할 선물 최고가를 입력해주세요");
			gift.setG_end(sc.nextInt());
		}
		
		

		pstmt = conn.prepareStatement("UPDATE gift SET gname = ?, g_start = ?, g_end = ? WHERE gno = ?");
		
		pstmt.setString(1, gift.getGname());
		pstmt.setInt(2, gift.getG_start());
		pstmt.setInt(3, gift.getG_end());
		pstmt.setInt(4, gift.getGno());
		
		
		int count = pstmt.executeUpdate();
		
		System.out.println(count+"개 행 업데이트 완료!");
	}


	//번호에 해당하는 행 받아와서 gift 객체에 넣고 반환 
	private static Gift findGno(int gno) throws SQLException {
		Gift gift = new Gift();
		rs = stmt.executeQuery("SELECT * FROM gift WHERE gno = " + gno);
		if (rs.next()) {
			gift.setGno(rs.getInt("gno"));
			gift.setGname(rs.getString("gname"));
			gift.setG_start(rs.getInt("g_start"));
			gift.setG_end(rs.getInt("g_end"));
		}
		return gift;
	}

	private static void delete(String className) {
		String sql = "DELETE FROM gift WHERE gno = ?";
		try {
			System.out.println("삭제할 선물 번호 입력");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc.nextInt());

			int count = pstmt.executeUpdate();
			System.out.println(count + "개 삭제");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 조건에 의한 검색
	private static void select(String className) {
		String sql = "SELECT * FROM gift WHERE gno = ?";
		try {
			System.out.println("선물 번호 입력");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sc.nextInt());

			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			if (rs.next()) {
				for (int i = 1; i <= count; i++) {// 각 타입별로 출력하기
					switch (rsmd.getColumnType(i)) {
					case Types.NUMERIC:
					case Types.INTEGER:
						System.out.println(rsmd.getColumnName(i) + " : " + rs.getInt(i) + " ");
						break;
					case Types.FLOAT:
						System.out.println(rsmd.getColumnName(i) + " : " + rs.getFloat(i) + " ");
						break;
					case Types.DOUBLE:
						System.out.println(rsmd.getColumnName(i) + " : " + rs.getDouble(i) + " ");
						break;
					case Types.DATE:
						System.out.println(rsmd.getColumnName(i) + " : " + rs.getDate(i) + " ");
						break;
					default:
						System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i) + " ");
						break;
					}
				} // end for
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void rollback() throws SQLException {
		conn.rollback();
		System.out.println("rollback success");
	}

	public static void commit() throws SQLException {
		conn.commit();
		System.out.println("commit success");
	}

	private static void insert() {
		System.out.println("GNO : ");
		String gno = sc.next();
		System.out.println("GNAME : ");
		String gname = sc.next();
		System.out.println("G_START : ");
		String g_start = sc.next();
		System.out.println("G_END : ");
		String g_end = sc.next();

		try {
			pstmt = conn.prepareStatement("insert into gift values(?,?,?,?)");
			pstmt.setNString(1, gno);
			pstmt.setNString(2, gname);
			pstmt.setNString(3, g_start);
			pstmt.setNString(4, g_end);

			int result = pstmt.executeUpdate();
			System.out.println(result + "개 데이터가 추가됐습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void selectAll(String className) throws SQLException {
		rs = stmt.executeQuery("select * from " + className);
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= count; i++) {// 각 타입별로 출력하기
				switch (rsmd.getColumnType(i)) {
				case Types.NUMERIC:
				case Types.INTEGER:
					System.out.print(rsmd.getColumnName(i) + " : " + rs.getInt(i) + " ");
					break;
				case Types.FLOAT:
					System.out.print(rsmd.getColumnName(i) + " : " + rs.getFloat(i) + " ");
					break;
				case Types.DOUBLE:
					System.out.print(rsmd.getColumnName(i) + " : " + rs.getDouble(i) + " ");
					break;
				case Types.CHAR:
					System.out.print(rsmd.getColumnName(i) + " : " + rs.getString(i) + " ");
					break;
				case Types.DATE:
					System.out.print(rsmd.getColumnName(i) + " : " + rs.getDate(i) + " ");
					break;
				default:
					System.out.print(rsmd.getColumnName(i) + " : " + rs.getString(i) + " ");
					break;
				}
				System.out.println();
			} // end for
			System.out.println();
		} // end
	}// end select

}
