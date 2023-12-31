package dao;

import model.Account;
import model.Transaction;

import java.util.List;
import java.util.Optional;

/*
* Data access interface that manages account life cycle
* */
public interface AccountDao {
    /*
    * Method accepts account to further map it to relational object and save it to database
    * */
    Account save(Account account);

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
