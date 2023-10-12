package service.classes;

import exception.AccountExistException;
import exception.AccountNotFoundException;
import model.Account;
import repository.AccountRepository;
import repository.dao.AccountDao;
import service.AccountService;

/*
 * Service that provides functionality to create and manage accounts.
 * */
public class AccountServiceImpl implements AccountService {

    private static int ACCOUNT_ID = 1;


    private final AccountRepository repository;

    public AccountServiceImpl() {
        this.repository = new AccountDao();
    }

    /*
     * Method accepts username and password to create new account and saves it to storage
     * */
    @Override
    public void createAccount(String username, String password) {
        if (repository.findAccountByUsername(username) == null) {
            repository.save(new Account(ACCOUNT_ID++, username, password, 0));
        } else {
            throw new AccountExistException("Account with name: " + username + " already exists");
        }
    }

    @Override
    public Account validateAccount(String username, String password) {
        Account account = repository.findAccountByUsername(username);
        if (account.getUsername().equals(username)) {
            if (account.getPassword().equals(password)) {
                return account;
            } else {
                throw new IllegalArgumentException("Wrong password");
            }
        } else {
            throw new AccountNotFoundException("Account with name: " + username + " not found");
        }
    }

    @Override
    public String getAccountInfo(Account account) {
        return "Id: " + account.getId() +
                "\nName: " + account.getUsername() +
                "\nBalance: " + account.getBalance();
    }
}
