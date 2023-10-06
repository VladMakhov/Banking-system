package api;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TransactionServiceTest {

    TransactionService service = new TransactionService();

    @Test
    public void createPlayer() {
        Player player = service.createPlayer("Player");
        Assert.assertEquals("Player", player.getUsername());
        Assert.assertEquals(0, player.getBalance());
        Assert.assertEquals(new ArrayList<>(), player.getTransactions());
    }

    @Test
    public void deposit() {
        Player player = service.createPlayer("Player");
        Assert.assertEquals(0, player.getBalance());
        service.deposit(player, 1000);
        Assert.assertEquals(1000, player.getBalance());
    }

    @Test
    public void withdrawal_correct() {
        Player player = service.createPlayer("Player");
        Assert.assertEquals(0, player.getBalance());
        service.deposit(player, 1000);
        Assert.assertEquals(1000, player.getBalance());
    }

    @Test
    public void withdrawal_exception() {
        Player player = service.createPlayer("Player");
        Assert.assertFalse(service.withdraw(player, 1000));
    }
}
