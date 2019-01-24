package main.java.com.ccprocessor.ccvalidator;

/**
 * Luhn Credit Card validator interface.
 * @author sdeshmuk
 *
 */
public interface ILuhn {
	
	/**
	 * Returns true if credit card number is valid and false otherwise.
	 * @param cardNumber
	 * @return
	 */
	public boolean validate(String cardNumber);
}
