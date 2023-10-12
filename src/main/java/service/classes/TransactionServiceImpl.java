package service.classes;

import dao.DAO;
import model.Account;
import model.Transaction;
import model.TransactionType;
import service.AccountService;
import service.TransactionService;

import java.util.stream.Collectors;


/*
* Service manages transactions lifecycle
* */
public class TransactionServiceImpl implements TransactionService {

    private static int TRANSACTION_ID = 1;

    private final AccountService accountService;
    private final DAO dao;

    public TransactionServiceImpl() {
        this.accountService = new AccountServiceImpl();
        this.dao = new DAO();
    }

    /*
     * Method and creates new transaction and adding it to transaction list of account
     * */
    @Override
    public void saveTransaction(int amount, int accountId, TransactionType type) {
        Transaction transaction = new Transaction(TRANSACTION_ID++, accountId, amount, type);
        dao.saveTransaction(transaction);
    }


    /*
     * Method that create a formatted result of account transaction history
     * */
    @Override
    public String getTransactionHistory(Account account) {
        var transactions = dao.findAccountHistory(account);

        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("""
                ID  Amount  Type
                """);

        String body = transactions.stream()
                .map(tr -> String.format("%s   %s    %s", tr.getId(), tr.getAmount(), tr.getType()))
                .collect(Collectors.joining("\n"));

        return formattedResult.append(body).toString();
    }

}

