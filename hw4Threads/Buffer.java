// Buffer.java

import java.util.*;
import java.util.concurrent.Semaphore;

/*
 Holds the transactions for the worker
 threads.
 
*/
public class Buffer {
	public static final int SIZE = 64;
	
	private ArrayList<Transaction> transactions;
	
	private Semaphore canAdd;
	private Semaphore canRemove;
	private Object alterLock;
	
	/**
	 * Constructor
	 */
	public Buffer() {
	    transactions = new ArrayList<Transaction>();
	    canAdd = new Semaphore(SIZE);
	    canRemove = new Semaphore(0);
	    alterLock = new Object();
	}
	
	/**
	 * Adds a transaction to the buffer
	 * @param t the transaction to add
	 */
	public void add(Transaction t) {
	    // check if we can add (buffer is not full)
	    try {
            canAdd.acquire();
        } catch (InterruptedException ignored) {}
        
        // synchronize so as not to try to add 2+ objects at the same time
        synchronized(alterLock) {
	        transactions.add(t);
        }
	    
	    // signal to remove that there's at least one element in the buffer
	    canRemove.release();
	}
	
	/**
	 * Gets the first transaction in the buffer
	 * @return the first transaction
	 */
	public Transaction remove() {
	    // check if there's an item to remove
	    try {
            canRemove.acquire();
        } catch (InterruptedException ignored) {}
        
        Transaction t;
        
        // synchronize the change of the ArrayList
        synchronized(alterLock) {
            t = transactions.remove(0);
        }
        
        // signal that we have removed an element
        canAdd.release();
        return t;
	}
}
