package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController implements Initializable {

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnSignin;

	@FXML
	private TextField lblInvalid;

	Connection con;
	PreparedStatement prepStmt;
	ResultSet rs;

	// Sample
	// username: test
	// password: test
	@FXML
	void login(ActionEvent event) {
		String username = txtUsername.getText();
		String password = txtPassword.getText();

		if (username.equals("") && password.equals("")) {
			lblInvalid.setText("Invalid");
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs151-project", "root", "password");
				prepStmt = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
				prepStmt.setString(1, username);
				prepStmt.setString(2, password);

				rs = prepStmt.executeQuery();

				if (rs.next()) {
					lblInvalid.setText("Success");
				} else {
					lblInvalid.setText("Invalid");
					txtUsername.setText("");
					txtPassword.setText("");
				}
			} catch (SQLException e) {
				System.out.println(e);
			} catch (ClassNotFoundException ex) {
				System.out.println(ex);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}