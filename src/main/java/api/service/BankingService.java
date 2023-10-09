package api.service;

import api.model.Account;

import java.util.List;
import java.util.Map;


/*
* Main facade class that connects all services into one and provides logging
* */
public class BankingService {
    private final WalletService walletService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public BankingService() {
        this.walletService = new WalletService();
        this.accountService = new AccountService();
        this.transactionService = new TransactionService();
    }

    public Map<String, Account> getAccounts() {
        return accountService.getAccounts();
    }

    public Account getAccountByName(String username) {
        return accountService.getAccounts().get(username);
    }

    public Account createAccount(String username, String password) {
        if (accountService.getAccounts().containsKey(username)) {
            System.out.println("!!! Choose different name");
        } else {
            addAccountToStorage(username, accountService.createAccount(username, password));
            addToLogs("Account created with name: " + username);
        }
        return accountService.createAccount(username, password);
    }

    public List<String> getLogs() {
        return transactionService.getLogs();
    }

    public void addToLogs(String message) {
        transactionService.addMessageToLogs(message);
    }

    public void deposit(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            if (amount >= 0) {
                walletService.deposit(account, amount);
                System.out.println("balance: " + account.getBalance());
                addToLogs(account.getUsername() + " made deposit transaction on " + amount);
            } else {
                System.out.println("!!! Deposit can not be negative");
            }
        } catch (NumberFormatException e) {
            System.out.println("!!! Incorrect value");
            addToLogs(account.getUsername() + " failed deposit transaction");
        }
    }

    public void withdraw(Account account, String unparsed) {
        try {
            long amount = Integer.parseInt(unparsed);
            if (amount >= 0) {
                if (account.getBalance() - amount >= 0) {
                    walletService.withdraw(account, amount);
                    System.out.println("balance: " + account.getBalance());
                    addToLogs(account.getUsername() + " made withdrawing transaction on " + amount);
                } else {
                    System.out.println("!!! Not enough money on your account");
                    addToLogs(account.getUsername() + " failed deposit transaction");
                }
            } else {
                System.out.println("!!! Withdrawal can not be negative");
                addToLogs(account.getUsername() + " failed deposit transaction");
            }
        } catch (NumberFormatException e) {
            System.out.println("!!! Incorrect value");
            addToLogs(account.getUsername() + " failed withdrawal transaction");
        }

    }

    public String getAccountInfo(Account account) {
        addToLogs(account.getUsername() + " requested info");
        return accountService.getAccountInfo(account);
    }

    public String getTransactionHistory(Account account) {
        addToLogs(account.getUsername() + " requested transaction history");
        return transactionService.getTransactionHistory(account);
    }

    public void addAccountToStorage(String username, Account account) {
        accountService.addAccountToStorage(username, account);
        System.out.println("// Account is created");
        addToLogs("Account was created: " + username);
    }

    public Account loginAccount(String username, String password) {
        if (getAccounts().containsKey(username)) {
            if (getAccounts().get(username).getPassword().equals(password)) {
                addToLogs(username + " has entered his account");
                return getAccountByName(username);
            } else {
                System.out.println("!!! Wrong password");
            }
        } else {
            System.out.println("!!! Account does not exist");
        }
        return null;
    }
}
