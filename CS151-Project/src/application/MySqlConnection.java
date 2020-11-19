package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySqlConnection {
	Connection conn = null;

	// Connection will connect to mySQL database and return the connection
	// successfully if connector file is referenced in file

	public static Connection ConnectDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs151-project", "root",
					"password");
			return conn;
		} catch (ClassNotFoundException | SQLException ex) {
			System.err.println("MySqlConnection : " + ex.getMessage());
			return null;
		}
	}

	// method to get all data from database and input into table view for user to
	// see when the application is opened
	public static ObservableList<Account> getAccountData() {
		Connection conn = ConnectDb();
		ObservableList<Account> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from Account");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Account(Integer.parseInt(rs.getString("transactionID")), rs.getDate("date"),
						rs.getString("description"), rs.getFloat("amount")));
			}
		} catch (Exception e) {
		}
		return list;
	}
}
