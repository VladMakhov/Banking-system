package service;

import model.Account;
import model.TransactionType;

public interface TransactionService {
    void saveTransaction(int amount, int accountId, TransactionType type);
    String getTransactionHistory(Account account);
}
