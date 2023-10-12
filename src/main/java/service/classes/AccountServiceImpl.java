package service.classes;

import model.Account;
import repository.AccountRepository;
import repository.dao.AccountDao;
import service.AccountService;
import util.LogService;

import java.util.stream.Collectors;


public class AccountServiceImpl implements AccountService {

    private static int ACCOUNT_ID = 1;

    private final AccountRepository accountRepository;
    private final LogService logService;

    public AccountServiceImpl() {
        this.accountRepository = new AccountDao();
        this.logService = new LogService();
    }

    @Override
    public void createAccount(String username, String password) {
        try {
            if (accountRepository.findAccountByUsername(username) == null) {
                accountRepository.save(new Account(ACCOUNT_ID++, username, password, 0));
                logService.addLog("Account created with name: " + username);
                System.out.println("INFO: Account registered successfully");
            } else {
                System.out.println("INFO: Account with name: " + username + " already exists");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(e.getMessage());
        }
    }

    @Override
    public Account validateAccount(String username, String password) {
        try {
            Account account = accountRepository.findAccountByUsername(username);
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return account;
                } else {
                    System.out.println("ERROR: Wrong password");
                }
            } else {
                System.out.println("INFO: Account with name: " + username + " not found");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: Account does not exist" );
        }
        return null;
    }

    @Override
    public String getTransactionHistory(Account account) {
        var transactions = accountRepository.getAccountHistory(account);
        logService.addLog(account.getUsername() + " requested transaction history");
        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("""
                ID  Amount  Type
                """);

        String body = transactions.stream()
                .map(tr -> String.format("%s    %s    %s", tr.getTransactionId(), tr.getAmount(), tr.getType()))
                .collect(Collectors.joining("\n"));

        return formattedResult.append(body).toString();
    }

    @Override
    public String getAccountInfo(Account account) {
        logService.addLog(account.getUsername() + " requested info");
        return "Id: " + account.getId() +
                "\nName: " + account.getUsername() +
                "\nBalance: " + account.getBalance();
    }

}
