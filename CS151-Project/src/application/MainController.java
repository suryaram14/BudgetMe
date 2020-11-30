package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController implements Initializable {
	// all FXML initializing variables

	@FXML
	private TableView<Account> tableTransactions;

	@FXML
	private TableColumn<Account, Integer> col_transactionID;

	@FXML
	private TableColumn<Account, Date> col_date;

	@FXML
	private TableColumn<Account, String> col_description;

	@FXML
	private TableColumn<Account, String> col_category;

	@FXML
	private TableColumn<Account, Double> col_amount;

	@FXML
	private TextField txt_amount;

	@FXML
	private TextField txt_description;

	@FXML
	private DatePicker txt_date;

	@FXML
	private TextField txt_transactionID;

	@FXML
	private ComboBox<String> txt_category;

	@FXML
	private Button logOutBtn;

	@FXML
	private AnchorPane HomePagePane;

	@FXML
	private BorderPane barChartBorderPane;

	@FXML
	private BorderPane pieChartBorderPane;

	ObservableList<Account> lists;

	int index = -1;

	// variables associated with mySQL
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	String username;
	MySqlConnection mySqlCon = new MySqlConnection();

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	/*
	 * to add transaction, user must input all fields after inputting all fields, if
	 * everything is input correctly, an alert message shows up confirming the
	 * adding of a transaction.
	 * 
	 * if transaction is unsuccessful, an Error alert will pop up
	 */

	public void addTransaction() {
		conn = MySqlConnection.ConnectDb();
		String sqlAdd = "insert into Account (username, date, description, category, amount) values(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sqlAdd);
			ps.setString(1, username);
			ps.setString(2, txt_date.getValue().toString());
			ps.setString(3, txt_description.getText());
			ps.setString(4, txt_category.getValue().toString());
			ps.setString(5, txt_amount.getText());
			ps.execute();

			Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
			alertSuccess.showAndWait();
			UpdateTransaction();
		} catch (Exception e) {
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.showAndWait();
		}
	}

	// helper method to update the table view after user inputs a transaction
	// helper method is called to updated table after every add and delete function
	// this helps table update dynamically
	public void UpdateTransaction() {
		getUsername();
		col_transactionID.setCellValueFactory(new PropertyValueFactory<Account, Integer>("transactionID"));
		col_date.setCellValueFactory(new PropertyValueFactory<Account, Date>("date"));
		col_description.setCellValueFactory(new PropertyValueFactory<Account, String>("description"));
		col_category.setCellValueFactory(new PropertyValueFactory<Account, String>("category"));
		col_amount.setCellValueFactory(new PropertyValueFactory<Account, Double>("amount"));
		mySqlCon.setUsername(username);
		lists = mySqlCon.getAccountData();
		tableTransactions.setItems(lists);
	}

	/*
	 * to delete transaction, user must input transaction ID. if transaction ID
	 * matches an ID in the table, user can delete a transaction. Success
	 * Confirmation message will show up and alert user that the deletion was
	 * successful
	 * 
	 * if user does not input a proper transaction ID to be deleted, Error alert
	 * message shows up
	 */
	public void deleteTransaction() {
		conn = MySqlConnection.ConnectDb();
		String sqlDelete = "delete from Account where transactionID = ?";
		try {
			ps = conn.prepareStatement(sqlDelete);
			ps.setString(1, txt_transactionID.getText());
			ps.execute();

			Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
			alertSuccess.showAndWait();
			UpdateTransaction();
		} catch (Exception e) {
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.showAndWait();
		}
	}

	/*
	 * method to get each row in the data table If row is selected we can click on
	 * it. Once clicked on, we can delete transaction from here
	 * 
	 * On Mouse Clicked handles this method in the table
	 */
	public void getRow(MouseEvent e) {
		conn = MySqlConnection.ConnectDb();
		index = tableTransactions.getSelectionModel().getSelectedIndex();

		if (index <= -1)
			return;

		txt_transactionID.setText(col_transactionID.getCellData(index).toString());
		txt_date.setPromptText(col_date.getCellData(index).toString());
		txt_description.setText(col_description.getCellData(index).toString());
		txt_category.setPromptText(col_category.getCellData(index).toString());
		txt_amount.setText(col_amount.getCellData(index).toString());
	}

	/*
	 * to view a bar chart representation of their expenses, the user must click on
	 * 'Bar Chart View' and it will show a chart of their expenses split up in a bar
	 * chart
	 */

	public void goToBarChart(ActionEvent event) throws SQLException {
		conn = MySqlConnection.ConnectDb();

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Category");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Amount");

		BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);

		XYChart.Series<String, Number> xyChart = new XYChart.Series<String, Number>();
		String sql = "select category, amount from Account order by amount asc";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			xyChart.getData().add(new XYChart.Data<String, Number>(rs.getString("category"), rs.getDouble("amount")));
		}

		barChart.getData().add(xyChart);
		barChart.setLegendVisible(false);

		barChartBorderPane.setCenter(barChart);
	}

	/*
	 * to view a bar chart representation of their expenses, the user must click on
	 * 'Pie Chart View' and it will show a chart of their expenses sliced in a pie
	 * chart
	 */
	public void goToPieChart(ActionEvent event) throws SQLException {
		conn = MySqlConnection.ConnectDb();

		PieChart pieChart = new PieChart();

		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		String sql = "select * from Account";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			list.add(new PieChart.Data(rs.getString("category"), rs.getDouble("amount")));
		}

		pieChart.getData().addAll(list);

		pieChartBorderPane.setCenter(pieChart);
	}

	public void logout() {
		try {
			Stage mainStage = (Stage) HomePagePane.getScene().getWindow();
			mainStage.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/login.FXML"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane, 700, 400);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Initialize method to set and grab values from table in database
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO Auto-generated method stub
		txt_category.getItems().addAll("Housing", "Utilities", "Misc.", "Transportation", "Clothing", "Medical", "Food",
				"Debt Payments");

		col_transactionID.setCellValueFactory(new PropertyValueFactory<Account, Integer>("transactionID"));
		col_date.setCellValueFactory(new PropertyValueFactory<Account, Date>("date"));
		col_description.setCellValueFactory(new PropertyValueFactory<Account, String>("description"));
		col_category.setCellValueFactory(new PropertyValueFactory<Account, String>("category"));
		col_amount.setCellValueFactory(new PropertyValueFactory<Account, Double>("amount"));
		mySqlCon.setUsername(username);
		lists = mySqlCon.getAccountData();
		tableTransactions.setItems(lists);
	}

}
