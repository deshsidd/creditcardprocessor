package main.java.com.ccprocessor.core;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import main.java.com.ccprocessor.ccvalidator.Luhn;
import main.java.com.ccprocessor.entity.CreditCard;
import main.java.com.ccprocessor.entity.CreditCardStatus;
import main.java.com.ccprocessor.transaction.CreditTransaction;
import main.java.com.ccprocessor.transaction.DebitTransaction;
import main.java.com.ccprocessor.transaction.NewCardTransaction;
import main.java.com.ccprocessor.transaction.Transaction;

/**
 * Handles processing of the transactions
 * @author sdeshmuk
 *
 */
public class TransactionProcessor {
	
    TreeSet<String> names;
	HashMap<String,CreditCard> nameToCardMap;
	
	public TransactionProcessor() {
		names = new TreeSet<String>();
		nameToCardMap = new HashMap<String, CreditCard>();
	}
	
 	/**
	 * Process a list of transactions.
	 * @param list
	 */
	public void process(List<Transaction> list) {
		for (Transaction t : list) {
			process(t);
		}
	}
	
	/**
	 * Process the given transaction.
	 * @param t
	 */
	public void process(Transaction t) {
		if (t instanceof NewCardTransaction) {
			processNewCard((NewCardTransaction) t);
		} else if (t instanceof DebitTransaction) {
			processDebit((DebitTransaction) t);
		} else if (t instanceof CreditTransaction) {
			processCredit((CreditTransaction) t);
		} else {
			// No logic to process the given transaction.
		}
	}
	
	/**
	 * Prints out a summary of all credit cards.
	 */
	public void generateReport() {
		System.out.println(".......Credit Cards Summary.......");
		for (String name : names) {
			System.out.print(name + ": ");
			CreditCard cc = nameToCardMap.get(name);
			if (cc.getCreditCardStatus().equals(CreditCardStatus.ERROR)) {
				System.out.println("error");
			} else {
				System.out.println("$"+cc.getBalance());
			}
		}
	}
	
	/**
	 * Gets report as String
	 */
	public String getReport() {
		StringBuffer sb = new StringBuffer();
		sb.append(".......Credit Cards Summary.......\n");
		for (String name : names) {
			sb.append(name + ": ");
			CreditCard cc = nameToCardMap.get(name);
			if (cc.getCreditCardStatus().equals(CreditCardStatus.ERROR)) {
				sb.append("error\n");
			} else {
				sb.append("$"+cc.getBalance()+"\n");
			}
		}
		return sb.toString();
	}
		
	private void processNewCard(NewCardTransaction nct) {
		// If existing customer is making a new card request we update their current card
		// validate
		Luhn validator = new Luhn();
		boolean isValid = validator.validate(nct.getCardNumber());
		String name = nct.getCustomerName();
		if (names.contains(name)) {
			// existing customer
			if (isValid) {
				// update card number and limit
				CreditCard cc = nameToCardMap.get(name);
				cc.setCardNumber(nct.getCardNumber());
				cc.setCreditCardStatus(CreditCardStatus.ACTIVE);
				cc.setLimit(nct.getLimit());
			} else {
				// if card number is invalid, do not update the card
			}
		} else {
			// new customer
			names.add(name);
			CreditCard cc = new CreditCard(name, nct.getCardNumber(), nct.getLimit());			
			if (!isValid) {
				cc.setCreditCardStatus(CreditCardStatus.ERROR);
			}
			nameToCardMap.put(name, cc);
		}
	} 
	
	private void processDebit(DebitTransaction dt) {
		String name = dt.getCustomerName();
		// card does not exist
		if (!nameToCardMap.containsKey(name)) {
			// customer with name for transaction does not exist. Invalid transaction.
			return;
		}
		CreditCard cc = nameToCardMap.get(name);
		// card is in error state
		if (cc.getCreditCardStatus().equals(CreditCardStatus.ERROR)) {
			return;
		}
		int balance = cc.getBalance();
		int amount = dt.getAmount();
		if (balance + amount > cc.getLimit()) {
			// decline transaction. no change to account. return.
			return;
		} else {
			// Add debit amount to balance
			cc.setBalance(balance + amount);
		}
	}
	
	private void processCredit(CreditTransaction ct) {
		String name = ct.getCustomerName();
		if (!nameToCardMap.containsKey(name)) {
			// customer with name for transaction does not exist. Invalid transaction.
			return;
		}
		CreditCard cc = nameToCardMap.get(name);
		// card is in error state
		if (cc.getCreditCardStatus().equals(CreditCardStatus.ERROR)) {
			return;
		}
		// remove credit amount from balance
		int balance = cc.getBalance();
		int amount = ct.getAmount();
		cc.setBalance(balance - amount);
	}
 }
