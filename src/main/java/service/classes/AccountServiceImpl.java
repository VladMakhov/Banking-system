package service.classes;

import exception.AccountExistException;
import exception.AccountNotFoundException;
import model.Account;
import service.AccountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Service that provides functionality to create and manage accounts.
 * */
public class AccountServiceImpl implements AccountService {

    private static int ACCOUNT_ID = 1;
    private static final Map<String, Account> accounts = new HashMap<>();

    /*
     * Method accepts username and password to create new account and saves it to storage
     * */
    @Override
    public void createAccount(String username, String password) {
        if (!accounts.containsKey(username)) {
            addAccountToStorage(new Account(ACCOUNT_ID++, username, password, 0, new ArrayList<>()));
        } else {
            throw new AccountExistException("Account with name: " + username + " already exist");
        }
    }

    @Override
    public Account getAccountByName(String username) {
        if (accounts.containsKey(username)) {
            return accounts.get(username);
        } else {
            throw new AccountNotFoundException("Account with name: " + username + " not found");
        }
    }

    @Override
    public String getAccountInfo(Account account) {
        return "Id: " + account.getId() +
                "\nName: " + account.getUsername() +
                "\nBalance: " + account.getBalance();
    }

    private static void addAccountToStorage(Account account) {
        accounts.put(account.getUsername(), account);
    }

}
