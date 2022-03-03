package jdbc;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MainEntry {
	static {
		
	}
	public static void main(String[] args) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "CREATE TABLE KOSATAB(NAME VARCHAR2(20), AGE NUMBER)";
			System.out.println(sql.toString());
			stmt.executeUpdate(sql);
			
			sql = "select * from kosaTab";
			rs =stmt.executeQuery(sql);
			
			sql = "insert into kosaTab values('aa', 33)";
			int result = stmt.executeUpdate(sql);
			
			System.out.println(result + "개 추가됨");
			
			while(rs.next()) {
				System.out.println("name : " + rs.getString(1));
				System.out.println("age : " + rs.getInt("age"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			rs.close(); stmt.close();conn.close();
		}
	}
}
