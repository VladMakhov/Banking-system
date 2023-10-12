package service.classes;

import exception.NegativeNumberArgumentException;
import model.Account;
import model.TransactionType;
import service.FinanceService;
import service.TransactionService;

/*
* Service that gives ability to manipulate with account balance
* by making transactions such as deposit (add money) and withdrawal (take money)
* */
public class FinanceServiceImpl implements FinanceService {

    private final TransactionService transactionService;

    public FinanceServiceImpl() {
        this.transactionService = new TransactionServiceImpl();
    }

    /*
    * Method that accepts account and amount of money to withdraw money from Account if he got enough
    * */
    @Override
    public void withdraw(Account account, long amount) {
        if (account.getBalance() - amount < 0) {
            throw new IllegalArgumentException("Not enough money on the account");
        } else if (amount <= 0) {
            throw new NegativeNumberArgumentException("Can not subtract negative value");
        }
        account.setBalance(account.getBalance() - amount);
        transactionService.createTransaction(amount, account.getUsername(), TransactionType.WITHDRAWAL);
    }

    /*
    * Method that accepts account and amount of money to deposit into account
    * */
    @Override
    public void deposit(Account account, long amount) {
        if (amount <= 0) {
            throw new NegativeNumberArgumentException("Can not add negative value");
        } else {
            account.setBalance(account.getBalance() + amount);
            transactionService.createTransaction(amount, account.getUsername(), TransactionType.DEPOSIT);
        }
    }
}

