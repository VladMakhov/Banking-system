package service;

import model.Account;

public interface FinanceService {
    void deposit(Account account, String amount);
    void withdraw(Account account, String amount);
}
