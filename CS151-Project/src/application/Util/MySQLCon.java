package application.Util;

import java.sql.DriverManager;

public class MySQLCon {

	public static void main(String args[]) {
		try {
			java.sql.Connection con;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs151-project", "root", "boyebak2000");
			System.out.println("Success");
		} catch (Exception e) {
			System.out.print("Error");
			;
		}
	}

}
