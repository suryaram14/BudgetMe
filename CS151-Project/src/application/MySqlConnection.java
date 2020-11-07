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

	public static Connection ConnectDb() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.153/cs151-project", "newuser",
					"password");
			return conn;
		} catch (ClassNotFoundException | SQLException ex) {
			System.err.println("MySqlConnection : " + ex.getMessage());
			return null;
		}
	}

	public static ObservableList<Account> getAccountData() {
		Connection conn = ConnectDb();
		ObservableList<Account> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("select * from Account");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Account(rs.getInt("transactionID"), rs.getDate("date"), rs.getString("description"),
						rs.getFloat("amount")));
			}
		} catch (Exception e) {
		}
		return list;
	}
}
