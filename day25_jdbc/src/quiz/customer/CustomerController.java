package quiz.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.util.regex.Pattern;

import connUtil.DBConnection;

public class CustomerController {

	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection conn = null;
	static PreparedStatement pstmt = null;

	// db connect
	public static void connect() {
		try {
			conn = DBConnection.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);

		} catch (Exception e) {
			e.printStackTrace();
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

		Customer customer = new Customer();

		while (true) {

			DBConnection.menu();
			switch (sc.nextInt()) {
			case 0:
				rollback();
				break;
			case 1:
				commit();
				break;
			case 2:
				insert();
				selectAll(customer.getClassName());
				break;
			case 3:
				update(customer.getClassName());
				break;
			case 4:
				selectAll(customer.getClassName());
				break;
			case 5:
				select(customer.getClassName());
				break;
			case 6:
				delete(customer.getClassName());
				break;
			case 7:
				close();
				System.out.println("프로그램종료!");
				System.exit(0);
				break;
			default:
				break;
			}
		} // end while
	}
	//이메일 전화번호 정규표현식 
	private static boolean MatchString(int div, String string) {
		String pattern;
		if (div == 1) {// 이메일
			pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
			if (Pattern.matches(pattern, string)) {
				return true;
			} else {
				return false;
			}
		} else if (div == 2) {// 휴대전화
			pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
			if (Pattern.matches(pattern, string)) {
				return true;
			} else {
				return false;
			}
		} else {// 둘다 아닌다른 수가 들어온 경우
			System.out.println("로직오류");
			return false;
		}

	}
	// customer 객체 이용해서 업데이트
	private static void update(String className) throws SQLException {

		System.out.println("변경할 고객 번호 입력 > ");

		Customer customer = findCode(sc.nextInt());
		System.out.println(customer);
		if (customer != null) { // null이면 해당 고객번호가 없는것
			
			System.out.println("고객이름을 변경하시겠습니까?(y/n)");
			if (sc.next().equalsIgnoreCase("y")) {
				System.out.println("변경할 고객 이름을 입력해주세요");
				customer.setName(sc.next());
			}
			
			System.out.println("이메일을 변경하시겠습니까?(y/n)");
			if (sc.next().equalsIgnoreCase("y")) {
				while(true) {
					System.out.println("변경할 이메일을 입력해주세요");
					String email = sc.next();
					if(!MatchString(1, email)) {//정규표현식
						System.out.println("이메일형식에 맞게 입력해주세요!");
					}else {
						customer.setEmail(email);
						break;
					}
				}
			}
			System.out.println("전화번호를 입력하시겠습니까?(y/n)");
			if (sc.next().equalsIgnoreCase("y")) {
				while(true) {
					System.out.println("전화번호를 입력해주세요");
					String phone = sc.next();
					if(!MatchString(2, phone)) {//정규표현식
						System.out.println("이메일형식에 맞게 입력해주세요!");
					}else {
						customer.setPhone(phone);
						break;
					}
				}
			}
			pstmt = conn.prepareStatement("UPDATE " + className + " SET name = ?, email = ?, phone = ? WHERE code = ?"); // 변경 안했으면 조회했던 칼럼값 그대로 업뎃 된다.
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getEmail());
			pstmt.setString(3, customer.getPhone());
			pstmt.setInt(4, customer.getCode());
			int count = pstmt.executeUpdate();

			System.out.println(count + "개 행 업데이트 완료!");
		} else {
			System.out.println("고객번호를 제대로 입력해 주세요");
		}

	}

	// 번호에 해당하는 행 받아와서 customer 객체에 넣고 반환
	private static Customer findCode(int code) {
		Customer customer = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM customers WHERE code = " + code);
			if (rs.next()) {
				customer = new Customer();// 조회된 결과가 있을때 객체 초기화
				customer.setCode(rs.getInt(1));
				customer.setName(rs.getString(2));
				customer.setEmail(rs.getString(3));
				customer.setPhone(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	private static void delete(String className) {
		String sql = "DELETE FROM " + className + " WHERE code = ?";
		try {
			System.out.println("삭제할 고객 번호 입력 > ");
			int code = sc.nextInt();
			Customer customer = findCode(code);
			if (customer != null) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, code);

				int count = pstmt.executeUpdate();
				System.out.println(count + "개 삭제");
			} else {
				System.out.println("삭제할 고객번호를 제대로 입력해 주세요!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 조건에 의한 검색
	private static void select(String className) {
		String sql = "SELECT * FROM " + className + " WHERE code = ?";
		try {
			System.out.println("고객 번호 입력");
			int code = sc.nextInt();
			Customer customer = findCode(code);
			if (customer != null) {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, code);

				rs = pstmt.executeQuery();

				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();

				System.out.println("코드\t이름\t이메일\t\t전화번호");
				if (rs.next()) {
					for (int i = 1; i <= count; i++) {// 각 타입별로 출력하기
						switch (rsmd.getColumnType(i)) {
						case Types.NUMERIC:
						case Types.INTEGER:
							System.out.print(rs.getInt(i) + "\t");
							break;
						default:
							System.out.print(rs.getString(i) + "\t");
							break;
						}
					} // end for
					System.out.println();
				}
			} else {
				System.out.println("고객번호를 제대로 입력해주세요!");
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

		String email = "", name = "", phone = "";
		System.out.println("NAME : ");
		name = sc.next();
		while (true) {
			System.out.println("EMAIL : ");
			email = sc.next();
			if (!MatchString(1, email)) System.out.println("이메일 형식에 맞게 입력해주세요!");//정규표현식 사용
			else break;

		}
		
		while (true) {
			System.out.println("PHONE : ");
			phone = sc.next();
			if (!MatchString(2, phone)) System.out.println("전화번호 형식에 맞게 입력해주세요!");//정규표현식 사용
			else break;
		}

		try {
			pstmt = conn.prepareStatement("insert into customers values(code_seq.nextval,?,?,?)");
			pstmt.setNString(1, name);
			pstmt.setNString(2, email);
			pstmt.setNString(3, phone);

			pstmt.executeUpdate();
			System.out.println(name + "고객 님 신규 추가됐습니다.");
			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void selectAll(String className) throws SQLException {
		rs = stmt.executeQuery("select * from " + className);
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		System.out.println("코드\t이름\t이메일\t\t전화번호");
		while (rs.next()) {
			for (int i = 1; i <= count; i++) {// 각 타입별로 출력하기
				switch (rsmd.getColumnType(i)) {
				case Types.NUMERIC:
				case Types.INTEGER:
					System.out.print(rs.getInt(i) + "\t");
					break;
				default:
					System.out.print(rs.getString(i) + "\t");
					break;
				}
			} // end for
			System.out.println();
		} // end
	}// end select



}
