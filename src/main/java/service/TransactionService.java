package service;

import model.Account;
import model.TransactionType;

/*
 * Service manages transactions lifecycle.
 * Provides logging, validation of parameters and exception handling
 * */
public interface TransactionService {
    /*
     * Method and creates new transaction and adding it to transaction list of account
     * */
    void saveTransaction(int amount, int accountId, TransactionType type);

    /*
     * Method that create a formatted result of account transaction history
     * */
    String getTransactionHistory(Account account);
}
