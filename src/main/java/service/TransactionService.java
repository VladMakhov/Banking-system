package service;

import model.Account;
import model.TransactionType;

public interface TransactionService {
    void createTransaction(long amount, String username, TransactionType type);

    String getTransactionHistory(Account account);
}
