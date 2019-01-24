package main.java.com.ccprocessor.entity;

/**
 * Stores Credit Card Information.
 * @author sdeshmuk
 *
 */
public class CreditCard extends AbstractCard {
	

	private int limit;
	private int balance;
	private CreditCardStatus ccStatus;
	
	public CreditCard(String name, String cardNumber, int limit) {
		super(name, cardNumber);
		this.limit = limit;
		balance = 0;
		ccStatus = CreditCardStatus.ACTIVE;
	}
 	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}

	public CreditCardStatus getCreditCardStatus() {
		return ccStatus;
	}

	public void setCreditCardStatus(CreditCardStatus ccStatus) {
		this.ccStatus = ccStatus;
	}	
}
