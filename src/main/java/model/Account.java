package model;


public class Account {
    private final int id;
    private final String username;
    private final String password;
    private int balance;

    public Account(int id, String username, String password, int balance) {
        this.id = id;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
