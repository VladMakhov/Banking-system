package unit;


import dispatcher.Dispatcher;
import dispatcher.DispatcherImpl;
import model.Account;
import org.junit.jupiter.api.*;



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
    @DisplayName("Creating empty account")
    public void createAccount_returns_valid_empty_account() {
        dispatcher.createAccount("account", "password");
        Account a = dispatcher.validateAccount("account", "password");
        Assertions.assertEquals("account", a.getUsername());
        Assertions.assertEquals("password", a.getPassword());
        Assertions.assertEquals(0, a.getBalance());
    }

    @Test
    @DisplayName("Making valid deposit")
    public void deposit_returns_balance() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "1000");
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    @DisplayName("Making invalid deposit - returns exception")
    public void deposit_throws_exception() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "-1");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    @DisplayName("Making valid withdrawal")
    public void withdrawal_returns_balance() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "1000");
        dispatcher.withdraw(account, "1000");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    @DisplayName("Making invalid withdrawal - returns exception")
    public void withdrawal_throws_exception() {
        Assertions.assertEquals(0, account.getBalance());
        dispatcher.deposit(account, "1000");
        dispatcher.withdraw(account, "2000");
        Assertions.assertEquals(1000, account.getBalance());
    }

}
