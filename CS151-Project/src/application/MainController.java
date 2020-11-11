package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable{
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
    private TableColumn<Account, Float> col_amount;
    
    @FXML
    private TextField txt_amount;

    @FXML
    private TextField txt_description;

    @FXML
    private DatePicker txt_date;
    
    @FXML
    private TextField txt_transactionID;
    
    @FXML
    private AnchorPane HomePagePane;

    ObservableList<Account> lists;
    
    int index = -1;
    
    // variables associated with mySQL
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null; 
    
	/* to add transaction, user must input all fields
	 * after inputting all fields, if everything is input correctly,
	 * an alert message shows up confirming the adding of a transaction.
	 * 
	 * if transaction is unsuccessful, an Error alert will pop up*/
    
    public void addTransaction() {
    	conn = MySqlConnection.ConnectDb();
    	String sqlAdd = "insert into Account (date, description, amount) values(?,?,?)";
    	try {
    		ps = conn.prepareStatement(sqlAdd);
    		ps.setString(1, txt_date.getValue().toString());
    		ps.setString(2, txt_description.getText());
    		ps.setString(3, txt_amount.getText());
    		ps.execute();
    		
    		
    		Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
    		alertSuccess.showAndWait();
    		UpdateTransaction();
    	}catch(Exception e){
    		Alert alertError = new Alert(AlertType.ERROR);
    		alertError.showAndWait();
    	}
    }
    
    // helper method to update the table view after user inputs a transaction
    // helper method is called to updated table after every add and delete function
    // this helps table update dynamically
    public void UpdateTransaction(){
    	col_transactionID.setCellValueFactory(new PropertyValueFactory<Account,Integer>("transactionID"));
    	col_date.setCellValueFactory(new PropertyValueFactory<Account,Date>("date"));
    	col_description.setCellValueFactory(new PropertyValueFactory<Account,String>("description"));
    	col_amount.setCellValueFactory(new PropertyValueFactory<Account,Float>("amount"));
        
        lists = MySqlConnection.getAccountData();
        tableTransactions.setItems(lists);
    }
    
	/* to delete transaction, user must input transaction ID.
	 * if transaction ID matches an ID in the table, user can delete a transaction.
	 * Success Confirmation message will show up and alert user that the deletion was successful
	 * 
	 * if user does not input a proper transaction ID to be deleted,
	 * Error alert message shows up*/
    public void deleteTransaction(){
    	conn = MySqlConnection.ConnectDb();
    	String sqlDelete = "delete from Account where transactionID = ?";
    	try {
    		ps = conn.prepareStatement(sqlDelete);
    		ps.setString(1, txt_transactionID.getText());
    		ps.execute();
    		
    		Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
    		alertSuccess.showAndWait();
    		UpdateTransaction();
    	} catch(Exception e) {
    		Alert alertError = new Alert(AlertType.ERROR);
    		alertError.showAndWait();
    	}
    }
    
	/*method to get each row in the data table
	 * If row is selected we can click on it.
	 * Once clicked on, we can delete transaction from here
	 * 
	 * On Mouse Clicked handles this method in the table*/
    public void getRow(MouseEvent e) {
    	conn = MySqlConnection.ConnectDb();
    	index = tableTransactions.getSelectionModel().getSelectedIndex();
    	
    	if(index <= -1)
    		return;
    	
    	txt_transactionID.setText(col_transactionID.getCellData(index).toString());
    	txt_date.setPromptText(col_date.getCellData(index).toString());
    	txt_description.setText(col_description.getCellData(index).toString());
    	txt_amount.setText(col_amount.getCellData(index).toString());
    }
    
    // Initialize method to set and grab values from table in database
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO Auto-generated method stub
		col_transactionID.setCellValueFactory(new PropertyValueFactory<Account,Integer>("transactionID"));
		col_date.setCellValueFactory(new PropertyValueFactory<Account, Date>("date"));
		col_description.setCellValueFactory(new PropertyValueFactory<Account, String>("description"));
		col_amount.setCellValueFactory(new PropertyValueFactory<Account, Float>("amount"));
		
		lists = MySqlConnection.getAccountData();
		tableTransactions.setItems(lists);
		
	}
	

}
