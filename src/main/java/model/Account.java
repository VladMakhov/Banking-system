package model;

import java.util.Objects;


public class Account {
    private final int id;
    private final String username;
    private final String password;
    private long balance;

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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && balance == account.balance && Objects.equals(username, account.username) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, balance);
    }
}
