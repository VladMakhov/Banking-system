package dispatcher;

import exception.AccountExistException;
import exception.AccountNotFoundException;
import model.Account;
import service.AccountService;
import service.LogService;
import service.TransactionService;
import service.FinanceService;

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
        this.financeService = new FinanceService();
        this.accountService = new AccountService();
        this.transactionService = new TransactionService();
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
        try {
            accountService.createAccount(username, password);
        } catch (AccountExistException e) {
            addLog(e.getMessage());
            System.out.println("!!! " + e.getMessage());
        }
    }

    @Override
    public void deposit(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            financeService.deposit(account, amount);
            System.out.println("balance: " + account.getBalance());
            addLog(account.getUsername() + " made deposit transaction on " + amount);
        } catch (NumberFormatException e) {
            System.out.println("!!! Incorrect value");
            addLog(account.getUsername() + " failed deposit transaction");
        } catch (IllegalArgumentException e) {
            System.out.println("!!! Can not add negative value");
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
        } catch (NumberFormatException e) {
            System.out.println("!!! Incorrect value");
            addLog(account.getUsername() + " failed withdrawal transaction");
        } catch (IllegalArgumentException e) {
            System.out.println("!!! Not enough money on the account");
            addLog(account.getUsername() + " failed withdrawal transaction");
        }

    }

    @Override
    public String getAccountInfo(Account account) {
        addLog(account.getUsername() + " requested info");
        return accountService.getAccountInfo(account);
    }

    @Override
    public String getTransactionHistory(Account account) {
        addLog(account.getUsername() + " requested transaction history");
        return transactionService.getTransactionHistory(account);
    }

    @Override
    public Account validateAccount(String username, String password) {
        try {
            Account account = accountService.getAccountByName(username);
            if (Objects.equals(account.getUsername(), username)) {
                if (Objects.equals(account.getPassword(), password)) {
                    addLog(username + " has entered his account");
                    return account;
                } else {
                    System.out.println("!!! Wrong password");
                }
            } else {
                System.out.println("!!! Account does not exist");
            }
        } catch (AccountNotFoundException e) {
            addLog(e.getMessage());
            System.out.println("!!! Account does not exist");
        }
        return null;
    }
}
