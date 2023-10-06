package api;

import java.util.ArrayList;

class TransactionService {

    private static int PLAYER_ID = 1;
    private static int TRANSACTION_ID = 1;

    public Player createPlayer(String username) {
        Player player = new Player();

        player.setId(PLAYER_ID++);
        player.setUsername(username);
        player.setBalance(0);
        player.setTransactions(new ArrayList<>());

        return player;
    }

    private static Transaction createTransaction(Player p, long amount, TransactionType type) {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(TRANSACTION_ID++);
        transaction.setPlayer(p.getId());
        transaction.setAmount(amount);
        transaction.setType(type);

        return transaction;
    }

    public boolean withdraw(Player p, long amount) {
        if (p.getBalance() - amount < 0) {
            System.out.println("Player " + p.getUsername() + " don`t have enough money");
            return false;
        } else {
            p.setBalance(p.getBalance() - amount);

            Transaction transaction = createTransaction(p, amount, TransactionType.WITHDRAWAL);

            p.getTransactions().add(transaction);

            System.out.println("You successfully withdraw: " + amount + " from your balance");
            return true;
        }
    }

    public void deposit(Player p, long amount) {

        p.setBalance(p.getBalance() + amount);

        Transaction transaction = createTransaction(p, amount, TransactionType.DEPOSIT);

        p.getTransactions().add(transaction);

        System.out.println("You successfully deposit: " + amount + " to your balance");
    }

}
