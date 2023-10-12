package repository;

import model.Account;

public interface AccountRepository extends DatabaseConnection {
    void save(Account account);
    Account findAccountByUsername(String username);
}
