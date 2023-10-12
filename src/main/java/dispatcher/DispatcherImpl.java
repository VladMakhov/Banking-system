package dispatcher;

import exception.AccountExistException;
import exception.AccountNotFoundException;
import exception.NegativeNumberArgumentException;
import model.Account;
import service.AccountService;
import service.FinanceService;
import service.TransactionService;
import service.classes.AccountServiceImpl;
import service.classes.FinanceServiceImpl;
import service.classes.LogService;
import service.classes.TransactionServiceImpl;

import java.util.List;
import java.util.Objects;


/*
* Implementation of dispatcher interface.
* Facade class that connects all services into one, provides logging and exception handling
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
    public boolean createAccount(String username, String password) {
        try {
            accountService.createAccount(username, password);
            addLog("Account created with name: " + username);
            return true;
        } catch (AccountExistException e) {
            addLog(e.getMessage());
            System.out.println("ERROR: " + e.getMessage());
        }
        return false;
    }

    @Override
    public String getAccountInfo(Account account) {
        addLog(account.getUsername() + " requested info");
        return accountService.getAccountInfo(account);
    }

    @Override
    public Account validateAccount(String username, String password) {
        try {
            Account account = accountService.getAccountByName(username);
            if (Objects.equals(account.getPassword(), password)) {
                addLog(username + " has entered his account");
                return account;
            } else {
                System.out.println("ERROR: Wrong password");
            }
        } catch (AccountNotFoundException e) {
            addLog(String.format("%s: %s", username, e.getMessage()));
            System.out.println("ERROR: Account does not exist");
        }
        return null;
    }

    @Override
    public void deposit(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            financeService.deposit(account, amount);
            System.out.println("Balance: " + account.getBalance());
            addLog(account.getUsername() + " made deposit transaction on " + amount);
        } catch (NumberFormatException | NegativeNumberArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            addLog(account.getUsername() + " failed deposit transaction");
        }
    }

    @Override
    public void withdraw(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            financeService.withdraw(account, amount);
            System.out.println("balance: " + account.getBalance());
            addLog(account.getUsername() + " made withdrawing transaction on " + amount);
        } catch (NegativeNumberArgumentException | IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
            addLog(account.getUsername() + " failed withdrawal transaction");
        }

    }

    @Override
    public String getTransactionHistory(Account account) {
        addLog(account.getUsername() + " requested transaction history");
        return transactionService.getTransactionHistory(account);
    }

}
