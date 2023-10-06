package api;


import api.model.Player;
import api.service.TransactionService;

public class Application {
    public static void main(String[] args) {

//        First, we need to initialize Transaction Service
        TransactionService service = new TransactionService();

//        Second, creating player through service
        Player p1 = service.createPlayer("Andrew");

//        Depositing
        service.deposit(p1, 2000);

//        Withdrawing
        service.withdraw(p1, 100);

//        We can see Player info with his List of transactions
        System.out.println(p1);

//        If we don`t have enough money to withdraw - service will cancel transaction and send notification about the problem
        service.withdraw(p1, 10000);
    }
}


