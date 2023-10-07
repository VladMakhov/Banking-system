package api;


import api.model.Player;
import api.service.TransactionService;


import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        TransactionService service = new TransactionService();
        Scanner scanner = new Scanner(System.in);

        System.out.print("""
             _________________________________________________________________________
            | Good day, sir!                                                          |
            | This is national bank of america and we are ready to serve you.         |
            | To proceed you need to either register Account or Log in to existing.   |
            | Press sign up to create new Account                                     |
            |_________________________________________________________________________|
                """);

        System.out.println("What is your name?");
        System.out.print(">> ");
        var name = scanner.nextLine();

        Player player = service.createPlayer(name);
        System.out.println("""
             _________________________________________________
            | Instruction:                                    |
            | To check balance press 'balance'                |
            | To deposit money press 'deposit'                |
            | To withdraw money press 'withdraw'              |
            | To see your transaction history press 'history' |
            | To quit program press 'exit'                    |
            |_________________________________________________|
                """);

        var input = "go";
        while (!input.equals("exit")) {
            System.out.print(">> ");
            input = scanner.nextLine();

            switch (input) {
                case "balance" -> System.out.println("balance: " + player.getBalance());
                case "deposit" -> {
                    System.out.print("How much money would you like to deposit: ");
                    var amount = scanner.nextLine();
                    service.deposit(player, Integer.parseInt(amount));

                }
                case "withdraw" -> {
                    System.out.print("How much money would you like to withdraw: ");
                    var amount = Integer.parseInt(scanner.nextLine());

                    if (player.getBalance() - amount >= 0) {
                        service.withdraw(player, amount);
                    } else {
                        System.out.println("Not enough money on your account");
                    }

                }
                case "history" -> System.out.println(service.getTransactionHistory(player));
                case "exit" -> {}
                default -> System.out.println("Incorrect command");
            }
        }

    }
}
