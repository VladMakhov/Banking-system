package dao;

import model.Account;
import model.Transaction;
import util.DatabaseConnection;

import java.util.List;
import java.util.Optional;

/*
* Data access interface that manages account life cycle
* */
public interface AccountDao extends DatabaseConnection {
    /*
    * Method accepts account to further map it to relational object and save it to database
    * */
    void save(Account account);

    /*
     * Method looking for account with corresponding username.
     * If account does not exist null will be returned
     * */
    Optional<Account> findAccountByUsername(String username);

    /*
     * Method selection account history and returns list of transactions
     * */
    List<Transaction> getAccountHistory(Account account);
}
