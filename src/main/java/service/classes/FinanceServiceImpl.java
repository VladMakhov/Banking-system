package service.classes;

import dao.DAO;
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
    private final DAO dao;

    public FinanceServiceImpl() {
        this.transactionService = new TransactionServiceImpl();
        this.dao = new DAO();
    }

    /*
     * Method that accepts account and amount of money to withdraw money from Account if he got enough
     * */
    @Override
    public void withdraw(Account account, int amount) {
        if (amount > 0) {
            if (account.getBalance() - amount >= 0) {
                dao.executeWithdraw(account, amount);
                account.setBalance(account.getBalance() - amount);
                transactionService.saveTransaction(amount, account.getId(), TransactionType.WITHDRAWAL);
            } else {
                throw new IllegalArgumentException("Not enough money on the account");
            }
        } else {
            throw new NegativeNumberArgumentException("Can not subtract negative value");
        }

    }

    /*
     * Method that accepts account and amount of money to deposit into account
     * */
    @Override
    public void deposit(Account account, int amount) {
        if (amount > 0) {
            dao.executeDeposit(account, amount);
            account.setBalance(account.getBalance() + amount);
            transactionService.saveTransaction(amount, account.getId(), TransactionType.DEPOSIT);
        } else {
            throw new NegativeNumberArgumentException("Can not add negative value");
        }
    }
}

