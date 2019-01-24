package main.java.com.ccprocessor.transaction;

public class DebitTransaction extends Transaction {
	
	int amount;
	
	public DebitTransaction(String cName, int amount) {
		super(cName);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
