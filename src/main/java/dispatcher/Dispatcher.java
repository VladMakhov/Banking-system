package dispatcher;

import model.Account;

import java.util.List;

/*
* Dispatcher interface accepts ingoing requests to further distribute them to specific service.
* It allows controller to use only one class to communicate with system instead of importing a bunch of services
* */
public interface Dispatcher {
    /*
    * Manage finances (Finance Service)
    * */
    void deposit(Account account, String amount);
    void withdraw(Account account, String amount);

    /*
    * Manage account life cycle (Account Service)
    * */
    boolean createAccount(String username, String password);
    Account validateAccount(String username, String password);
    String getAccountInfo(Account account);

    /*
    * Manage logging (Log Service)
    * */
    void addLog(String message);
    List<String> getLogs();

    /*
    * Manage transactions (Transaction Service)
    * */
    String getTransactionHistory(Account account);

}
