package main.java.com.ccprocessor.transaction;

/**
 * class to be extended by concrete Transaction types such as debit, credit, new card.
 * @author sdeshmuk
 *
 */
public class Transaction {
	
	String customerName;
	
	public Transaction(String cName) {
		customerName = cName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
