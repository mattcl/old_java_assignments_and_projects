
public class User {
    public static String getPrettyTime(long time) {
        
        return null;
    }
    
    private String username;
    private String password;
    private String hash;
    private long timeForCrack;
    
    public User() {
        this("-username-", "");
    }
    
    public User(String username, String hash) {
        this.username = username;
        this.hash = hash;
        password = "";
        timeForCrack = -1l;
    }
    
    public void setPassword(String pass) {
        password = pass;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public String getHash() {
        return hash;
    }
    
    public void setTimeForCrack(long time) {
        timeForCrack = time;
    }
    
    public long getTimeForCrack() {
        return timeForCrack;
    }
    
    public boolean isCracked() {
        return !password.equals("");
    }
}
