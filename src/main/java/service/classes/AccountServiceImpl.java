package service.classes;

import dao.AccountDao;
import dao.classes.AccountDaoImpl;
import model.Account;
import service.AccountService;

import java.util.Optional;
import java.util.stream.Collectors;


public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final LogService logService;

    public AccountServiceImpl() {
        this.accountDao = new AccountDaoImpl();
        this.logService = new LogService();
    }

    @Override
    public void createAccount(String username, String password) {
        try {
            Optional<Account> account = accountDao.findAccountByUsername(username);
            if (account.isEmpty()) {
                accountDao.save(new Account(0, username, password, 0));
                logService.addLog("Account created with name: " + username);
                System.out.println("INFO: Account registered successfully");
            } else {
                throw new RuntimeException("Account with name: " + username + " already exists");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(e.getMessage());
        }
    }

    @Override
    public Optional<Account> validateAccount(String username, String password) {
        try {
            Optional<Account> account = accountDao.findAccountByUsername(username);
            if (account.isPresent()) {
                if (account.get().getPassword().equals(password)) {
                    return account;
                } else {
                    throw new RuntimeException("Wrong password");
                }
            } else {
                throw new RuntimeException("Account with name: " + username + " not found");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public String getTransactionHistory(Account account) {
        var transactions = accountDao.getAccountHistory(account);
        logService.addLog(account.getUsername() + " requested transaction history");
        StringBuilder formattedResult = new StringBuilder();
        formattedResult.append("""
                ID  Amount  Type
                """);

        String body = transactions.stream()
                .map(tr -> String.format("%s    %s    %s", tr.transactionId(), tr.amount(), tr.type()))
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
