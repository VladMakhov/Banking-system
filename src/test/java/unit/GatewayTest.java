package unit;


import config.LiquibaseMigrationConfig;
import gateway.Gateway;
import gateway.GatewayImpl;
import model.Account;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
public class GatewayTest {

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    static Gateway gateway = new GatewayImpl();
    static Account account;

    @BeforeAll
    static void init() {
        container.start();
        LiquibaseMigrationConfig liquibaseMigrationConfig = new LiquibaseMigrationConfig();
        liquibaseMigrationConfig.run();
        gateway.createAccount("test", "test");
        account = gateway.validateAccount("test", "test").orElseThrow();
    }

    @BeforeEach
    void dest() {
        account.setBalance(0);
    }

    @Test
    @DisplayName("Creating empty account")
    public void createAccount_returns_valid_empty_account() {
        gateway.createAccount("account", "password");
        Account a = gateway.validateAccount("account", "password").orElseThrow();
        Assertions.assertEquals("account", a.getUsername());
        Assertions.assertEquals("password", a.getPassword());
        Assertions.assertEquals(0, a.getBalance());
    }

    @Test
    @DisplayName("Making valid deposit")
    public void deposit_returns_balance() {
        Assertions.assertEquals(0, account.getBalance());
        gateway.deposit(account, "1000");
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    @DisplayName("Making invalid deposit - returns exception")
    public void deposit_throws_exception() {
        Assertions.assertEquals(0, account.getBalance());
        gateway.deposit(account, "-1");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    @DisplayName("Making valid withdrawal")
    public void withdrawal_returns_balance() {
        Assertions.assertEquals(0, account.getBalance());
        gateway.deposit(account, "1000");
        gateway.withdraw(account, "1000");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    @DisplayName("Making invalid withdrawal - returns exception")
    public void withdrawal_throws_exception() {
        Assertions.assertEquals(0, account.getBalance());
        gateway.deposit(account, "1000");
        gateway.withdraw(account, "2000");
        Assertions.assertEquals(1000, account.getBalance());
    }

}
