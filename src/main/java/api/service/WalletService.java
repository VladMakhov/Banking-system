package api.service;

import api.model.Account;
import api.model.Transaction;
import api.model.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Service that provides functionality to create Account with unique ID, his own balance and List of transactions.
* You can manipulate with account balance by making transactions such as deposit (add money) and withdrawal (take money)
* Transactions have their own unique ID as well.
* */
public class WalletService {

//    Auto incrementing ID`s
    private static int ACCOUNT_ID = 1;
    private static int TRANSACTION_ID = 1;
    public List<String> logs = new ArrayList<>();
    public Map<String, Account> accounts = new HashMap<>();

    public List<String> getLogs() {
        return logs;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    //    You should create accounts through special method
    public Account createAccount(String username, String password) {
        Account account = new Account();

        account.setId(ACCOUNT_ID++);
        account.setUsername(username);
        account.setBalance(0);
        account.setPassword(password);
        account.setTransactions(new ArrayList<>());

        return account;
    }

//    Private method to regulate transactions
    private static Transaction createTransaction(Account account, long amount, TransactionType type) {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(TRANSACTION_ID++);
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setType(type);

        return transaction;
    }

//    Method to withdraw money from Account if he got enough
    public long withdraw(Account account, long amount) {
        if (account.getBalance() - amount < 0) {
            throw new IllegalArgumentException("Not enough money on the account");
        } else if (amount <= 0) {
            throw new NumberFormatException("Can not subtract negative value");
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = createTransaction(account, amount, TransactionType.WITHDRAWAL);
        account.getTransactions().add(transaction);
        return account.getBalance();
    }

//    Method to add money to Account balance
    public long deposit(Account account, long amount) {
        if (amount <= 0) {
            throw new NumberFormatException("Can not add negative value");
        }
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = createTransaction(account, amount, TransactionType.DEPOSIT);
        account.getTransactions().add(transaction);
        return account.getBalance();
    }

//    Get formatted transaction history
    public String getTransactionHistory(Account account) {
        StringBuilder str = new StringBuilder();
        str.append("""
                
                ID  Amount  Type
                """);
        List<String> stringStream = account.getTransactions().stream()
                .map(tr -> tr.getTransactionId() + "   " + tr.getAmount() + "   " + tr.getType()).toList();
        for (int i = stringStream.size() - 1; i >= 0; i--) {
            str.append(stringStream.get(i)).append("\n");
        }
        return str.toString();
    }

//    Get formatted Account info
    public String getAccountInfo(Account account) {
        return "Id: " + account.getId() +
                "\nName: " + account.getUsername() +
                "\nBalance: " + account.getBalance();
    }
}
