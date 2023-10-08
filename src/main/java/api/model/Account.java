package api.model;

import java.util.ArrayList;
import java.util.List;

/*
* Blueprint of a Player that regulates through the Wallet Service
*  */
public class Account {
    private int id;
    private String username;
    private String password;
    private long balance = 0;
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var t : transactions) {
            stringBuilder.append(t);
        }
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", transactions=" + stringBuilder +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
