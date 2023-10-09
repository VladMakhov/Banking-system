package api;


import api.model.Account;
import api.service.BankingService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;


public class WalletServiceTest {

    BankingService service = new BankingService();

    @Test
    public void createAccount_returns_valid_empty_account() {
        Account account = service.createAccount("Account", "123");
        Assertions.assertEquals("Account", account.getUsername());
        Assertions.assertEquals(0, account.getBalance());
        Assertions.assertEquals(new ArrayList<>(), account.getTransactions());
    }

    @Test
    public void deposit_returns_balance() {
        Account account = service.createAccount("Account", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, "1000");
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void deposit_throws_exception() {
        Account account = service.createAccount("Account", "123");
        service.deposit(account, "-1");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_returns_balance() {
        Account account = service.createAccount("Account", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, "1000");
        service.withdraw(account, "1000");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_throws_exception() {
        Account account = service.createAccount("Account", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, "1000");
        service.withdraw(account, "2000");
        Assertions.assertEquals(1000, account.getBalance());
    }

}
