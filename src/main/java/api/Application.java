package api;


public class Application {
    public static void main(String[] args) {
        TransactionService service = new TransactionService();

        Player p1 = service.createPlayer("Andrew");

        service.deposit(p1, 2000);
        service.withdraw(p1, 100);

        System.out.println(p1);

        service.withdraw(p1, 10000);
    }
}


