package api.service;

import api.model.Player;
import api.model.Transaction;
import api.model.TransactionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* Service that provides functionality to create Player with unique ID, his own balance and List of transactions.
* You can manipulate with player balance by making transactions such as deposit (add money) and withdrawal (take money)
* Transactions have their own time stamp and unique ID as well.
* */
public class TransactionService {

//    Auto incrementing ID`s
    private static int PLAYER_ID = 1;
    private static int TRANSACTION_ID = 1;

//    You should create players through special method
    public Player createPlayer(String username) {
        Player player = new Player();

        player.setId(PLAYER_ID++);
        player.setUsername(username);
        player.setBalance(0);
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
        transaction.setDate(new Date(System.currentTimeMillis()));

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

        List<String> stringStream = p.getTransactions().stream()
                .map(tr -> " " + tr.getTransactionId() + " " + tr.getType() + " " + tr.getAmount()).toList();
        for (int i = stringStream.size() - 1; i >= 0; i--) {
            str.append(stringStream.get(i)).append("\n");
        }
        return str.toString();
    }

}
