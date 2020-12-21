package createAccount;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import homePage.MySqlConnection;
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

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public void handle(ActionEvent event) throws Exception {
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
	void register(ActionEvent event) throws Exception {
		conn = MySqlConnection.ConnectDb();
		String registerSQL = "insert into users(username, password) values(?, ?)";
		if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setContentText("Fields cannot be empty.");
			alertError.showAndWait();
		}
		else {
			try {
				ps = conn.prepareStatement(registerSQL);
				ps.setString(1, txtUsername.getText());
				ps.setString(2, txtPassword.getText());
				ps.execute();
				
				AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/Main.FXML"));
				createAccPane.getChildren().setAll(pane);
			} 
			catch (SQLException e) {
				System.out.print(e);
				Alert alertError = new Alert(AlertType.ERROR);
    			alertError.setContentText("Username already exists. Please choose another username.");
    			alertError.showAndWait();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
