// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	
	private Account[] accounts;
	private Buffer buffer;
	
	private ArrayList<String> badTransactions;
	private Object badTransactionLock;
	
	private boolean willTrackBad;
	private int limit;
	
	public Bank() {
	    this(0);
	    willTrackBad = false;
	}
	
	public Bank(int limit) {
	    buffer = new Buffer();
	    initializeAccounts();
	    badTransactions = new ArrayList<String>();
	    badTransactionLock = new Object();
	    this.limit = limit;
	    willTrackBad = true;
	    
	}
	
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				buffer.add(new Transaction(from, to, amount));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) {
	    // fork off the workers
	    Thread[] workers = new Thread[numWorkers];
	    for(int i = 0; i < numWorkers; i++) {
	        workers[i] = new Thread(new Worker());
	        workers[i].start();
	    }
	    
	    // read the file
	    readFile(file);
	    
	    // add a null for each worker
	    for(int i = 0; i < numWorkers; i++)
	        buffer.add(null);
	    
	    // Join all of the workers
	    try {
	        for(int i = 0; i < numWorkers; i++) {
	            workers[i].join();
	        }
	    } catch (Exception ignored) {}
	}

	/*
	 * gets the limit
	 */
	public int getLimit() {
	    return limit;
	}
	
	/*
	 * indicates if the bank will track bad transactions
	 */
	public boolean willTrackBad() {
	    return willTrackBad;
	}
	
	/*
	 * adds a bad transaction
	 */
	public void addBadTransaction(int from, int to, int amount, int balance) {
	    synchronized(badTransactionLock) {
	        badTransactions.add("from:" + from + " to:" + to + " amt:" + amount + " bal:" + balance);
	    }
	}
	
	/**
	 * prints the summary of the accounts in the bank
	 */
	public void summary() {
	    for(int i = 0; i < ACCOUNTS; i++)
	        System.out.println(accounts[i].toString());
	    
	    if(willTrackBad) {
	        System.out.println("Bad transactions...");
	        for(int i = 0; i < ACCOUNTS; i++)
	            System.out.println(badTransactions.get(i));
	    }
	}
	
	
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		int limit = 0;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
			
			if(args.length >= 3) {
			    limit = Integer.parseInt(args[2]);
			}
		}
		
		// YOUR CODE HERE
		Bank bank = new Bank(limit);
		long startTime = System.currentTimeMillis();
		bank.processFile(file, numWorkers);
		long endTime = System.currentTimeMillis();
		bank.summary();
		
		System.out.println("Done");
		System.out.println("Took " + (endTime - startTime) + "ms");
	}
	
	// -------------- Private ---------------- //
	private void initializeAccounts() {
	    accounts = new Account[ACCOUNTS];
	    for(int i = 0; i < ACCOUNTS; i++)
	        accounts[i] = new Account(this, i);
	}
	
	private class Worker implements Runnable {
	    
	    /*
	     * loop the worker executes
	     */
	    public void run() {
	        Transaction t;
	        while((t = buffer.remove()) != null) {
	            int amount = t.amount;
	            int from = t.from;
	            int to = t.to;
	            Bank bank = accounts[from].getBank();
	            
	            int bal = accounts[from].change(-amount);
	            if(bank.willTrackBad() && bal < bank.getLimit()) bank.addBadTransaction(from, to, amount, bal);
	            
	            accounts[to].change(amount);
	        }
	    }
	}
}

