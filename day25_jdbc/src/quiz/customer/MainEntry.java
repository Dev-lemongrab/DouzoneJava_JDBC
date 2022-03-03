package quiz.customer;

import java.sql.SQLException;

public class MainEntry {
	public static void main(String[] args) throws SQLException {//View
		CustomerController.connect();
		CustomerController.menu();
	}
}
