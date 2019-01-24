package main.java.com.ccprocessor.transaction;

public class NewCardTransaction extends Transaction {
	
	private String cardNumber;
	private int limit;
	
	public NewCardTransaction(String cName, String cardNumber, int limit) {
		super(cName);
		this.cardNumber = cardNumber;
		this.limit = limit;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
