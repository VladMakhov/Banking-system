package repository;

import model.Account;
import model.Transaction;

import java.util.List;

/*
 * Data access interface that manages transaction life cycle
 * */
public interface TransactionRepository extends DatabaseConnection {
    /*
    * Method saves transaction to database
    * */
    void saveTransaction(Transaction transaction);

    /*
    * Method selection account history and returns list of transactions
    * */
    List<Transaction> getAccountHistory(Account account);
}
