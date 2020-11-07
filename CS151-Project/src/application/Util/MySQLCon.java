package application.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLCon {

	// Connection will connect to mySQL database and return the connection successfully if connector file is referenced in file
	
	public static Connection connectionDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account_database", "root", "1234");
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
