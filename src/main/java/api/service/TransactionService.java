package api.service;

import api.model.Account;
import api.model.Transaction;
import api.model.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*
* Service manages transactions lifecycle
* */
public class TransactionService {

    private static int TRANSACTION_ID = 1;

    /*
     * Method accepts Existing account and amount of money along with type of transaction
     * and creates and returns new transaction
     * */
    public Transaction createTransaction(Account account, long amount, TransactionType type) {
        return new Transaction(TRANSACTION_ID++, account.getId(), amount, type);
    }

    private static List<String> logs = new ArrayList<>();

    /*
     * Method that create a formatted result of account transaction history
     * */
    public String getTransactionHistory(Account account) {
        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("""
                
                ID  Amount  Type
                """);

        String transactions = account.getTransactions().stream()
                .map(tr -> tr.getTransactionId() + "   " + tr.getAmount() + "   " + tr.getType())
                .collect(Collectors.joining("\n"));

        return formattedResult.append(transactions).toString();
    }

    public void addMessageToLogs(String message) {
        logs.add(message);
    }

    public List<String> getLogs() {
        return logs;
    }

}
