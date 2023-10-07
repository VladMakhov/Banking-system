package api.out;


import api.model.Player;
import api.service.TransactionService;


import java.util.*;

/*
 * Output class.
 * Using console to communicate with service.
 * You can create account or log in to existing.
 * Inside account you are able to deposit money or withdraw if you have enough.
 * Also, you can see info and transaction history with unique id of transaction.
 * Everything you do on one account is not affection others.
 * If program is terminated all Information will be erased.
 * */
public class Application {
    public static void main(String[] args) {
        TransactionService service = new TransactionService();
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                 _________________________________________________________________________
                | Good day, sir!                                                          |
                | This is bank of Ylab University.                                        |
                | To proceed you need to either register Account or Log in to existing.   |
                |_________________________________________________________________________|
                    """);

        Map<String, Player> accounts = new HashMap<>();
        Player player;
        var a = "start";

        while (!a.equals("end")) {
            System.out.println("\n'register' to create new Account or 'login' to connect to existing one?");
            System.out.print(">> ");
            var in = scanner.nextLine().toLowerCase();

            switch (in) {
                case "register" -> {
                    System.out.print("Username: ");
                    var name = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();

                    if (accounts.containsKey(name)) {
                        System.out.println("Choose different name");
                    } else {
                        Player p = service.createPlayer(name, password);
                        accounts.put(name, p);
                        System.out.println("Account is created");
                    }
                }
                case "login" -> {
                    System.out.print("Username: ");
                    var name = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();

                    if (accounts.containsKey(name)) {
                        if (!password.equals(accounts.get(name).getPassword())) {
                            System.out.println("Wrong password");
                            break;
                        }
                        player = accounts.get(name);
                        System.out.println("""
                                 _________________________________________________
                                | Instruction:                                    |
                                | To check info press 'info'                      |
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
                                case "info" -> System.out.println(service.getPlayerInfo(player));
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
                                case "exit" -> {
                                }
                                default -> System.out.println("Incorrect command");
                            }
                        }
                    } else {
                        System.out.println("Account does not exist");
                    }
                }
                case "exit" -> a = "end";
                default -> System.out.println("Unknown command");
            }
        }

    }
}
