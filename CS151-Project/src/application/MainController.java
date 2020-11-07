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
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable{

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
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null; 
    
    public void addTransaction() {
    	conn = MySqlConnection.ConnectDb();
    	String sqlAdd = "insert into Account (transactionID, date, description, amount) values(?,?,?,?)";
    	try {
    		ps = conn.prepareStatement(sqlAdd);
    		ps.setString(1, txt_transactionID.getText());
    		ps.setString(2, txt_date.getValue().toString());
    		ps.setString(3, txt_description.getText());
    		ps.setString(4, txt_amount.getText());
    		ps.execute();
    		
    		
    		Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
    		alertSuccess.showAndWait();
    		UpdateTransaction();
    	}catch(Exception e){
    		Alert alertError = new Alert(AlertType.ERROR);
    		alertError.showAndWait();
    	}
    }
    
    public void UpdateTransaction(){
    	col_transactionID.setCellValueFactory(new PropertyValueFactory<Account,Integer>("transactionID"));
    	col_date.setCellValueFactory(new PropertyValueFactory<Account,Date>("date"));
    	col_description.setCellValueFactory(new PropertyValueFactory<Account,String>("description"));
    	col_amount.setCellValueFactory(new PropertyValueFactory<Account,Float>("amount"));
        
        lists = MySqlConnection.getAccountData();
        tableTransactions.setItems(lists);
    }
    
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
