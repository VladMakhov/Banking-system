package dispatcher;

import model.Account;
import service.AccountService;
import service.FinanceService;
import service.TransactionService;
import service.classes.AccountServiceImpl;
import service.classes.FinanceServiceImpl;
import service.classes.LogService;
import service.classes.TransactionServiceImpl;

import java.util.List;


/*
* Implementation of dispatcher interface.
* Facade class that connects all services into one.
* */
public class DispatcherImpl implements Dispatcher {

    private final FinanceService financeService;
    private final AccountService accountService;
    private final LogService logService;
    private final TransactionService transactionService;

    public DispatcherImpl() {
        this.financeService = new FinanceServiceImpl();
        this.accountService = new AccountServiceImpl();
        this.transactionService = new TransactionServiceImpl();
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
        return transactionService.getTransactionHistory(account);
    }

}
