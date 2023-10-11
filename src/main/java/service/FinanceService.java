package service;

import model.Account;

public interface FinanceService {
    void deposit(Account account, long amount);

    void withdraw(Account account, long amount);
}
