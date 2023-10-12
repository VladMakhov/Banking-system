package service.classes;

import model.Account;
import repository.AccountRepository;
import repository.dao.AccountDao;
import service.AccountService;


public class AccountServiceImpl implements AccountService {

    private static int ACCOUNT_ID = 1;

    private final AccountRepository repository;
    private final LogService logService;

    public AccountServiceImpl() {
        this.repository = new AccountDao();
        this.logService = new LogService();
    }

    @Override
    public void createAccount(String username, String password) {
        try {
            if (repository.findAccountByUsername(username) == null) {
                repository.save(new Account(ACCOUNT_ID++, username, password, 0));
                logService.addLog("Account created with name: " + username);
                System.out.println("Account registered successfully");
            } else {
                System.out.println("Account with name: " + username + " already exists");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(e.getMessage());
        }
    }

    @Override
    public Account validateAccount(String username, String password) {
        try {
            Account account = repository.findAccountByUsername(username);
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return account;
                } else {
                    System.out.println("Wrong password");
                }
            } else {
                System.out.println("Account with name: " + username + " not found");
            }
        } catch (RuntimeException e) {
            System.out.println("ERROR: " + e.getMessage());
            logService.addLog(e.getMessage());
        }
        return null;
    }

    @Override
    public String getAccountInfo(Account account) {
        logService.addLog(account.getUsername() + " requested info");
        return "Id: " + account.getId() +
                "\nName: " + account.getUsername() +
                "\nBalance: " + account.getBalance();
    }
}
