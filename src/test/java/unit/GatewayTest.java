package unit;


import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import config.LiquibaseMigrationConfig;
import gateway.Gateway;
import gateway.GatewayImpl;
import model.Account;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(MockitoExtension.class)
public class GatewayTest {

    public static PostgreSQLContainer<?> postgreSQLContainer;
    static Gateway gateway;
    static Account account;
    static String URL;
    static String NAME;
    static String PASSWORD;

    static  {
        postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true)
                .withExposedPorts(5432)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(9090), new ExposedPort(5432)))
                ));
        gateway = new GatewayImpl();
        postgreSQLContainer.start();
        URL = postgreSQLContainer.getJdbcUrl();
        NAME = postgreSQLContainer.getUsername();
        PASSWORD = postgreSQLContainer.getPassword();
    }

    @BeforeAll
    static void init() {
        System.out.println(URL);
        System.out.println(NAME);
        System.out.println(PASSWORD);
        LiquibaseMigrationConfig liquibaseMigrationConfig = new LiquibaseMigrationConfig();
        liquibaseMigrationConfig.run(URL, NAME, PASSWORD);
        gateway.createAccount("test", "test");
        account = gateway.validateAccount("test", "test").orElseThrow();
    }

    @BeforeEach
    void eraseBalance() {
        account.setBalance(0);
    }

    @AfterAll
    static void closePostgres() {
        postgreSQLContainer.close();
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
