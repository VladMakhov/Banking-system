package service;

import model.Account;

/*
 * Service that gives ability to manipulate with account balance
 * by making transactions such as deposit (add money) and withdrawal (take money).
 * Provides logging, validation of parameters and exception handling
 * */
public interface FinanceService {
    /*
     * Method that accepts account and amount of money to deposit into account
     * */
    void deposit(Account account, String amount);

    /*
     * Method that accepts account and amount of money to withdraw money from Account if he got enough
     * */
    void withdraw(Account account, String amount);

}
