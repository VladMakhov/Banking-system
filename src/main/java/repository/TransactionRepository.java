package repository;

import model.Account;
import model.Transaction;

import java.util.List;

public interface TransactionRepository extends DatabaseConnection {
    void saveTransaction(Transaction transaction);
    List<Transaction> getAccountHistory(Account account);
}
