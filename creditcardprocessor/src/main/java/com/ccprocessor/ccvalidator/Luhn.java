package main.java.com.ccprocessor.ccvalidator;

/**
 * Validates credit card numbers using Luhn Algorithm.
 * 
 * @author sdeshmuk
 *
 */
public class Luhn implements ILuhn {

	@Override
	public boolean validate(String cardNumber) {

		int sum = 0;
		boolean flag = false;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			int currentDigit = Integer.parseInt(cardNumber.substring(i, i + 1));
			if (flag) {
				currentDigit *= 2;
				if (currentDigit > 9) {
					currentDigit = (currentDigit % 10)+1;
				}
			}
			sum += currentDigit;
			flag = !flag;
		}
		return (sum % 10 == 0);
	}
}
