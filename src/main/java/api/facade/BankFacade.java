package api.facade;

import api.model.Account;

import java.util.List;

public interface BankFacade {
    void deposit(Account account, String amount);
    void withdraw(Account account, String amount);

    void createAccount(String username, String password);
    Account validateAccount(String username, String password);
    String getAccountInfo(Account account);

    void addLog(String message);
    List<String> getLogs();

    String getTransactionHistory(Account account);

}
