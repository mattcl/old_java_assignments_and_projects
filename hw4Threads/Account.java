// Account.java

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
    public static final int STARTING_BALANCE = 1000;
    
	private int id;
	private int balance;
	private int transactions;
	
	// It may work out to be handy for the account to
	// have a pointer to its Bank.
	// (a suggestion, not a requirement)
	private Bank bank;  
	
	public Account(Bank bank, int id) {
	    this(bank, id, STARTING_BALANCE);
	}
	
	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		transactions = 0;
	}
	
	/*
	 * change the balance and return the new balance
	 */
	public synchronized int change(int amt) {
	    transactions++;
	    balance += amt;
	    return balance;
	}
	
	/*
	 * get the number of transactions
	 */
	public synchronized int numTransactions() {
	    return transactions;
	}
	
	/*
	 * get pointer to bank object
	 */
	public Bank getBank() {
	    return bank;
	}
	
	/*
	 * get the string representation
	 */
	public String toString() {
	    return "acct:" + id + " balance:" + balance + " trans:" + transactions;
	}
	
}
