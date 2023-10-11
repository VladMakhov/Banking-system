package dispatcher;

import exception.AccountExistException;
import exception.AccountNotFoundException;
import model.Account;
import service.classes.AccountServiceImpl;
import service.classes.LogService;
import service.classes.TransactionServiceImpl;
import service.classes.FinanceServiceImpl;

import java.util.List;
import java.util.Objects;


/*
* Implementation of dispatcher interface.
* Facade class that connects all services into one, provides logging and exception handling
* */
public class DispatcherImpl implements Dispatcher {

    private final FinanceServiceImpl financeServiceImpl;
    private final AccountServiceImpl accountServiceImpl;
    private final LogService logService;
    private final TransactionServiceImpl transactionServiceImpl;

    public DispatcherImpl() {
        this.financeServiceImpl = new FinanceServiceImpl();
        this.accountServiceImpl = new AccountServiceImpl();
        this.transactionServiceImpl = new TransactionServiceImpl();
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
            accountServiceImpl.createAccount(username, password);
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
        return accountServiceImpl.getAccountInfo(account);
    }

    @Override
    public Account validateAccount(String username, String password) {
        try {
            Account account = accountServiceImpl.getAccountByName(username);
            if (Objects.equals(account.getPassword(), password)) {
                addLog(username + " has entered his account");
                return account;
            } else {
                System.out.println("ERROR: Wrong password");
            }
        } catch (AccountNotFoundException e) {
            addLog(e.getMessage());
            System.out.println("ERROR: Account does not exist");
        }
        return null;
    }

    @Override
    public void deposit(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            financeServiceImpl.deposit(account, amount);
            System.out.println("balance: " + account.getBalance());
            addLog(account.getUsername() + " made deposit transaction on " + amount);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect value");
            addLog(account.getUsername() + " failed deposit transaction");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Can not add negative value");
            addLog(account.getUsername() + " failed deposit transaction");
        }
    }

    @Override
    public void withdraw(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            financeServiceImpl.withdraw(account, amount);
            System.out.println("balance: " + account.getBalance());
            addLog(account.getUsername() + " made withdrawing transaction on " + amount);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Incorrect value");
            addLog(account.getUsername() + " failed withdrawal transaction");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Not enough money on the account");
            addLog(account.getUsername() + " failed withdrawal transaction");
        }

    }

    @Override
    public String getTransactionHistory(Account account) {
        addLog(account.getUsername() + " requested transaction history");
        return transactionServiceImpl.getTransactionHistory(account);
    }

}
