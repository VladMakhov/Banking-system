package api;


import api.model.Account;
import api.service.WalletService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.ArrayList;


public class WalletServiceTest {

    WalletService service = new WalletService();

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
        Assertions.assertEquals(1000, service.deposit(account, 1000));
    }

    @Test
    public void deposit_throws_exception() {
        Account account = service.createAccount("Account", "123");
        Assert.assertThrows(NumberFormatException.class,() -> service.deposit(account, -1));
    }

    @Test
    public void withdrawal_returns_balance() {
        Account account = service.createAccount("Account", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, 1000);
        service.withdraw(account, 1000);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_throws_exception() {
        Account account = service.createAccount("Account", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, 1000);
        Assert.assertThrows(IllegalArgumentException.class, () -> service.withdraw(account, 2000));
    }

}
