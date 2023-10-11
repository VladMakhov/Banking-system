package unit;


import dispatcher.Dispatcher;
import dispatcher.DispatcherImpl;
import model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;


public class DispatcherTest {

    static Dispatcher dispatcher = new DispatcherImpl();

    static Account account;

    @BeforeAll
    static void init() {
        dispatcher.createAccount("test", "test");
        account = dispatcher.validateAccount("test", "test");
    }

    @BeforeEach
    void eraseBalance() {
        account.setBalance(0);
    }

    @Test
    public void createAccount_returns_valid_empty_account() {
        Assertions.assertEquals("test", account.getUsername());
        Assertions.assertEquals("test", account.getPassword());
        Assertions.assertEquals(0, account.getBalance());
        Assertions.assertEquals(new ArrayList<>(), account.getTransactions());
    }

    @Test
    public void deposit_returns_balance() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "1000");
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void deposit_throws_exception() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "-1");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_returns_balance() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "1000");
        dispatcher.withdraw(account, "1000");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_throws_exception() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "1000");
        dispatcher.withdraw(account, "2000");
        Assertions.assertEquals(1000, account.getBalance());
    }

}
