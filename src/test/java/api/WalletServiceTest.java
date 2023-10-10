package api;


import api.facade.BankFacade;
import api.model.Account;
import api.facade.BankFacadeImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;


public class WalletServiceTest {

    BankFacade service = new BankFacadeImpl();

    @Test
    public void createAccount_returns_valid_empty_account() {
        service.createAccount("Account1", "123");
        Account account = service.validateAccount("Account1", "123");
        Assertions.assertEquals("Account1", account.getUsername());
        Assertions.assertEquals(0, account.getBalance());
        Assertions.assertEquals(new ArrayList<>(), account.getTransactions());
    }

    @Test
    public void deposit_returns_balance() {
        service.createAccount("Account2", "123");
        Account account = service.validateAccount("Account2", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, "1000");
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void deposit_throws_exception() {
        service.createAccount("Account3", "123");
        Account account = service.validateAccount("Account3", "123");
        service.deposit(account, "-1");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_returns_balance() {
        service.createAccount("Account4", "123");
        Account account = service.validateAccount("Account4", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, "1000");
        service.withdraw(account, "1000");
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void withdrawal_throws_exception() {
        service.createAccount("Account5", "123");
        Account account = service.validateAccount("Account5", "123");
        Assertions.assertEquals(0, account.getBalance());
        service.deposit(account, "1000");
        service.withdraw(account, "2000");
        Assertions.assertEquals(1000, account.getBalance());
    }

}
