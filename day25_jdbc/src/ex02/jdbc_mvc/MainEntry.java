package ex02.jdbc_mvc;

import java.sql.SQLException;

public class MainEntry {
	public static void main(String[] args) throws SQLException {//View
		GiftController.connect();
		GiftController.menu();
	}
}
