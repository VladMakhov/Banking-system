package unit;

import dao.AccountDao;
import dao.classes.AccountDaoImpl;
import model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AccountService;
import service.classes.AccountServiceImpl;
import service.classes.LogService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    static AccountService accountService;
    static AccountDao accountDao = Mockito.mock(AccountDaoImpl.class);
    static LogService logService = new LogService();
    static Account account = new Account(0, "test", "test", 0);

    static {
        accountService = new AccountServiceImpl(accountDao, logService);
    }

    @Test
    public void create_account() {
        Mockito.when(accountDao.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito.when(accountDao.save(Mockito.any(Account.class))).thenReturn(account);

        Account saved = accountService.createAccount("test", "test");
        Assertions.assertNotNull(saved);
    }

    @Test
    public void create_account_when_name_is_taken() {
        Mockito.when(accountDao.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(account));

        Mockito.when(accountDao.save(Mockito.any(Account.class))).thenReturn(account);

        Account saved = accountService.createAccount("test", "test");
        Assertions.assertNull(saved);
    }

    @Test
    public void validate_account() {
        Mockito.when(accountDao.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(account));

        Optional<Account> validated = accountService.validateAccount("test", "test");

        Assertions.assertNotEquals(Optional.empty(), validated);
    }

    @Test
    public void validate_account_incorrect_name() {
        Mockito.when(accountDao.findAccountByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Optional<Account> validated = accountService.validateAccount("test", "test");

        Assertions.assertEquals(Optional.empty(), validated);
    }
}
