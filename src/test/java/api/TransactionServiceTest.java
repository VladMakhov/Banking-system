package api;


import api.model.Player;
import api.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/*
* Functionality is quit intuitive and not a lot of method to test
* */
public class TransactionServiceTest {

    TransactionService service = new TransactionService();

    @Test
    public void createPlayer() {
        Player player = service.createPlayer("Player", "123");
        Assert.assertEquals("Player", player.getUsername());
        Assert.assertEquals(0, player.getBalance());
        Assert.assertEquals(new ArrayList<>(), player.getTransactions());
    }

    @Test
    public void deposit() {
        Player player = service.createPlayer("Player", "123");
        Assert.assertEquals(0, player.getBalance());
        service.deposit(player, 1000);
        Assert.assertEquals(1000, player.getBalance());
    }

    @Test
    public void withdrawal() {
        Player player = service.createPlayer("Player", "123");
        Assert.assertEquals(0, player.getBalance());
        service.deposit(player, 1000);
        service.withdraw(player, 1000);
        Assert.assertEquals(0, player.getBalance());
    }
}
