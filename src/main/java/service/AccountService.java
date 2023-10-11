package service;

import model.Account;


public interface AccountService {
    void createAccount(String username, String password);

    Account getAccountByName(String username);

    String getAccountInfo(Account account);
}
