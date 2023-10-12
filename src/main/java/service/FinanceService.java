package service;

import model.Account;

public interface FinanceService {
    void deposit(Account account, int amount);
    void withdraw(Account account, int amount);
}
