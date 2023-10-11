package conrtoller;


import model.Account;
import dispatcher.Dispatcher;
import dispatcher.DispatcherImpl;

import java.util.Scanner;

/*
 * Controller class.
 * Using console to communicate with service.
 * Everything you do on one account does not affect others.
 * */
public class Controller {
    public static void main(String[] args) {
        Dispatcher dispatcher = new DispatcherImpl();

        Scanner scanner = new Scanner(System.in);

        Account account;
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
         *
         * Basically, it works like this:
         * | first layer | Home page - you can register accounts or log in to one.
         * | second layer | After logging you able to manage account finances.
         * */
        while (!program.equals("end")) {
            System.out.println("""
                    // Type 'register' to create new Account or 'login' to connect to existing one?""");
            System.out.print(">> ");
            var in = scanner.nextLine().toLowerCase();

            switch (in) {
                case "register" -> {
                    System.out.print("Username: ");
                    var username = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();
                    dispatcher.createAccount(username, password);
                }
                case "login" -> {
                    System.out.print("Username: ");
                    var username = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();

                    account = dispatcher.validateAccount(username, password);

                    if (account != null) {
                        System.out.println("// Welcome, " + account.getUsername() + "!\n" + instruction);
                        var input = "start";

                        while (!input.equals("exit")) {
                            System.out.print(">> ");
                            input = scanner.nextLine().toLowerCase();

                            switch (input) {
                                case "deposit" -> {
                                    System.out.print("// How much money would you like to deposit: ");
                                    var amount = scanner.nextLine();
                                    dispatcher.deposit(account, amount);
                                }
                                case "withdraw" -> {
                                    System.out.print("// How much money would you like to withdraw: ");
                                    var amount = scanner.nextLine();
                                    dispatcher.withdraw(account, amount);
                                }
                                case "info" -> System.out.println(dispatcher.getAccountInfo(account));
                                case "history" -> System.out.println(dispatcher.getTransactionHistory(account));
                                case "exit" -> dispatcher.addLog(account.getUsername() + " exited his account");
                                case "help" -> {
                                    System.out.println(instruction);
                                    dispatcher.addLog(account.getUsername() + " requested help menu");
                                }
                                default -> System.out.println("!!! Incorrect command");
                            }
                        }
                    }

                }
                case "exit" -> {
                    program = "end";
                    dispatcher.addLog("Program exited");
                }
                default -> System.out.println("!!! Incorrect command");
            }
        }
        System.out.println("Logs:");
        dispatcher.getLogs().stream().map(s -> "> " + s).forEach(System.out::println);
    }


}
