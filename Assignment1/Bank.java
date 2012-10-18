/**
 * 
 */

/**
 * @author admin
 *
 */
public class Bank {

    private double balance;
    
    public void setBalance() {
        balance = 5000;
    }   
}



class MyClass {
    Bank myBank = new Bank();
    
    public void run() {
        myBank.balance = 10000;
    }
}