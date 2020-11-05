package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class test extends Application {

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Login");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 10, 0));

		Text BudgetText = new Text("BudgetMe");
		BudgetText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		grid.add(BudgetText, 0, 0);

		Text scenetitle = new Text("Login");
		scenetitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(scenetitle, 0, 1, 2, 1);

		Label userName = new Label("Username:");
		grid.add(userName, 0, 2);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 2);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 3);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 3);

		Button btn = new Button("Log in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 5);

		Scene scene = new Scene(grid, 500, 475);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}