package conrtoller;


import dispatcher.Dispatcher;
import dispatcher.DispatcherImpl;
import model.Account;

import java.util.Scanner;

/*
 * Controller class.
 * Using console to communicate with service.
 * Everything you do on one account does not affect others.
 * */
public class Controller {

    public void start() {
        Dispatcher dispatcher = new DispatcherImpl();
        Account account;

        Scanner scanner = new Scanner(System.in);

        String welcome = """
                Good day, sir!
                This is bank of Ylab University.
                To proceed you need to either register Account or Log in to existing.""";
        String instruction = """
                Instruction:
                To check info type 'info'
                To deposit money type 'deposit'
                To withdraw money type 'withdraw'
                To see your transaction history type 'history'
                To see instruction type 'help'
                To log out type 'exit'""";

        System.out.println(welcome);
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
                    Sign up or Sign in?""");
            System.out.print(">> ");
            var in = scanner.nextLine().strip().toLowerCase().strip();

            switch (in) {
                case "register", "sign up", "signup" -> {
                    System.out.print("Enter name: ");
                    var username = scanner.nextLine().strip();
                    System.out.print("Enter password: ");
                    var password = scanner.nextLine().strip();
                    dispatcher.createAccount(username, password);
                }
                case "login", "sign in", "signin" -> {
                    System.out.print("Enter name: ");
                    var username = scanner.nextLine().strip();
                    System.out.print("Enter password: ");
                    var password = scanner.nextLine().strip();

                    account = dispatcher.validateAccount(username, password);

                    if (account != null) {
                        System.out.println("INFO: Welcome, " + account.getUsername() + "!\n" + instruction);
                        var input = "start";

                        while (!input.equals("exit")) {
                            System.out.print(">> ");
                            input = scanner.nextLine().strip().toLowerCase();

                            switch (input) {
                                case "deposit" -> {
                                    System.out.print("Amount: ");
                                    var amount = scanner.nextLine().strip();
                                    dispatcher.deposit(account, amount);
                                }
                                case "withdraw" -> {
                                    System.out.print("Amount: ");
                                    var amount = scanner.nextLine().strip();
                                    dispatcher.withdraw(account, amount);
                                }
                                case "info" -> System.out.println(dispatcher.getAccountInfo(account));
                                case "history" -> System.out.println(dispatcher.getTransactionHistory(account));
                                case "exit" -> {
                                    dispatcher.addLog(account.getUsername() + " exited account");
                                    System.out.println("INFO: Bye");
                                }
                                case "help" -> {
                                    System.out.println("\n" + instruction);
                                    dispatcher.addLog(account.getUsername() + " requested help menu");
                                }
                                default -> System.out.println("ERROR: Incorrect command");
                            }
                        }
                    }

                }
                case "exit" -> program = "end";
                default -> System.out.println("ERROR: Incorrect command");
            }
        }
        System.out.println("Logs:");
        dispatcher.getLogs().stream().map(s -> "> " + s).forEach(System.out::println);
    }

}
