package dao;

import model.Account;
import model.Transaction;

/*
 * Data access interface that manages finance life cycle
 * */
public interface FinanceDao {
    /*
    * Method adding validated amount to account
    * */
    void deposit(Account account, long amount);

    /*
     * Method withdrawing validated amount from account
     * */
    void withdraw(Account account, long amount);

    /*
     * Method that saves transaction to database
     * */
    void save(Transaction transaction);

}
