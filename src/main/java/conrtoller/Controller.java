package conrtoller;


import config.DatabaseConnectionConfig;
import dao.classes.AccountDaoImpl;
import dao.classes.FinanceDaoImpl;
import gateway.Gateway;
import gateway.GatewayImpl;
import model.Account;
import service.classes.AccountServiceImpl;
import service.classes.FinanceServiceImpl;
import service.classes.LogService;

import java.util.Optional;
import java.util.Scanner;

/*
 * Controller class.
 * Using console to communicate with service.
 * Everything you do on one account does not affect others.
 * */
public class Controller {

    public void start() {
        Gateway gateway =
                new GatewayImpl(
                        new FinanceServiceImpl(new FinanceDaoImpl(
                                new DatabaseConnectionConfig()
                        ),
                                new LogService()
                        ),
                        new AccountServiceImpl(new AccountDaoImpl(
                                new DatabaseConnectionConfig()
                        ),
                                new LogService()
                        ),
                        new LogService()
                );

        Optional<Account> account;

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
        Optional<Object> program = Optional.of(new Object());
        /*
         * Main execution code that allows to communicate with application with console interface
         *
         * Basically, it works like this:
         * | first layer | Home page - you can register accounts or log in to one.
         * | second layer | After logging you able to manage account finances.
         * */
        while (program.isPresent()) {
            System.out.println("""
                    Sign up or Sign in?""");
            System.out.print(">> ");
            var input = scanner.nextLine().strip().toLowerCase().strip();

            switch (input) {
                case "register", "sign up", "signup" -> {
                    System.out.print("Enter name: ");
                    var username = scanner.nextLine().strip();
                    System.out.print("Enter password: ");
                    var password = scanner.nextLine().strip();
                    gateway.createAccount(username, password);
                }
                case "login", "sign in", "signin" -> {
                    System.out.print("Enter name: ");
                    var username = scanner.nextLine().strip();
                    System.out.print("Enter password: ");
                    var password = scanner.nextLine().strip();

                    account = gateway.validateAccount(username, password);

                    if (account.isPresent()) {
                        System.out.println("INFO: Welcome, " + account.get().getUsername() + "!\n" + instruction);
                        while (account.isPresent()) {
                            System.out.print(">> ");
                            input = scanner.nextLine().strip().toLowerCase();

                            switch (input) {
                                case "deposit" -> {
                                    System.out.print("Amount: ");
                                    var amount = scanner.nextLine().strip();
                                    gateway.deposit(account.get(), amount);
                                }
                                case "withdraw" -> {
                                    System.out.print("Amount: ");
                                    var amount = scanner.nextLine().strip();
                                    gateway.withdraw(account.get(), amount);
                                }
                                case "info" -> System.out.println(gateway.getAccountInfo(account.get()));
                                case "history" -> System.out.println(gateway.getTransactionHistory(account.get()));
                                case "exit" -> {
                                    gateway.addLog(account.get().getUsername() + " exited account");
                                    account = Optional.empty();
                                    System.out.println("INFO: Bye");
                                }
                                case "help" -> {
                                    System.out.println("\n" + instruction);
                                    gateway.addLog(account.get().getUsername() + " requested help menu");
                                }
                                default -> System.out.println("ERROR: Incorrect command");
                            }
                        }
                    }

                }
                case "exit" -> program = Optional.empty();
                default -> System.out.println("ERROR: Incorrect command");
            }
        }
        System.out.println("Logs:");
        gateway.getLogs().stream().map(s -> "> " + s).forEach(System.out::println);
    }

}
