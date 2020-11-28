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
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account_database", "root", "1234");
			return conn;
		} catch (ClassNotFoundException | SQLException ex) {
			System.err.println("MySqlConnection : " + ex.getMessage());
			return null;
		}
	}

	String username;

	public void setUsername(String username) {
		this.username = username;
	}

	// method to get all data from database and input into table view for user to
	// see when the application is opened
	public ObservableList<Account> getAccountData() {
		Connection conn = ConnectDb();
		ObservableList<Account> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from Account where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			System.out.println(ps.toString());

			while (rs.next()) {
				list.add(new Account(Integer.parseInt(rs.getString("transactionID")), rs.getDate("date"), rs.getString("description"), rs.getString("category"), rs.getDouble("amount")));
			}
		} catch (Exception e) {
		}
		return list;
	}
}
