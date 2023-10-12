package service.classes;

import dao.DAO;
import exception.AccountExistException;
import exception.AccountNotFoundException;
import model.Account;
import service.AccountService;

/*
 * Service that provides functionality to create and manage accounts.
 * */
public class AccountServiceImpl implements AccountService {

    private static int ACCOUNT_ID = 1;


    private final DAO dao;

    public AccountServiceImpl() {
        this.dao = new DAO();
    }

    /*
     * Method accepts username and password to create new account and saves it to storage
     * */
    @Override
    public void createAccount(String username, String password) {
        if (dao.findAccountByName(username) == null) {
            dao.saveAccount(new Account(ACCOUNT_ID++, username, password, 0));
        } else {
            throw new AccountExistException("Account with name: " + username + " already exists");
        }
    }

    @Override
    public Account validateAccount(String username, String password) {
        Account account = dao.findAccountByName(username);
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
