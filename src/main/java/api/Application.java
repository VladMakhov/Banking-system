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
            | This is bank of Ylab University and we are ready to serve you.          |
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
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "balance" -> System.out.println("balance: " + player.getBalance());
                case "deposit" -> {
                    System.out.print("How much money would you like to deposit: ");
                    try {
                        var amount = Integer.parseInt(scanner.nextLine());
                        if (amount < 0) {
                            throw new NumberFormatException();
                        }
                        service.deposit(player, amount);
                        System.out.println("balance: " + player.getBalance());
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect value");
                    }
                }
                case "withdraw" -> {
                    System.out.print("How much money would you like to withdraw: ");
                    try {
                        var amount = Integer.parseInt(scanner.nextLine());
                        if (amount < 0) {
                            throw new NumberFormatException();
                        }
                        if (player.getBalance() - amount >= 0) {
                            service.withdraw(player, amount);
                            System.out.println("balance: " + player.getBalance());
                        } else {
                            System.out.println("Not enough money on your account");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect value");
                    }

                }
                case "history" -> System.out.println(service.getTransactionHistory(player));
                case "exit" -> {}
                default -> System.out.println("Incorrect command");
            }
        }

    }
}
