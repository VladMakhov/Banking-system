package unit;

import dao.FinanceDao;
import dao.classes.FinanceDaoImpl;
import model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.FinanceService;
import service.classes.FinanceServiceImpl;
import service.classes.LogService;

@ExtendWith(MockitoExtension.class)
public class FinanceServiceTest {

    static FinanceService financeService;
    static FinanceDao financeDao = Mockito.mock(FinanceDaoImpl.class);
    static LogService logService = new LogService();
    static Account account = new Account(0, "test", "test", 0);

    static {
        financeService = new FinanceServiceImpl(financeDao, logService);
    }

    @Test
    public void deposit_correct() {
        financeService.deposit(account, "3000");
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test made deposit transaction on 3000", logService.getLogs().get(lastLogIndex));
    }

    @Test
    public void deposit_not_a_number_for_amount() {
        financeService.deposit(account, "a");
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test failed deposit transaction", logService.getLogs().get(lastLogIndex));
    }

    @Test
    public void deposit_negative_number_for_amount() {
        financeService.deposit(account, "-1");
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test failed deposit transaction", logService.getLogs().get(lastLogIndex));
    }

    @Test
    public void withdraw_correct() {
        account.setBalance(4000);
        financeService.withdraw(account, "3000");
        account.setBalance(0);
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test made withdrawal transaction on 3000", logService.getLogs().get(lastLogIndex));
    }

    @Test
    public void withdraw_not_enough_money() {
        financeService.withdraw(account, "3000");
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test failed withdrawal transaction", logService.getLogs().get(lastLogIndex));
    }

    @Test
    public void withdraw_not_a_number_for_amount() {
        financeService.withdraw(account, "a");
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test failed withdrawal transaction", logService.getLogs().get(lastLogIndex));
    }

    @Test
    public void withdraw_negative_number_for_amount() {
        financeService.withdraw(account, "-1");
        int lastLogIndex = logService.getLogs().size() - 1;
        Assertions.assertEquals("test failed withdrawal transaction", logService.getLogs().get(lastLogIndex));
    }
}
