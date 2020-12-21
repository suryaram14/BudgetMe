package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import homePage.HomePageController;
import homePage.MySqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController implements Initializable {
	// defined fields in login.FXML
	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnSignin;

	@FXML
	private TextField lblInvalid;

	@FXML
	private AnchorPane LoginPane;

	@FXML
	private Hyperlink createAccLink;

	// variables associated with mySQL
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	// Sample
	// username: test
	// password: test

	public void handle(ActionEvent event) throws IOException {
		if (event.getSource() == createAccLink) {
			createAcc();
		} else {
			login();
		}
	}

	@FXML
	void createAcc() {
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/createAccount/createAccount.FXML"));
			LoginPane.getChildren().setAll(pane);
		} catch (IOException error) {
			error.printStackTrace();
		}

	}

	@FXML
	void login() throws IOException {
		conn = MySqlConnection.ConnectDb();
		String loginSQL = "select * from users where username = ? and password = ?";

		// If username and password are null, and Invalid message will appear
		if (txtUsername.getText().equals("") || txtPassword.getText().equals("")) {  
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setContentText("Fields cannot be empty.");
			alertError.showAndWait();
		} else {
			// To login connect mysql database with eclipse
			// if username and password fields are blank, we can set username and passwords
			// by inserting into mysql database
			try {
				ps = conn.prepareStatement(loginSQL);
				ps.setString(1, txtUsername.getText());
				ps.setString(2, txtPassword.getText());
				rs = ps.executeQuery();

				// if username and password match - go to Main page
				if (rs.next()) {
						Stage loginStage = (Stage) LoginPane.getScene().getWindow();
						loginStage.close();
						HomePageController mainControl = new HomePageController();
						mainControl.setUsername(txtUsername.getText());
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/homePage/HomePage.FXML"));
						loader.setController(mainControl);
						AnchorPane pane = loader.load();
						Scene scene = new Scene(pane, 937, 503);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.show();				
					}

				// if username and password dont match, set to Invalid and clear text inputs
				else {
					Alert alertError = new Alert(AlertType.ERROR);
        			alertError.setContentText("Username and/or password is wrong");
        			alertError.showAndWait();
				}
			} catch (SQLException e) {
				Alert alertError = new Alert(AlertType.ERROR);
    			alertError.setContentText("Username or password is wrong");
    			alertError.showAndWait();
    			System.out.println(e.getMessage());
			}
		}
	}
	
	public String username;
	
	public String getUsername() {
		return username;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}