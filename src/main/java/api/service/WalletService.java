package api.service;

import api.model.Account;
import api.model.TransactionType;

/*
* Service that gives ability to manipulate with account balance
* by making transactions such as deposit (add money) and withdrawal (take money)
* */
public class WalletService {

    private final TransactionService transactionService;

    public WalletService() {
        this.transactionService = new TransactionService();
    }

    /*
    * Method that accepts account and amount of money and withdraw money from Account if he got enough
    * */
    public void withdraw(Account account, long amount) {
        if (account.getBalance() - amount < 0) {
            throw new IllegalArgumentException("Not enough money on the account");
        } else if (amount <= 0) {
            throw new NumberFormatException("Can not subtract negative value");
        }
        account.setBalance(account.getBalance() - amount);
        transactionService.createTransaction(amount, account.getUsername(), TransactionType.WITHDRAWAL);
    }

    /*
    * Method that accepts account and amount of money to deposit and add amount to account
    * */
    public void deposit(Account account, long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Can not add negative value");
        } else {
            account.setBalance(account.getBalance() + amount);
            transactionService.createTransaction(amount, account.getUsername(), TransactionType.DEPOSIT);
        }
    }
}
