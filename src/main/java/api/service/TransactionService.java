package api.service;

import api.model.Player;
import api.model.Transaction;
import api.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

/*
* Service that provides functionality to create Player with unique ID, his own balance and List of transactions.
* You can manipulate with player balance by making transactions such as deposit (add money) and withdrawal (take money)
* Transactions have their own unique ID as well.
* */
public class TransactionService {

//    Auto incrementing ID`s
    private static int PLAYER_ID = 1;
    private static int TRANSACTION_ID = 1;

//    You should create players through special method
    public Player createPlayer(String username, String password) {
        Player player = new Player();

        player.setId(PLAYER_ID++);
        player.setUsername(username);
        player.setBalance(0);
        player.setPassword(password);
        player.setTransactions(new ArrayList<>());

        return player;
    }

//    Private method to regulate transactions
    private static Transaction createTransaction(Player p, long amount, TransactionType type) {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(TRANSACTION_ID++);
        transaction.setPlayer(p.getId());
        transaction.setAmount(amount);
        transaction.setType(type);

        return transaction;
    }

//    Method to withdraw money from Player if he got enough
    public void withdraw(Player p, long amount) {
        p.setBalance(p.getBalance() - amount);
        Transaction transaction = createTransaction(p, amount, TransactionType.WITHDRAWAL);
        p.getTransactions().add(transaction);
    }

//    Method to add money to Player balance
    public void deposit(Player p, long amount) {
        p.setBalance(p.getBalance() + amount);
        Transaction transaction = createTransaction(p, amount, TransactionType.DEPOSIT);
        p.getTransactions().add(transaction);

    }

//    Get formatted transaction history
    public String getTransactionHistory(Player p) {
        StringBuilder str = new StringBuilder();
        str.append("""
                
                ID Amount   Type
                """);
        List<String> stringStream = p.getTransactions().stream()
                .map(tr -> tr.getTransactionId() + "   " + tr.getAmount() + "   " + tr.getType()).toList();
        for (int i = stringStream.size() - 1; i >= 0; i--) {
            str.append(stringStream.get(i)).append("\n");
        }
        return str.toString();
    }

//    Get formatted Player info
    public String getPlayerInfo(Player player) {
        return "Id: " + player.getId() +
                "\nName: " + player.getUsername() +
                "\nBalance: " + player.getBalance();
    }
}
