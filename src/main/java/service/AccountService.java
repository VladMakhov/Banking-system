package service;

import model.Account;

/*
 * Service that provides functionality to create and manage accounts, logging and validation of parameters
 * */
public interface AccountService {
    /*
     * Method accepts username and password to create new account and saves it to storage.
     * Before creating it checks if account with that username already exist
     * */
    void createAccount(String username, String password);

    /*
    * Method validates input parameters and if account with those attribute exist - returns account
    * */
    Account validateAccount(String username, String password);

    /*
    * Get info about account
    * */
    String getAccountInfo(Account account);
}
