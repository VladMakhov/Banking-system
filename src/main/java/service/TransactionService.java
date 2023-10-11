package service;

import model.Account;
import model.Transaction;
import model.TransactionType;

import java.util.stream.Collectors;


/*
* Service manages transactions lifecycle
* */
public class TransactionService {

    private static int TRANSACTION_ID = 1;

    private final AccountService accountService;

    public TransactionService() {
        this.accountService = new AccountService();
    }

    /*
     * Method and creates new transaction and adding it to transaction list of account
     * */
    public void createTransaction(long amount, String username, TransactionType type) {
        Transaction transaction = new Transaction(TRANSACTION_ID++, username, amount, type);
        accountService.getAccountByName(username).getTransactions().add(transaction);
    }

    /*
     * Method that create a formatted result of account transaction history
     * */
    public String getTransactionHistory(Account account) {
        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("""
                ID  Amount  Type
                """);

        String transactions = account.getTransactions().stream()
                .map(tr -> tr.transactionId() + "   " + tr.amount() + "   " + tr.type())
                .collect(Collectors.joining("\n"));

        return formattedResult.append(transactions).toString();
    }

}
