package api;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String username;
    private long balance = 0;
    private List<Transaction> transactions = new ArrayList<>();

    public Player() {
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
