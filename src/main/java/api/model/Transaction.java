package api.model;


/*
 * Blueprint of a Transaction that regulates through the Transaction Service
 * */
public class Transaction {
    private long transactionId;
    private int player;
    private long amount;
    private TransactionType type;

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", playerId=" + player +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
