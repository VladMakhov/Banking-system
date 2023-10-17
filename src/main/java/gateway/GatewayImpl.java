package gateway;

import model.Account;
import service.AccountService;
import service.FinanceService;
import service.classes.LogService;

import java.util.List;
import java.util.Optional;

/*
* Implementation of dispatcher interface.
* Facade class that connects all services into one.
* */
public class GatewayImpl implements Gateway {

    private final FinanceService financeService;
    private final AccountService accountService;
    private final LogService logService;

    public GatewayImpl(FinanceService financeService, AccountService accountService, LogService logService) {
        this.financeService = financeService;
        this.accountService = accountService;
        this.logService = logService;
    }

    @Override
    public List<String> getLogs() {
        return logService.getLogs();
    }

    @Override
    public void addLog(String message) {
        logService.addLog(message);
    }

    @Override
    public void createAccount(String username, String password) {
        accountService.createAccount(username, password);
    }

    @Override
    public String getAccountInfo(Account account) {
        return accountService.getAccountInfo(account);
    }

    @Override
    public Optional<Account> validateAccount(String username, String password) {
        return accountService.validateAccount(username, password);
    }

    @Override
    public void withdraw(Account account, String unparsed) {
        financeService.withdraw(account, unparsed);
    }

    @Override
    public void deposit(Account account, String unparsed) {
        financeService.deposit(account, unparsed);
    }

    @Override
    public String getTransactionHistory(Account account) {
        return accountService.getTransactionHistory(account);
    }

}
