package repository;

import model.Account;

/*
* Data access interface that manages account life cycle
* */
public interface AccountRepository extends DatabaseConnection {
    /*
    * Method accepts account to further map it to relational object and save it to database
    * */
    void save(Account account);

    /*
     * Method looking for account with corresponding username.
     * If account does not exist null will be returned
     * */
    Account findAccountByUsername(String username);
}
