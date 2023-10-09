package api.model;

import java.util.ArrayList;
import java.util.List;

/*
* Blueprint of a Player that regulates through the Wallet Service
*  */
public class Account {
    private final int id;
    private final String username;
    private final String password;
    private long balance;
    private List<Transaction> transactions;

    public Account(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
