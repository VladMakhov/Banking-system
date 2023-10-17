package gateway;

import model.Account;

import java.util.List;
import java.util.Optional;

/*
* Dispatcher interface accepts ingoing requests to further distribute them to specific service.
* It allows controller to use only one class to communicate with system instead of importing a bunch of services
* */
public interface Gateway {
    /*
    * Manage finances (Finance Service)
    * */
    void deposit(Account account, String amount);
    void withdraw(Account account, String amount);

    /*
    * Manage account life cycle (Account Service)
    * */
    void createAccount(String username, String password);
    Optional<Account> validateAccount(String username, String password);
    String getAccountInfo(Account account);
    String getTransactionHistory(Account account);

    /*
    * Manage logging (Log Service)
    * */
    void addLog(String message);
    List<String> getLogs();

}
