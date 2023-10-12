package repository;

import model.Account;

public interface FinanceRepository extends DatabaseConnection {
    void deposit(Account account, long amount);
    void withdraw(Account account, long amount);
}
