package application;

import java.util.Date;

public class Account {
	int transactionID;
	Date date;
	String description;
	float amount;
	
	public Account(int transactionID, Date date, String description, float amount) {
		super();
		this.transactionID = transactionID;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
	
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
	
}
