package dispatcher;

import model.Account;

import java.util.List;

/*
* Dispatcher interface accepts ingoing requests to further distribute them to specific service.
* It allows controller to use only one class to communicate with system instead of importing a bunch of services
* */
public interface Dispatcher {
    /*
    * Methods that manages finances
    * */
    void deposit(Account account, String amount);
    void withdraw(Account account, String amount);

    /*
    * Methods that manages account life cycle
    * */
    void createAccount(String username, String password);
    Account validateAccount(String username, String password);
    String getAccountInfo(Account account);

    /*
    * Methods that manages logging
    * */
    void addLog(String message);
    List<String> getLogs();

    /*
    * Methods that manages transactions
    * */
    String getTransactionHistory(Account account);

}
