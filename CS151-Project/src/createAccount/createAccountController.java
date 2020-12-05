package createAccount;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class createAccountController implements Initializable {

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button registerBtn;

	@FXML
	private AnchorPane createAccPane;

	@FXML
	private Hyperlink loginLink;

	Connection con;
	PreparedStatement prepStmt;
	ResultSet rs;

	public void handle(ActionEvent event) throws ClassNotFoundException {
		if (event.getSource() == loginLink) {
			goToLogin();
		} else {
			register(event);
		}
	}

	@FXML
	void goToLogin() {
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Main.FXML"));
			createAccPane.getChildren().setAll(pane);
		} catch (IOException error) {
			error.printStackTrace();
		}

	}

	@FXML
	void register(ActionEvent event) throws ClassNotFoundException {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		
		if (username.equals("") && password.equals("")) {
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.showAndWait();
		}
		
		else {
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account_database", "root", "1234");
				prepStmt = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
				prepStmt.setString(1, username);
				prepStmt.setString(2, password);
				prepStmt.executeUpdate();
				try {
					AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Main.FXML"));
					createAccPane.getChildren().setAll(pane);
		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.print(e);
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
