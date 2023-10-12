package service.classes;

import model.Account;
import model.Transaction;
import model.TransactionType;
import repository.TransactionRepository;
import repository.dao.TransactionDao;
import service.AccountService;
import service.TransactionService;

import java.util.stream.Collectors;


/*
* Service manages transactions lifecycle
* */
public class TransactionServiceImpl implements TransactionService {

    private static int TRANSACTION_ID = 1;

    private final AccountService accountService;
    private final TransactionRepository repository;

    public TransactionServiceImpl() {
        this.accountService = new AccountServiceImpl();
        this.repository = new TransactionDao();
    }

    /*
     * Method and creates new transaction and adding it to transaction list of account
     * */
    @Override
    public void saveTransaction(int amount, int accountId, TransactionType type) {
        Transaction transaction = new Transaction(TRANSACTION_ID++, accountId, amount, type);
        repository.saveTransaction(transaction);
    }


    /*
     * Method that create a formatted result of account transaction history
     * */
    @Override
    public String getTransactionHistory(Account account) {
        var transactions = repository.getAccountHistory(account);

        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("""
                ID  Amount  Type
                """);

        String body = transactions.stream()
                .map(tr -> String.format("%s   %s    %s", tr.getTransactionId(), tr.getAmount(), tr.getType()))
                .collect(Collectors.joining("\n"));

        return formattedResult.append(body).toString();
    }

}

