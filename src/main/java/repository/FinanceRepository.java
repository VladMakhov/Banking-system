package repository;

import model.Account;
import model.Transaction;
import util.DatabaseConnection;


/*
 * Data access interface that manages finance life cycle
 * */
public interface FinanceRepository extends DatabaseConnection {
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
