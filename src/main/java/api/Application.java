package api;


import api.model.Account;
import api.service.BankingService;

import java.util.Scanner;

/*
 * Output class.
 * Using console to communicate with service.
 * Everything you do on one account does not affect others.
 * */
public class Application {
    public static void main(String[] args) {
        BankingService service = new BankingService();

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
         * P.S. Yes, code is big, but I am not sure how to decompose this and not lose infinite loop.
         *
         * Basically, it works like this:
         * 1) | first layer | Home page - you can register accounts or log in to one.
         * 2) | second layer | After logging you able to manage account finances.
         * 3) You can exit account and exit program, but data will be erased due to program memory storage.
         * */
        while (!program.equals("end")) {
            System.out.println("""
                    // Type 'register' to create new Account or 'login' to connect to existing one?""");
            System.out.print(">> ");
            var in = scanner.nextLine().toLowerCase();

            switch (in) {
                case "register" -> {
                    System.out.print("Username: ");
                    var name = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();

                    if (service.getAccounts().containsKey(name)) {
                        System.out.println("!!! Choose different name");
                    } else {
                        Account p = service.createAccount(name, password);
                        service.getAccounts().put(name, p);
                        System.out.println("// Account is created");
                        service.getLogs().add("Account was created: " + p.getUsername());
                    }
                }
                case "login" -> {
                    System.out.print("Username: ");
                    var name = scanner.nextLine();
                    System.out.print("Password: ");
                    var password = scanner.nextLine();

                    if (service.getAccounts().containsKey(name)) {
                        if (!password.equals(service.getAccounts().get(name).getPassword())) {
                            System.out.println("!!! Wrong password");
                            break;
                        }
                        account = service.getAccounts().get(name);
                        service.getLogs().add(account.getUsername() + " has entered his account");
                        System.out.println("// Welcome, " + account.getUsername() + "!");
                        System.out.print(instruction);
                        var input = "go";
                        while (!input.equals("exit")) {
                            System.out.print(">> ");
                            input = scanner.nextLine().toLowerCase();

                            switch (input) {
                                case "deposit" -> {
                                    System.out.print("// How much money would you like to deposit: ");
                                    try {
                                        var amount = Integer.parseInt(scanner.nextLine());
                                        long deposit = service.deposit(account, amount);
                                        System.out.println("balance: " + deposit);
                                        service.getLogs().add(account.getUsername() + " made deposit transaction on " + amount);
                                    } catch (NumberFormatException e) {
                                        System.out.println("!!! Incorrect value");
                                        service.getLogs().add(account.getUsername() + " failed deposit transaction");
                                    }
                                }
                                case "withdraw" -> {
                                    System.out.print("// How much money would you like to withdraw: ");
                                    try {
                                        var amount = Integer.parseInt(scanner.nextLine());
                                        long withdraw = service.withdraw(account, amount);
                                        System.out.println("balance: " + withdraw);
                                        service.getLogs().add(account.getUsername() + " made withdrawing transaction on " + amount);
                                    } catch (NumberFormatException e) {
                                        System.out.println("!!! Incorrect value");
                                        service.getLogs().add(account.getUsername() + " failed deposit transaction");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println("!!! Not enough money on your account");
                                        service.getLogs().add(account.getUsername() + " failed deposit transaction");
                                    }
                                }
                                case "info" -> {
                                    System.out.println(service.getAccountInfo(account));
                                    service.getLogs().add(account.getUsername() + " requested info");
                                }
                                case "history" -> {
                                    System.out.println(service.getTransactionHistory(account));
                                    service.getLogs().add(account.getUsername() + " requested transaction history");
                                }
                                case "exit" -> service.getLogs().add(account.getUsername() + " exited his account");
                                case "help" -> {
                                    System.out.println(instruction);
                                    service.getLogs().add(account.getUsername() + " requested help menu");
                                }
                                default -> System.out.println("!!! Incorrect command");
                            }
                        }
                    } else {
                        System.out.println("!!! Account does not exist");
                    }
                }
                case "exit" -> {
                    program = "end";
                    service.getLogs().add("Program exited");
                }
                default -> System.out.println("!!! Incorrect command");
            }
        }
        System.out.println("Logs:");
        service.getLogs().stream().map(s -> "> " + s).forEach(System.out::println);
    }
}
