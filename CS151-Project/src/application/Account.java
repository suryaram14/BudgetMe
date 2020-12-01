package application;

import java.util.Date;

public class Account {
	// variables for user input
	int transactionID;
	Date date;
	String description;
	String category;
	double amount;
	
	// constructors for variables
	public Account(int transactionID, Date date, String description, String category, double amount) {
		super();
		this.transactionID = transactionID;
		this.date = date;
		this.description = description;
		this.category = category;
		this.amount = amount;
	}
	
	// getters and setters for all variables
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
