package api.service;

import api.model.Account;

import java.util.List;
import java.util.Map;

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

    public Account createAccount(String username, String password) {
        return accountService.createAccount(username, password);
    }

    public List<String> getLogs() {
        return transactionService.getLogs();
    }

    public long deposit(Account account, long amount) {
        return walletService.deposit(account, amount);
    }

    public long withdraw(Account account, long amount) {
        return walletService.withdraw(account, amount);
    }

    public String getAccountInfo(Account account) {
        return accountService.getAccountInfo(account);
    }

    public String getTransactionHistory(Account account) {
        return transactionService.getTransactionHistory(account);
    }
}
