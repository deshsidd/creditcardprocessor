package main.java.com.ccprocessor.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import main.java.com.ccprocessor.transaction.CreditTransaction;
import main.java.com.ccprocessor.transaction.DebitTransaction;
import main.java.com.ccprocessor.transaction.NewCardTransaction;
import main.java.com.ccprocessor.transaction.Transaction;
import test.java.ccprocessor.LuhnTest;
import test.java.ccprocessor.TransactionProcessorTest;

public class Main {

	public static void main(String[] args) {
		
		// Run automated tests
		runAutomatedTests();
		
		List<Transaction> txs = null;
		if(args.length > 0) {
			// Take input from file
			try {
			txs = readFromFile(args[0]);           
			} catch (Exception e) {
				System.out.println("ERROR OCCURRED : " + e.getMessage());
				System.out.println("STACK TRACE : \n");
				e.printStackTrace();
				System.exit(0);
			}
        } else {
        	// Take input from stdin       
        	try {
        		txs = readFromCommandLine();
	        } catch (Exception e) {
				System.out.println("ERROR OCCURRED : " + e.getMessage());
				System.out.println("STACK TRACE : \n\n");
				e.printStackTrace();
				System.exit(0);
			}
        }
		if (txs == null) {
			// txs should not be null here
			System.out.println("ERROR OCCURRED : Please contact developer for details");
			System.exit(0);
		}
		TransactionProcessor tp = new TransactionProcessor();
		tp.process(txs);
		tp.generateReport();
	}

	private static List<Transaction> readFromFile(String filename) throws IOException, IllegalArgumentException {
		List<Transaction> txs = new ArrayList<Transaction>();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = br.readLine().trim();
		while(line != null) {
			Transaction tx = processLine(line);
			if (tx == null) break;
			txs.add(tx);
			line = br.readLine();
		}
		br.close();
		return txs;
	}

	private static List<Transaction> readFromCommandLine() throws IllegalArgumentException{
		System.out.println("Please enter your input:");
		List<Transaction> txs = new ArrayList<Transaction>();
		Scanner scanner = new Scanner(System.in);
    	while(scanner.hasNextLine()) {
    		String line = scanner.nextLine().trim();
    		Transaction tx = processLine(line);
			if (tx == null) break;
    		txs.add(tx);
    	}   	   	
    	scanner.close();
		return txs;
	}
	
	private static Transaction processLine(String line) throws IllegalArgumentException{
		String[] words = line.split("\\s+");
		String command = words[0].toLowerCase();
		Transaction tx = null;
		if (command.equalsIgnoreCase(Constants.ADD_CARD_COMMAND)) {
			String cName = words[1];
			String cardNumber = words[2];
			String limit = words[3].substring(1);
			int limitInt = Integer.valueOf(limit);				
			tx = new NewCardTransaction(cName, cardNumber, limitInt);
		} else if (command.equalsIgnoreCase(Constants.CHARGE_COMMAND)) {
			String cName = words[1];
			String amount = words[2].substring(1);
			int amountInt = Integer.valueOf(amount);
			tx = new DebitTransaction(cName, amountInt);
		} else if (command.equalsIgnoreCase(Constants.CREDIT_COMMAND)) {
			String cName = words[1];
			String amount = words[2].substring(1);
			int amountInt = Integer.valueOf(amount);
			tx = new CreditTransaction(cName, amountInt);
		} else if (command.equalsIgnoreCase(Constants.END_COMMAND)) {
			// end the processing.
			return tx;
		} else {
			throw new IllegalArgumentException("Input provided is incorrect or in the wrong format.");
		}
		return tx;
	}	
	
	private static void runAutomatedTests() {
		System.out.println("Running automated tests...");
		JUnitCore junit = new JUnitCore();
		Result result = junit.run(LuhnTest.class, TransactionProcessorTest.class);
		if (result.wasSuccessful()) {
			System.out.println(result.getRunCount() + " tests successfully executed.\n");
		} else {
			System.out.println(result.getFailureCount() + "/" +result.getRunCount() + " tests failed.\n");
		}
	}
}
