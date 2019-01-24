package main.java.com.ccprocessor.entity;

/**
 * Abstract Card class. Should be extended by other type of cards.
 * @author sdeshmuk
 *
 */
public abstract class AbstractCard {
		
	private String customerName;
	private String cardNumber;
	
	public AbstractCard(String name, String cardNumber) {
		this.customerName = name;
		this.cardNumber = cardNumber;
	}
	
	public String getName() {
		return customerName;
	}
	public void setName(String name) {
		this.customerName = name;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
