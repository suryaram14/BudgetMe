package application.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLCon {

	public static Connection connectionDB() {
		try {
			java.sql.Connection con;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs151-project", "root", "password");
			System.out.println("Success");
			return con;
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String args[]) {
		connectionDB();
	}
}
