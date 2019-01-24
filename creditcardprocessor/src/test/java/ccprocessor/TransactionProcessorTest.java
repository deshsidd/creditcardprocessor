package test.java.ccprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.com.ccprocessor.core.TransactionProcessor;
import main.java.com.ccprocessor.transaction.CreditTransaction;
import main.java.com.ccprocessor.transaction.DebitTransaction;
import main.java.com.ccprocessor.transaction.NewCardTransaction;
import main.java.com.ccprocessor.transaction.Transaction;

public class TransactionProcessorTest {
	/**
	 * Test for the following input :
	 * 
	 *  Add Tom 4111111111111111 $1000
		Add Lisa 5454545454545454 $3000
		Add Quincy 1234567890123456 $2000
		Charge Tom $500
		Charge Tom $800
		Charge Lisa $7
		Credit Lisa $100
		Credit Quincy $200		
	 */
	@Test
	public void declinedChargeTest() {
		
		List<Transaction> txs = new ArrayList<Transaction>();
		txs.add(createNewCardTransaction("Tom", "4111111111111111", 1000));
		txs.add(createNewCardTransaction("Lisa", "5454545454545454", 3000));
		txs.add(createNewCardTransaction("Quincy", "1234567890123456", 2000));
		txs.add(createDebitTransaction("Tom", 500));
		txs.add(createDebitTransaction("Tom", 800));
		txs.add(createDebitTransaction("Lisa", 7));
		txs.add(createCreditTransaction("Lisa", 200));
		
		String expectedOutput = ".......Credit Cards Summary.......\n" + 
				"Lisa: $-193\n" + 
				"Quincy: error\n" + 
				"Tom: $500\n";
		
		testTransactionPorcessorHelper(txs, expectedOutput);
	}
	
	/**
	 * Test for the following input :
	 * 
	 *  Add Tom 4111111111111112 $1000
		Add Lisa 79927398714 $3000
		Add Quincy 1234567890123456 $2000
		Charge Tom $500
		Charge Tom $800
		Charge Lisa $7
		Credit Lisa $100
		Credit Quincy $200		
	 */
	@Test
	public void invalidCreditCardsTest() {
		
		List<Transaction> txs = new ArrayList<Transaction>();
		txs.add(createNewCardTransaction("Tom", "4111111111111112", 1000));
		txs.add(createNewCardTransaction("Lisa", "79927398714", 3000));
		txs.add(createNewCardTransaction("Quincy", "1234567890123456", 2000));
		txs.add(createDebitTransaction("Tom", 500));
		txs.add(createDebitTransaction("Tom", 800));
		txs.add(createDebitTransaction("Lisa", 7));
		txs.add(createCreditTransaction("Lisa", 200));
		
		String expectedOutput = ".......Credit Cards Summary.......\n" + 
				"Lisa: error\n" + 
				"Quincy: error\n" + 
				"Tom: error\n";
		
		testTransactionPorcessorHelper(txs, expectedOutput);
	}
	
	/**
	 * Test for the following input :
	 * 
	 *  Add Tom 4111111111111111 $1000
		Add Lisa 5454545454545454 $3000
		Add Quincy 1234567890123456 $2000
		Charge Tom $500
		Credit Tom $2000
		Charge Lisa $7
		Credit Lisa $100
		Credit Quincy $200		
	 */
	@Test
	public void negativeBalanceTest() {
		
		List<Transaction> txs = new ArrayList<Transaction>();
		txs.add(createNewCardTransaction("Tom", "4111111111111111", 1000));
		txs.add(createNewCardTransaction("Lisa", "5454545454545454", 3000));
		txs.add(createNewCardTransaction("Quincy", "1234567890123456", 2000));
		txs.add(createDebitTransaction("Tom", 500));
		txs.add(createCreditTransaction("Tom", 2000));
		txs.add(createDebitTransaction("Lisa", 7));
		txs.add(createCreditTransaction("Lisa", 200));
		
		String expectedOutput = ".......Credit Cards Summary.......\n" + 
				"Lisa: $-193\n" + 
				"Quincy: error\n" + 
				"Tom: $-1500\n";
		
		testTransactionPorcessorHelper(txs, expectedOutput);
	}
	
	/**
	 * Test for the following input :
	 * 
	 *  Add A 4111111111111111 $1000
		Add B 5454545454545454 $3000
		Add C 1234567890123456 $2000
		Charge A $500
		Credit A $2000
		Charge B $7
		Credit B $100
		Credit C $200		
	 */
	@Test
	public void displayNamesAlphabeticallyTest() {
		
		List<Transaction> txs = new ArrayList<Transaction>();
		txs.add(createNewCardTransaction("A", "4111111111111111", 1000));
		txs.add(createNewCardTransaction("B", "5454545454545454", 3000));
		txs.add(createNewCardTransaction("C", "1234567890123456", 2000));
		txs.add(createDebitTransaction("A", 500));
		txs.add(createCreditTransaction("A", 2000));
		txs.add(createDebitTransaction("B", 7));
		txs.add(createCreditTransaction("B", 200));
		
		String expectedOutput = ".......Credit Cards Summary.......\n" + 
				"A: $-1500\n" + 
				"B: $-193\n" + 
				"C: error\n";
		
		testTransactionPorcessorHelper(txs, expectedOutput);
	}
	
	private void testTransactionPorcessorHelper(List<Transaction> txs, String expectedOutput) {
		TransactionProcessor tp = new TransactionProcessor();
		tp.process(txs);
		String actualOutput = tp.getReport();
		assertEquals(expectedOutput, actualOutput);
	}
	
	private Transaction createCreditTransaction(String cName, int amount) {
		return new CreditTransaction(cName, amount);
	}
	
	private Transaction createDebitTransaction(String cName, int amount) {
		return new DebitTransaction(cName, amount);
	}
	
	private Transaction createNewCardTransaction(String cName, String cardNumber, int limit) {
		return new NewCardTransaction(cName, cardNumber, limit);
	}
}
