package dispatcher;

import model.Account;
import service.AccountService;
import service.FinanceService;
import service.classes.AccountServiceImpl;
import service.classes.FinanceServiceImpl;
import util.LogService;

import java.util.List;


/*
* Implementation of dispatcher interface.
* Facade class that connects all services into one.
* */
public class DispatcherImpl implements Dispatcher {

    private final FinanceService financeService;
    private final AccountService accountService;
    private final LogService logService;

    public DispatcherImpl() {
        this.financeService = new FinanceServiceImpl();
        this.accountService = new AccountServiceImpl();
        this.logService = new LogService();
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
    public Account validateAccount(String username, String password) {
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
