package api.service;

import api.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
* Service that provides functionality to create Account with unique ID, his own balance and List of transactions.
* */
public class AccountService {

    private static int ACCOUNT_ID = 1;
    private static Map<String, Account> accounts = new HashMap<>();

    /*
     * Method accepts username and password to create and return new account
     * */
    public Account createAccount(String username, String password) {
        Account account = new Account(ACCOUNT_ID++, username, password);
        account.setTransactions(new ArrayList<>());
        return account;
    }

    public void addAccountToStorage(String username, Account account) {
        accounts.put(username, account);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public String getAccountInfo(Account account) {
        return "Id: " + account.getId() +
                "\nName: " + account.getUsername() +
                "\nBalance: " + account.getBalance();
    }
}
