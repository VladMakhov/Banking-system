package api.out;


import api.model.Player;
import api.service.TransactionService;


import java.util.*;

/*
 * Output class.
 * Using console to communicate with service.
 * Everything you do on one account does not affect others.
 * * */
public class Application {
    public static void main(String[] args) {

        TransactionService service = new TransactionService();
        Scanner scanner = new Scanner(System.in);
        Map<String, Player> accounts = new HashMap<>();
        List<String> logs = new ArrayList<>();
        Player player;
        String welcome = """
                 _________________________________________________________________________
                | Good day, sir!                                                          |
                | This is bank of Ylab University.                                        |
                | To proceed you need to either register Account or Log in to existing.   |
                |_________________________________________________________________________|
                """;
        String instruction = """
                 _________________________________________________
                | Instruction:                                    |
                | To check info type 'info'                       |
                | To deposit money type 'deposit'                 |
                | To withdraw money type 'withdraw'               |
                | To see your transaction history type 'history'  |
                | To see instruction type 'help'                  |
                | To log out type 'exit'                          |
                |_________________________________________________|
                """;

        System.out.print(welcome);
        var program = "start";

        /*
         * Main execution code that allows to communicate with application with console interface
         * P.S. Yes, code is big, but I am not sure how to decompose this and not lose infinite loop.
         *
         * Basically, it works like this:
         * 1) | first layer | Home page - you can register accounts or log in to one.
         * 2) | second layer | After logging you able to manage account finances.
         * 3) You can exit account and exit program, but data will be erased due to program memory storage.
         * */
        while (!program.equals("end")) {
            System.out.println("""
                    Type 'register' to create new Account or 'login' to connect to existing one?""");
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
                        System.out.println("--Account is created--");
                        logs.add("\nAccount was created: " + p.getUsername());
                    }
                }
                case "login" -> {
                    System.out.print("Username: ");
                    var name = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();

                    if (accounts.containsKey(name)) {
                        if (!password.equals(accounts.get(name).getPassword())) {
                            System.out.println("--Wrong password--");
                            break;
                        }
                        player = accounts.get(name);
                        logs.add("\n" + player.getUsername()  + " has entered his account");
                        System.out.println("--Welcome, " + player.getUsername() + "!--");
                        System.out.print(instruction);
                        var input = "go";
                        while (!input.equals("exit")) {
                            System.out.print(">> ");
                            input = scanner.nextLine().toLowerCase();

                            switch (input) {
                                case "info" -> {
                                    System.out.println(service.getPlayerInfo(player));
                                    logs.add("\n" + player.getUsername()  + " requested info");
                                }
                                case "deposit" -> {
                                    System.out.print("How much money would you like to deposit: ");
                                    try {
                                        var amount = Integer.parseInt(scanner.nextLine());
                                        if (amount < 0) {
                                            throw new NumberFormatException();
                                        }
                                        service.deposit(player, amount);
                                        System.out.println("balance: " + player.getBalance());
                                        logs.add("\n" + player.getUsername()  + " made deposit transaction on " + amount);
                                    } catch (NumberFormatException e) {
                                        System.out.println("--Incorrect value--");
                                        logs.add("\n" + player.getUsername()  + " failed deposit transaction");
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
                                            logs.add("\n" + player.getUsername()  + " made withdrawing transaction on " + amount);
                                        } else {
                                            System.out.println("Not enough money on your account");
                                            logs.add("\n" + player.getUsername()  + " failed deposit transaction");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("--Incorrect value--");
                                        logs.add("\n" + player.getUsername()  + " failed deposit transaction");
                                    }

                                }
                                case "history" -> {
                                    System.out.println(service.getTransactionHistory(player));
                                    logs.add("\n" + player.getUsername()  + " requested transaction history");
                                }
                                case "exit" -> {
                                    logs.add("\n" + player.getUsername()  + " exited his account");
                                }
                                case "help" -> {
                                    System.out.println(instruction);
                                    logs.add("\n" + player.getUsername()  + " requested help menu");
                                }
                                default -> System.out.println("--Incorrect command--");
                            }
                        }
                    } else {
                        System.out.println("Account does not exist");
                    }
                }
                case "exit" -> {
                    program = "end";
                    logs.add("\nprogram exited\n");
                }
                default -> System.out.println("--Incorrect command--");
            }
        }
        System.out.println(logs);

    }
}
