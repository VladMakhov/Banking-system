package model;


public class Account {
    private int id;
    private final String username;
    private final String password;
    private long balance;

    public Account(int id, String username, String password, int balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public Account(String username, String password, long balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

}
